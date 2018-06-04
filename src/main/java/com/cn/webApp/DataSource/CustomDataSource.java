package com.cn.webApp.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

/**
 * 自定义数据库连接池
 * Created by es on 2016/8/4.
 */
public class CustomDataSource extends MysqlDataSource {
Logger Log=Logger.getLogger(this.getClass());
    //region 连接参数管理
    protected String driverClassName = null;
    protected String username = null;
    protected int maxActive = 8;
    protected int maxIdle = 4;
    protected int maxWait = -1;

    protected boolean defaultAutoCommit = false;
    protected String validationQuery = null;
    protected int validationQueryTimeout = -1;
    protected boolean testOnBorrow = false;
    protected boolean testOnReturn = false;
    protected boolean testWhileIdle = false;

    protected String keystorePath = null; //keystore路径 参数字符串必须是 file:/XXXXX 类型
    protected String keystorePassword = null; //密码
    protected String trustorePath = null;
    protected String trustorePassword = null;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public String getKeystorePath() {
        return keystorePath;
    }

    public void setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    public String getTrustorePath() {
        return trustorePath;
    }

    public void setTrustorePath(String trustorePath) {
        this.trustorePath = trustorePath;
    }

    public String getTrustorePassword() {
        return trustorePassword;
    }

    public void setTrustorePassword(String trustorePassword) {
        this.trustorePassword = trustorePassword;
    }

    public boolean isDefaultAutoCommit() {
        return defaultAutoCommit;
    }

    public void setDefaultAutoCommit(boolean defaultAutoCommit) {
        this.defaultAutoCommit = defaultAutoCommit;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public int getValidationQueryTimeout() {
        return validationQueryTimeout;
    }

    public void setValidationQueryTimeout(int validationQueryTimeout) {
        this.validationQueryTimeout = validationQueryTimeout;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }
    //endregion

    //连接池初始化
    public void init() {
        for (int i = 0; i < maxIdle; i++) {
            Connection conn = this.newConnection();
            freeConnection(conn);
        }
    }

    public Connection getConnection() {
        final Connection conn;
        if (maxWait == -1) {
            conn = getConnection2();
        } else {
            conn = getConnection2(maxWait);
        }
        if (conn == null) {
            throw new RuntimeException("对不起，数据库忙");
        }
        return (Connection) Proxy.newProxyInstance(this.getClass()
                        .getClassLoader(), conn.getClass().getInterfaces(),
                new InvocationHandler() {
                    //此处为内部类，当close方法被调用时将conn还回池中,其它方法直接执行
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("close")) {
                            freeConnection(conn);
                            return null;
                        }
                        return method.invoke(conn, args);
                    }
                });
    }

    //region 连接池管理
    private int checkedOut = 0; //检查可用的连接数量
    private Vector<Connection> freeConnections = new Vector<Connection>();

    //添加一个空闲连接
    private synchronized void freeConnection(Connection con) {
        freeConnections.add(con);
        checkedOut--;
        Log.info("往池子里放进了一个连接！");
        notifyAll();
    }

    /**
     * 获得数据库连接
     *
     * @param timeout 超时时间
     * @return
     */
    private synchronized Connection getConnection2(long timeout) {
        long startTime = System.currentTimeMillis();
        Connection con;
        while ((con = getConnection2()) == null) {
            try {
                wait(timeout);
            } catch (InterruptedException e) {
            }
            if ((System.currentTimeMillis() - startTime) >= timeout) {
                return null;
            }
        }
        return con;
    }

    //获得数据库连接
    private synchronized Connection getConnection2() {
        Connection con = null;
        if (freeConnections.size() > 0) {
            con = (Connection) freeConnections.firstElement();
            freeConnections.removeElementAt(0);
            try {
                if (con.isClosed()) {
                    checkedOut--;
                    Log.info("从连接池删除一个无效连接");
                    con = getConnection2();
                }
            } catch (SQLException e) {
                con = getConnection2();
            }
        } else if (maxActive == 0 || checkedOut < maxActive) {
            con = newConnection();
        }
        if (con != null) {
            checkedOut++;
        }
        return con;
    }

    //释放所有连接
    public synchronized void close() {
        Enumeration allConnections = freeConnections.elements();
        while (allConnections.hasMoreElements()) {
            Connection con = (Connection) allConnections.nextElement();
            try {
                con.close();
            } catch (SQLException e) {
                Log.info("无法关闭连接池中的连接");
            }
        }
        freeConnections.removeAllElements();
    }

    //创建一个新的连接
    private Connection newConnection() {
        try {
            MysqlDataSource mysqlDS = new MysqlDataSource();
            mysqlDS.setLogger("com.mysql.jdbc.log.StandardLogger");
            if (getUseSSL()) {
                mysqlDS.setUseSSL(true);
                mysqlDS.setRequireSSL(true);
                mysqlDS.setVerifyServerCertificate(true);
                mysqlDS.setClientCertificateKeyStoreType("JKS");
                mysqlDS.setClientCertificateKeyStoreUrl(keystorePath);
                mysqlDS.setClientCertificateKeyStorePassword(keystorePassword);
                mysqlDS.setTrustCertificateKeyStoreType("JKS");
                mysqlDS.setTrustCertificateKeyStoreUrl(trustorePath);
                mysqlDS.setTrustCertificateKeyStorePassword(trustorePassword);
            }
            mysqlDS.setURL(url);
            mysqlDS.setUser(username);
            mysqlDS.setPassword(password);
            Connection conn = mysqlDS.getConnection();
            Log.info("连接池创建一个新的连接");
            return conn;
        } catch (SQLException e) {
            Log.info("无法创建该URL的连接:" + url);
            return null;
        }
    }
    //endregion

    //region 连接有效性检验
    //检验每一个数据库连接的有效性
    public synchronized void validateConnection(){
        int size = freeConnections.size();
        Connection conn;
        for(int i = 0; i < size; i++){
            conn = freeConnections.get(i);
            validateConnection(conn);
        }
    }

    private void validateConnection(Connection conn) {
        try {
            if(conn.isClosed()) {
                return;
            }
        } catch (SQLException e) {
            Log.error(e);
        }
        try{
            this.validate(conn, this.validationQueryTimeout);
        }catch (SQLException e){
            try {
                conn.close();
            } catch (SQLException e1) {
                Log.error(e1);
            }
        }
    }

    //检验连接是否可用
    private void validate(Connection conn, int timeout) throws SQLException {
        if(conn == null || conn.isClosed()){
            return;
        }
        if(this.validationQuery != null && this.validationQuery.length() != 0){
            PreparedStatement ps = conn.prepareStatement(this.validationQuery);
            if(timeout > 0) {
                ps.setQueryTimeout(timeout);
            }
            try {
                ResultSet sqle = ps.executeQuery();
                Throwable var4 = null;
                try {
                    if(!sqle.next()) {
                        throw new SQLException("validationQuery didn\'t return a row");
                    }
                } catch (Throwable var14) {
                    var4 = var14;
                } finally {
                    if(sqle != null) {
                        if(var4 != null) {
                            try {
                                sqle.close();
                            } catch (Throwable var13) {
                                var4.addSuppressed(var13);
                            }
                        } else {
                            sqle.close();
                        }
                    }
                }
            } catch (SQLException var16) {
                throw var16;
            }
        }else{
            if(timeout < 0) {
                timeout = 0;
            }
            if(!this.isValid(conn, timeout)) {
                throw new SQLException("isValid() returned false");
            }
        }
    }

    private boolean isValid(Connection conn, int timeout) throws SQLException {
        if(conn == null || conn.isClosed()) {
            return false;
        } else {
            try {
                return conn.isValid(timeout);
            } catch (SQLException var3) {
                this.handleException(var3);
                return false;
            }
        }
    }

    private void handleException(SQLException e) throws SQLException {
        throw e;
    }
}
package com.cn.webApp.propertiesConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.DataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import ca.ex.BaseException;



/**
 * 
 * property属性配置器，支持从数据库中，获得对应配置
 * 
 *
 */
public class DBPropertiesConfig extends
PropertyPlaceholderConfigurer implements  InitializingBean{
	
	private DataSource dataSource;
	//是否启用从DB中获得属性
	private boolean useDBConfig = true;
	//当前应用名
	private String app;
	
	String p = "\\{[a-zA-Z0-9\\.]+\\}";
	Pattern pattern = Pattern.compile(p);
	
	@Override
	protected String resolvePlaceholder(String placeholder, Properties props,
			int systemPropertiesMode) {
		String propVal = null;
		if (systemPropertiesMode == SYSTEM_PROPERTIES_MODE_OVERRIDE) {
			propVal = resolveSystemProperty(placeholder);
		}
		if(useDBConfig){
			propVal = resolveDBPlaceholder(placeholder);
		}
		if (propVal == null) {
			propVal = resolvePlaceholder(placeholder, props);
		}
		if (propVal == null && systemPropertiesMode == SYSTEM_PROPERTIES_MODE_FALLBACK) {
			propVal = resolveSystemProperty(placeholder);
		}
		return propVal;
	}
	
	protected String resolveDBPlaceholder(String placeholder) {
		String key = placeholder;
		if(app != null){
			key = app+"."+placeholder;
		}
		String propVal =  DBProperties.getProperty(key);
		if(propVal == null){
			key = "all."+placeholder;
		}
		propVal =  DBProperties.getProperty(key);
		if(propVal == null){
			propVal =  DBProperties.getProperty(placeholder);
		}
		return propVal;
	}
	
	 
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setUseDBConfig(boolean useDBConfig) {
		this.useDBConfig = useDBConfig;
	}
	
 
	@SuppressWarnings("unchecked")
	private void initProperties(){
		Iterator<Object> keys = DBProperties.keys().iterator();
		while (keys.hasNext()) {
			//DB配置优先级高
			String key = keys.next().toString();
			String value = resolveValue(key);
			DBProperties.put(key, value);
			System.setProperty(key, value);
		} 
	}
	
	private String resolveValue(String key){
		String value = DBProperties.getProperty(key);
		if(value == null){
			value = DBProperties.getProperty(app+"."+key);
		}
		if(value == null){
			value = DBProperties.getProperty("all."+key);
		}
		if(value == null){
			return null;
		}
		Matcher m = pattern.matcher(value);
		while(m.find()){
			String tag = m.group();
			String tempKey = tag.substring(1, tag.length()-1);
			String tempValue = resolveValue(tempKey);
			if(tempValue != null){
				value = value.replace(tag, tempValue);
			}
		}
		return value; 
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(!useDBConfig){
			return;
		}
		if(dataSource == null){
			throw new BaseException("必须设置平台数据源");
		}
		Connection conn = null;
		try{
			conn = dataSource.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select PROP_KEY,PROP_VALUE from base_properties");
			while(rs.next()){
				String key = rs.getString("PROP_KEY");
				String value = rs.getString("PROP_VALUE");
				DBProperties.put(key, value);
			}
			
			initProperties();
		}catch (Exception e) {
			logger.error(e);
			throw new BaseException("Init Platform DB Properties Failure",e);
		}finally{
			try{
				conn.close();
			}catch (Exception e) {
			}
		}
	}
	
	public void setApp(String app) {
		this.app = app;
	}
	

}

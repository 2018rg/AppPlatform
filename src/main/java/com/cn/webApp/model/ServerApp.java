package com.cn.webApp.model;

import java.util.Date;
/**
 * 行业前置 
 * 实体类
 * @author rg
 * 下午4:36:32
 */
public class ServerApp extends BaseObject{
    private String id;

    private String customerCode;

    private String serverIp;

    private int serverPort;

    private String serverRoot;

    private String appPackageId;

    private int ishttp;

    private String describ;

    private Date ctTime;

    private String operator;

    private Byte isUsed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp == null ? null : serverIp.trim();
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerRoot() {
        return serverRoot;
    }

    public void setServerRoot(String serverRoot) {
        this.serverRoot = serverRoot == null ? null : serverRoot.trim();
    }

    public String getAppPackageId() {
        return appPackageId;
    }

    public void setAppPackageId(String appPackageId) {
        this.appPackageId = appPackageId == null ? null : appPackageId.trim();
    }

    public Integer getIshttp() {
        return ishttp;
    }

    public void setIshttp(Integer ishttp) {
        this.ishttp = ishttp;
    }

    public String getDescrib() {
        return describ;
    }

    public void setDescrib(String describ) {
        this.describ = describ == null ? null : describ.trim();
    }

    public Date getCtTime() {
        return ctTime;
    }

    public void setCtTime(Date ctTime) {
        this.ctTime = ctTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Byte getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Byte isUsed) {
        this.isUsed = isUsed;
    }
}
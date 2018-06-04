package com.cn.webApp.model;

import java.util.Date;
/**
 * 实体类
 * 登录日志表
 * @author rg
 * 上午9:50:46
 */
public class LogLogin extends BaseObject{
    private String id;

    private String userId;

    private String customerunitcode;

    private Integer logType;

    private Integer logLimitType;

    private String ip;

    private Date occurTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getCustomerunitcode() {
        return customerunitcode;
    }

    public void setCustomerunitcode(String customerunitcode) {
        this.customerunitcode = customerunitcode == null ? null : customerunitcode.trim();
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public Integer getLogLimitType() {
        return logLimitType;
    }

    public void setLogLimitType(Integer logLimitType) {
        this.logLimitType = logLimitType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }
}
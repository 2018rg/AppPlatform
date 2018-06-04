package com.cn.webApp.model;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 实体类
 * 租户数据源
 * @author rg
 * 上午9:44:53
 */
public class DataSource extends BaseObject{
    private String id;

    private String customerunitcode;

    private String dsHost;

    private String dsPort;

    private String dsType;

    private String dsName;

    private String dsUser;

    private String dsPwd;

    private Date addTime;

    private Date lastUpdateTime;

    private String dsModel;

    private String appid;

    private String name;

    private String remark;

    private BigDecimal effective;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCustomerunitcode() {
        return customerunitcode;
    }

    public void setCustomerunitcode(String customerunitcode) {
        this.customerunitcode = customerunitcode == null ? null : customerunitcode.trim();
    }

    public String getDsHost() {
        return dsHost;
    }

    public void setDsHost(String dsHost) {
        this.dsHost = dsHost == null ? null : dsHost.trim();
    }

    public String getDsPort() {
        return dsPort;
    }

    public void setDsPort(String dsPort) {
        this.dsPort = dsPort == null ? null : dsPort.trim();
    }

    public String getDsType() {
        return dsType;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType == null ? null : dsType.trim();
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName == null ? null : dsName.trim();
    }

    public String getDsUser() {
        return dsUser;
    }

    public void setDsUser(String dsUser) {
        this.dsUser = dsUser == null ? null : dsUser.trim();
    }

    public String getDsPwd() {
        return dsPwd;
    }

    public void setDsPwd(String dsPwd) {
        this.dsPwd = dsPwd == null ? null : dsPwd.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getDsModel() {
        return dsModel;
    }

    public void setDsModel(String dsModel) {
        this.dsModel = dsModel == null ? null : dsModel.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getEffective() {
        return effective;
    }

    public void setEffective(BigDecimal effective) {
        this.effective = effective;
    }
}
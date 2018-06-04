package com.cn.webApp.model;

import java.util.Date;
/**
 * 资源包信息
 * 实体类
 * @author rg
 * 下午1:50:31
 */
public class AppResourcePackage extends BaseObject{
    private String id;

    private String resourcepackagename;

    private String serverip;

    private String serverport;

    private String approot;

    private String described;

    private Date addtime;

    private Integer status;

    private String customerunitcode;

    private String version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getResourcepackagename() {
        return resourcepackagename;
    }

    public void setResourcepackagename(String resourcepackagename) {
        this.resourcepackagename = resourcepackagename == null ? null : resourcepackagename.trim();
    }

    public String getServerip() {
        return serverip;
    }

    public void setServerip(String serverip) {
        this.serverip = serverip == null ? null : serverip.trim();
    }

    public String getServerport() {
        return serverport;
    }

    public void setServerport(String serverport) {
        this.serverport = serverport == null ? null : serverport.trim();
    }

    public String getApproot() {
        return approot;
    }

    public void setApproot(String approot) {
        this.approot = approot == null ? null : approot.trim();
    }

    public String getDescribed() {
        return described;
    }

    public void setDescribed(String described) {
        this.described = described == null ? null : described.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCustomerunitcode() {
        return customerunitcode;
    }

    public void setCustomerunitcode(String customerunitcode) {
        this.customerunitcode = customerunitcode == null ? null : customerunitcode.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }
}
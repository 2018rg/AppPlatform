package com.cn.webApp.model;
/**
 * 实体类
 * 系统标识对照表
 * @author rg
 * 上午9:33:53
 */
public class AppSoure extends BaseObject{
    private String appcode;

    private String appname;

    private String appver;

    private String description;

    public String getAppcode() {
        return appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode == null ? null : appcode.trim();
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname == null ? null : appname.trim();
    }

    public String getAppver() {
        return appver;
    }

    public void setAppver(String appver) {
        this.appver = appver == null ? null : appver.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}
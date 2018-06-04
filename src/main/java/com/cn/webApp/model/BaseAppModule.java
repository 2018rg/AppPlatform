package com.cn.webApp.model;

/**
 * 实体类
 * 应用包-应用模块关系表
 * @author rg
 * 上午9:43:28
 */
public class BaseAppModule extends BaseObject{
    private String id;

    private String appid;

    private String moduleid;

    private int sortid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getModuleid() {
        return moduleid;
    }

    public void setModuleid(String moduleid) {
        this.moduleid = moduleid == null ? null : moduleid.trim();
    }

    public int getSortid() {
        return sortid;
    }

    public void setSortid(int sortid) {
        this.sortid = sortid;
    }
}
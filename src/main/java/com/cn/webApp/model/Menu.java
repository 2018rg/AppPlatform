package com.cn.webApp.model;

/**
 * 实体类
 * 应用包-应用模块（菜单）目录信息
 * @author rg
 * 上午9:55:38
 */
public class Menu extends BaseObject{
    private String menuid;

    private String id;

    private String moduleid;

    private String menuname;

    private String smallicon;

    private String navlink;

    private String description;

    private String largeicon;

    private String parentmenuid;

    private int sortid;

    private int isvisible;

    private String appid;

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid == null ? null : menuid.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getModuleid() {
        return moduleid;
    }

    public void setModuleid(String moduleid) {
        this.moduleid = moduleid == null ? null : moduleid.trim();
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname == null ? null : menuname.trim();
    }

    public String getSmallicon() {
        return smallicon;
    }

    public void setSmallicon(String smallicon) {
        this.smallicon = smallicon == null ? null : smallicon.trim();
    }

    public String getNavlink() {
        return navlink;
    }

    public void setNavlink(String navlink) {
        this.navlink = navlink == null ? null : navlink.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getLargeicon() {
        return largeicon;
    }

    public void setLargeicon(String largeicon) {
        this.largeicon = largeicon == null ? null : largeicon.trim();
    }

    public String getParentmenuid() {
        return parentmenuid;
    }

    public void setParentmenuid(String parentmenuid) {
        this.parentmenuid = parentmenuid == null ? null : parentmenuid.trim();
    }

    public int getSortid() {
        return sortid;
    }

    public void setSortid(int sortid) {
        this.sortid = sortid;
    }

    public int getIsvisible() {
        return isvisible;
    }

    public void setIsvisible(int isvisible) {
        this.isvisible = isvisible;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }
}
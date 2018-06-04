package com.cn.webApp.model;

import java.util.Date;
/**
 * 实体类
 * 应用模块信息
 * @author rg
 * 下午3:28:57
 */
public class AppModule extends BaseObject{

	private String moduleid;

    private String id;

    private String modulecode;

    private String modulename;

    private int limitnum;

    private Date limitdt;

    private Date reditdt;	

    private String reditasn;

    private String description;

    private String apptype;

    private Date opdt;

    private String syscode;

    private String appid;

    private Double sortid;

    private String regcode;

    private String reditedcode;

    public String getModuleid() {
        return moduleid;
    }

    public void setModuleid(String moduleid) {
        this.moduleid = moduleid == null ? null : moduleid.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getModulecode() {
        return modulecode;
    }

    public void setModulecode(String modulecode) {
        this.modulecode = modulecode == null ? null : modulecode.trim();
    }

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename == null ? null : modulename.trim();
    }

    public int getLimitnum() {
        return limitnum;
    }

    public void setLimitnum(int limitnum) {
        this.limitnum = limitnum;
    }

    public Date getLimitdt() {
        return limitdt;
    }

    public void setLimitdt(Date limitdt) {
        this.limitdt = limitdt;
    }

    public Date getReditdt() {
        return reditdt;
    }

    public void setReditdt(Date reditdt) {
        this.reditdt = reditdt;
    }

    public String getReditasn() {
        return reditasn;
    }

    public void setReditasn(String reditasn) {
        this.reditasn = reditasn == null ? null : reditasn.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getApptype() {
        return apptype;
    }

    public void setApptype(String apptype) {
        this.apptype = apptype == null ? null : apptype.trim();
    }

    public Date getOpdt() {
        return opdt;
    }

    public void setOpdt(Date opdt) {
        this.opdt = opdt;
    }

    public String getSyscode() {
        return syscode;
    }

    public void setSyscode(String syscode) {
        this.syscode = syscode == null ? null : syscode.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public Double getSortid() {
        return sortid;
    }

    public void setSortid(Double sortid) {
        this.sortid = sortid;
    }

    public String getRegcode() {
        return regcode;
    }

    public void setRegcode(String regcode) {
        this.regcode = regcode == null ? null : regcode.trim();
    }

    public String getReditedcode() {
        return reditedcode;
    }

    public void setReditedcode(String reditedcode) {
        this.reditedcode = reditedcode == null ? null : reditedcode.trim();
    }
}
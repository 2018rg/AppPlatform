package com.cn.webApp.model;

import java.util.Date;
/**
 * 实体类
 * 应用包注册信息管理
 * @author rg
 * 上午9:32:52
 */
public class AppRedit extends BaseObject{
    private String appid;

    private String id;

    private String syscode;

    private String appname;

    private String appver;

    private String appdescription;

    private int applimitnum;

    private Date applimitdate;

    private Date opdt;

    private String reditasn;

    private Date reditdt;

    private String apptype;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private Long version;

    private String sign;

    private String regcode;

    private String accreditedcode;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSyscode() {
        return syscode;
    }

    public void setSyscode(String syscode) {
        this.syscode = syscode == null ? null : syscode.trim();
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

    public String getAppdescription() {
        return appdescription;
    }

    public void setAppdescription(String appdescription) {
        this.appdescription = appdescription == null ? null : appdescription.trim();
    }

    public int getApplimitnum() {
        return applimitnum;
    }

    public void setApplimitnum(int applimitnum) {
        this.applimitnum = applimitnum;
    }

    public Date getApplimitdate() {
        return applimitdate;
    }

    public void setApplimitdate(Date applimitdate) {
        this.applimitdate = applimitdate;
    }

    public Date getOpdt() {
        return opdt;
    }

    public void setOpdt(Date opdt) {
        this.opdt = opdt;
    }

    public String getReditasn() {
        return reditasn;
    }

    public void setReditasn(String reditasn) {
        this.reditasn = reditasn == null ? null : reditasn.trim();
    }

    public Date getReditdt() {
        return reditdt;
    }

    public void setReditdt(Date reditdt) {
        this.reditdt = reditdt;
    }

    public String getApptype() {
        return apptype;
    }

    public void setApptype(String apptype) {
        this.apptype = apptype == null ? null : apptype.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getRegcode() {
        return regcode;
    }

    public void setRegcode(String regcode) {
        this.regcode = regcode == null ? null : regcode.trim();
    }

    public String getAccreditedcode() {
        return accreditedcode;
    }

    public void setAccreditedcode(String accreditedcode) {
        this.accreditedcode = accreditedcode == null ? null : accreditedcode.trim();
    }
}
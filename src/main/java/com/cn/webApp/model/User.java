package com.cn.webApp.model;

import java.util.Date;
/**
 * 实体类
 * 用户信息表
 * @author rg
 * 上午10:12:16
 */
public class User extends BaseObject{
    private String id;

    private String empcode;

    private String emppwd;

    private Long sex;

    private String name;

    private String nation;

    private Long certificateid;

    private String idcardno;

    private String email;

    private String telephone;

    private Long empstate;

    private Date opdt;

    private Long isdelete;

    private Long empid;

    private String customerunitcode;

    private Integer loglimit;

    private String roleid;

    private String roletype;

    private Integer isadmin;

    private Integer ismaster;

    private Integer version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getEmpcode() {
        return empcode;
    }

    public void setEmpcode(String empcode) {
        this.empcode = empcode == null ? null : empcode.trim();
    }

    public String getEmppwd() {
        return emppwd;
    }

    public void setEmppwd(String emppwd) {
        this.emppwd = emppwd == null ? null : emppwd.trim();
    }

    public Long getSex() {
        return sex;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public Long getCertificateid() {
        return certificateid;
    }

    public void setCertificateid(Long certificateid) {
        this.certificateid = certificateid;
    }

    public String getIdcardno() {
        return idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno == null ? null : idcardno.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public Long getEmpstate() {
        return empstate;
    }

    public void setEmpstate(Long empstate) {
        this.empstate = empstate;
    }

    public Date getOpdt() {
        return opdt;
    }

    public void setOpdt(Date opdt) {
        this.opdt = opdt;
    }

    public Long getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Long isdelete) {
        this.isdelete = isdelete;
    }

    public Long getEmpid() {
        return empid;
    }

    public void setEmpid(Long empid) {
        this.empid = empid;
    }

    public String getCustomerunitcode() {
        return customerunitcode;
    }

    public void setCustomerunitcode(String customerunitcode) {
        this.customerunitcode = customerunitcode == null ? null : customerunitcode.trim();
    }

    public Integer getLoglimit() {
        return loglimit;
    }

    public void setLoglimit(Integer loglimit) {
        this.loglimit = loglimit;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public String getRoletype() {
        return roletype;
    }

    public void setRoletype(String roletype) {
        this.roletype = roletype == null ? null : roletype.trim();
    }

    public Integer getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(Integer isadmin) {
        this.isadmin = isadmin;
    }

    public Integer getIsmaster() {
        return ismaster;
    }

    public void setIsmaster(Integer ismaster) {
        this.ismaster = ismaster;
    }

    public Integer getVer() {
        return version;
    }

    public void setVer(Integer ver) {
        this.version = version;
    }
}
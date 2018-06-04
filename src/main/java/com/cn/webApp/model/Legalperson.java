package com.cn.webApp.model;

import java.util.Date;
/**
 * 实体类
 * 客户（法人）信息表
 * @author rg
 * 上午9:45:31
 */
public class Legalperson extends BaseObject{
    private String id;

    private String customerunitcode;

    private String customername;

    private String customernamejp;

    private String linkman;

    private String telephone;

    private String address;

    private String email;

    private int bankcode;

    private String bankcardno;

    private Date opdt;

    private Date opendt;

    private String remark;

    private Integer status;

    private Integer isdelete;

    private Integer isbalance;

    private String bankcodeno;

    private Date limitdt;

    private String weburl;

    private String databasestr;

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

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername == null ? null : customername.trim();
    }

    public String getCustomernamejp() {
        return customernamejp;
    }

    public void setCustomernamejp(String customernamejp) {
        this.customernamejp = customernamejp == null ? null : customernamejp.trim();
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman == null ? null : linkman.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getBankcode() {
        return bankcode;
    }

    public void setBankcode(Integer bankcode) {
        this.bankcode = bankcode;
    }

    public String getBankcardno() {
        return bankcardno;
    }

    public void setBankcardno(String bankcardno) {
        this.bankcardno = bankcardno == null ? null : bankcardno.trim();
    }

    public Date getOpdt() {
        return opdt;
    }

    public void setOpdt(Date opdt) {
        this.opdt = opdt;
    }

    public Date getOpendt() {
        return opendt;
    }

    public void setOpendt(Date opendt) {
        this.opendt = opendt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public Integer getIsbalance() {
        return isbalance;
    }

    public void setIsbalance(Integer isbalance) {
        this.isbalance = isbalance;
    }

    public String getBankcodeno() {
        return bankcodeno;
    }

    public void setBankcodeno(String bankcodeno) {
        this.bankcodeno = bankcodeno == null ? null : bankcodeno.trim();
    }

    public Date getLimitdt() {
        return limitdt;
    }

    public void setLimitdt(Date limitdt) {
        this.limitdt = limitdt;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl == null ? null : weburl.trim();
    }

    public String getDatabasestr() {
        return databasestr;
    }

    public void setDatabasestr(String databasestr) {
        this.databasestr = databasestr == null ? null : databasestr.trim();
    }
}
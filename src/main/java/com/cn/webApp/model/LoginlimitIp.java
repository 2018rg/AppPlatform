package com.cn.webApp.model;
/**
 * 实体类
 * 职员登录限制表
 * @author rg
 * 上午9:48:08
 */
public class LoginlimitIp extends BaseObject{
    private String id;

    private String empcode;

    private String limitip;

    private String customerunitcode;

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

    public String getLimitip() {
        return limitip;
    }

    public void setLimitip(String limitip) {
        this.limitip = limitip == null ? null : limitip.trim();
    }

    public String getCustomerunitcode() {
        return customerunitcode;
    }

    public void setCustomerunitcode(String customerunitcode) {
        this.customerunitcode = customerunitcode == null ? null : customerunitcode.trim();
    }
}
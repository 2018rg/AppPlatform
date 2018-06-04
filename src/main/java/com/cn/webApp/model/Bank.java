package com.cn.webApp.model;

/**
 * 实体类
 * 银行信息字典表
 * @author rg
 * 上午9:43:00
 */
public class Bank extends BaseObject{
    private int id;

    private String bankname;

    private String describe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname == null ? null : bankname.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }
}
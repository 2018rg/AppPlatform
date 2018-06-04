package com.cn.webApp.model;
/**
 * 实体类
 * 登录限制类型
 * @author rg
 * 上午9:49:00
 */
public class LoginlimitType extends BaseObject{
    private Integer typeid;

    private String typename;

    private String id;

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename == null ? null : typename.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
}
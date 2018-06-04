package com.cn.webApp.model;
/**
 * app资源信息
 * 实体类
 * @author rg
 * 上午11:58:36
 */
public class AppResource extends BaseObject{
    private String id;

    private String resourcename;

    private String resourcepackageid;

    private String path;

    private String url;

    private Integer state;

    private String version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename == null ? null : resourcename.trim();
    }

    public String getResourcepackageid() {
        return resourcepackageid;
    }

    public void setResourcepackageid(String resourcepackageid) {
        this.resourcepackageid = resourcepackageid == null ? null : resourcepackageid.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }
}
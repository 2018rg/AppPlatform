package com.cn.webApp.controller.base;

import java.security.Timestamp;
import java.util.Date;


import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


public class Msg {

	  private boolean success = false;
	  private String msg;
	  private String code;
	  private Object data;

	  public Msg()
	  {
	  }

	  public Msg(String msg)
	  {
	    this(true, msg);
	  }

	  public Msg(boolean success, String msg)
	  {
	    this(success, msg, null);
	  }

	  public Msg(String msg, Object data)
	  {
	    this(true, msg, null);
	  }

	  public Msg(boolean success, String msg, Object data)
	  {
	    this.msg = msg;
	    this.success = success;
	    if ((null == data) || ("null".equals(data)))
	      this.data = "";
	    else
	      this.data = data;
	  }

	  public Object getData()
	  {
	    return this.data;
	  }

	  public Msg setData(Object data)
	  {
	    this.data = data;
	    return this;
	  }

	  public String getMsg()
	  {
	    return this.msg;
	  }

	  public Msg setMsg(String msg)
	  {
	    this.msg = msg;
	    return this;
	  }

	  public boolean isSuccess()
	  {
	    return this.success;
	  }

	  public String getCode()
	  {
	    return this.code;
	  }

	  public void setCode(String code) {
	    this.code = code;
	  }

	  public Msg setSuccess(boolean success)
	  {
	    this.success = success;
	    return this;
	  }
	  public JsonConfig getJsonConfig(String dateFormat)
	  {
	    JsonConfig config = new JsonConfig();
	    return config;
	  }

	  public JSONObject toJSONObject()
	  {
	    String format = "yyyy-MM-dd HH:mm:ss";
	    JSONObject jsonObject = new JSONObject();
	    String newMsg = this.msg == null ? "" : this.msg;
	    setMsg(newMsg);
	    jsonObject = JSONObject.fromObject(this, getJsonConfig(format));
	    return jsonObject;
	  }

	  public JSONObject toJSONObject(String dataFormat)
	  {
	    JSONObject jsonObject = new JSONObject();
	    String newMsg = this.msg == null ? "" : this.msg;
	    setMsg(newMsg);
	    jsonObject = JSONObject.fromObject(this, getJsonConfig(dataFormat));
	    return jsonObject;
	  }


}

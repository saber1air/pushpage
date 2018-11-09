package com.deliver.util;

import java.util.HashMap;
import java.util.Map;

public class ResultInfo {
	protected boolean success = false;

	protected Integer code = null;  //用来存储表示特殊含义的信息
	protected String message = null;
	protected Map<String, Object> data = new HashMap<String, Object>();
	
	public ResultInfo(boolean success){
		this.success = success;
	}

	public ResultInfo(String key, Object object){
		this.success = true;
		this.data.put(key, object);
	}
	
	public ResultInfo(boolean success, String key, Object object){
		this.success = success;
		this.data.put(key, object);
	}

	public ResultInfo(int code){
		this.code = code;
	}
	
	public ResultInfo(boolean success, int code){
		this.success = success;
		this.code = code;
	}
	
	public ResultInfo(boolean success, int code, String message){
		this.success = success;
		this.code = code;
		this.message = message;
	}
	
	public ResultInfo(boolean success, String message){
		this.success = success;
		this.message = message;
	}

	public ResultInfo(boolean success, String message, Map<String, Object> data){
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public Map<String, Object> getData(){
		return data;
	}

	public void setData(Map<String, Object> data){
		this.data = data;
	}

	public void setData(String key, Object data){
		this.data.put(key, data);
	}

	public void addData(String key, Object data){
		this.data.put(key, data);
	}

	public Object getData(String key){
		return data.get(key);
	}

	public Integer getCode(){
		return code;
	}

	public void setCode(Integer code){
		this.code = code;
	}
}

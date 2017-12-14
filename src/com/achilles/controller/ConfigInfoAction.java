package com.achilles.controller;

import com.achilles.dto.ConfigInfo;
import com.achilles.service.ConfigInfoService;
import com.opensymphony.xwork2.ActionSupport;

public class ConfigInfoAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8019315138304509903L;
	
	private boolean result;
	private String message;
	
	private ConfigInfo config; 
	
	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ConfigInfo getConfig() {
		return config;
	}

	public void setConfig(ConfigInfo config) {
		this.config = config;
	}

	public String QuerySystemConfig() {
		try {
			ConfigInfoService service = new ConfigInfoService();
			config = service.QuerySystemConfigInfo();
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String SaveSystemConfig() {
		try {
			ConfigInfoService service = new ConfigInfoService();
			service.SaveSystemConfigInfo(config);
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}

}

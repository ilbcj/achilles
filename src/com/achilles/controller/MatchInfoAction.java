package com.achilles.controller;

import java.util.List;

import com.achilles.dto.MatchDayInfo;
import com.achilles.dto.MatchRegistrationInfo;
import com.achilles.service.MatchInfoService;
import com.opensymphony.xwork2.ActionSupport;

public class MatchInfoAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9049095344946867431L;
	
	private boolean result;
	private String message;

	private int draw;
	private long recordsTotal;
	
	private long recordsFiltered;
	private int start;
	private int length;
	
	List<MatchRegistrationInfo> activeRegistrationInfo;
	
	List<MatchDayInfo> activeMatchInfo;
	
	
	public List<MatchDayInfo> getActiveMatchInfo() {
		return activeMatchInfo;
	}

	public void setActiveMatchInfo(List<MatchDayInfo> activeMatchInfo) {
		this.activeMatchInfo = activeMatchInfo;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public List<MatchRegistrationInfo> getActiveRegistrationInfo() {
		return activeRegistrationInfo;
	}

	public void setActiveRegistrationInfo(
			List<MatchRegistrationInfo> activeRegistrationInfo) {
		this.activeRegistrationInfo = activeRegistrationInfo;
	}

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

	public String QueryActiveMatchRegistrationInfo() {
		try {
			MatchInfoService service = new MatchInfoService();
			activeRegistrationInfo = service.QueryActiveMatchRegistrationInfo();
			
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String SaveMatchDetail() {
		//TODO: implement
		return null;
	}
	
	public String QueryActiveMatchInfo() {
		try {
			MatchInfoService service = new MatchInfoService();
			activeMatchInfo = service.QueryActiveMatchInfo();
			
			
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String UpdateMatchInfo() {
		try {
			MatchInfoService service = new MatchInfoService();
			service.UpdateActiveMatchInfo();
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String TestInitRegistration() {
		try {
			MatchInfoService service = new MatchInfoService();
			service.TestInitRegistration();
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}

}

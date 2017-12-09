package com.achilles.controller;

import java.util.ArrayList;
import java.util.List;

import com.achilles.model.Player;
import com.achilles.service.AuthService;
import com.achilles.service.PlayerInfoService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PlayerInfoAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5736887056159386212L;

	private boolean result;
	private String message;
	
	private int draw;
	private long recordsTotal;
	
	private long recordsFiltered;
	private int start;
	private int length;
	
	private String loginId;
	private String name;
	private List<Player> items;
	
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
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Player> getItems() {
		return items;
	}
	public void setItems(List<Player> items) {
		this.items = items;
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

	public String QueryPlayers() {
		
		try {
			PlayerInfoService service = new PlayerInfoService();
			items = new ArrayList<Player>();
			recordsTotal = service.QueryPlayer(loginId, name, start, length, items);
			recordsFiltered = recordsTotal;
			draw = draw + 1;
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
}

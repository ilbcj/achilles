package com.achilles.controller;

import java.util.ArrayList;
import java.util.List;

import com.achilles.dto.PlayerDetail;
import com.achilles.model.Player;
import com.achilles.service.PlayerInfoService;
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
	
	private Player player;
	private List<Integer> delIds;
	
	private int id;
	private PlayerDetail detail;
	
	public PlayerDetail getDetail() {
		return detail;
	}
	public void setDetail(PlayerDetail detail) {
		this.detail = detail;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Integer> getDelIds() {
		return delIds;
	}
	public void setDelIds(List<Integer> delIds) {
		this.delIds = delIds;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
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
	
	public String SavePlayer() {
		try {
			if(player == null) 
				throw new Exception("选手数据错误，无法保存。");
			if(player.getLoginId() == null || player.getLoginId().length() == 0) 
				throw new Exception("选手账号不能为空");
			if(player.getPwd() == null || player.getPwd().length() == 0) 
				throw new Exception("选手口令不能为空");
			PlayerInfoService service = new PlayerInfoService();
			service.SavePlayer(player);
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String DeletePlayer() {
		try {
			if(delIds == null) 
				throw new Exception("没有指定要删除的选手。");
			PlayerInfoService service = new PlayerInfoService();
			service.DeletePlayers(delIds);
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String QueryPlayerDetail() {
		try {
			PlayerInfoService service = new PlayerInfoService();
			detail = service.QueryPlayerDetailInfo(id);
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String TestInitPlayers() {
		try {
			PlayerInfoService service = new PlayerInfoService();
			service.TestInitPlayers();
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
}

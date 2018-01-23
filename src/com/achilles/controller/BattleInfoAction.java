package com.achilles.controller;

import java.util.List;

import com.achilles.model.Battle;
import com.achilles.service.BattleInfoService;
import com.opensymphony.xwork2.ActionSupport;

public class BattleInfoAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8019315138304509903L;
	
	private boolean result;
	private String message;
	
	private List<Battle> battles; 
	private int battleId;
	private String repName;
	
	public List<Battle> getBattles() {
		return battles;
	}

	public void setBattles(List<Battle> battles) {
		this.battles = battles;
	}

	public int getBattleId() {
		return battleId;
	}

	public void setBattleId(int battleId) {
		this.battleId = battleId;
	}

	public String getRepName() {
		return repName;
	}

	public void setRepName(String repName) {
		this.repName = repName;
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

	
	public String QueryBattleInfo() {
		try {
			BattleInfoService service = new BattleInfoService();
			battles = service.QueryCurrentRoundBattleInfos();
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String SaveBattleRepInfo() {
		try {
			BattleInfoService service = new BattleInfoService();
			service.SaveBattleRepName(battleId, repName);
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}

}

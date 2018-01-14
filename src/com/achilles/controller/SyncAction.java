package com.achilles.controller;

import java.util.ArrayList;
import java.util.List;

import com.achilles.dto.MatchDayInfo;
import com.achilles.dto.MatchRegistrationInfo;
import com.achilles.dto.MatchRegistrationInfoForEdit;
import com.achilles.model.Plat;
import com.achilles.model.Round;
import com.achilles.model.Season;
import com.achilles.service.MatchInfoService;
import com.achilles.service.PlatService;
import com.achilles.service.RoundInfoService;
import com.achilles.service.SeasonInfoService;
import com.opensymphony.xwork2.ActionSupport;

public class SyncAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8019315138304509903L;
	
	private boolean result;
	private String message;
	
	private int playerId;
	private MatchRegistrationInfo regInfo;
	private MatchRegistrationInfoForEdit regInfoForEdit;
	private List<Plat> items;
	private Season season;
	private Round round;
	private List<MatchDayInfo> activeMatchInfo;
	
	public List<MatchDayInfo> getActiveMatchInfo() {
		return activeMatchInfo;
	}

	public void setActiveMatchInfo(List<MatchDayInfo> activeMatchInfo) {
		this.activeMatchInfo = activeMatchInfo;
	}

	public MatchRegistrationInfoForEdit getRegInfoForEdit() {
		return regInfoForEdit;
	}

	public void setRegInfoForEdit(MatchRegistrationInfoForEdit regInfoForEdit) {
		this.regInfoForEdit = regInfoForEdit;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public List<Plat> getItems() {
		return items;
	}

	public void setItems(List<Plat> items) {
		this.items = items;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
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

	public MatchRegistrationInfo getRegInfo() {
		return regInfo;
	}

	public void setRegInfo(MatchRegistrationInfo regInfo) {
		this.regInfo = regInfo;
	}

	public String SavePlayerMatchRegistrationInfo() {
		try {
			MatchInfoService service = new MatchInfoService();
			MatchRegistrationInfo oriRecord = service.QueryActiveMatchRegistrationInfoByPlayerId( regInfo.getPlayerId() );
			oriRecord.setAdversaryIds(regInfo.getAdversaryIds());
			oriRecord.setPlatIds(regInfo.getPlatIds());
			oriRecord.setDayIds(regInfo.getDayIds());
			service.SavePlayerMatchRegistrationInfo(oriRecord);
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String QueryPlayerMatchRegistrationInfo() {
		try {
			MatchInfoService service = new MatchInfoService();
			regInfo = service.QueryActiveMatchRegistrationInfoByPlayerId( playerId );
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String QueryPlayerMatchRegistrationInfoForEdit() {
		try {
			MatchInfoService service = new MatchInfoService();
			regInfoForEdit = service.QueryMatchRegistrationInfoForEditByPlayerId(playerId);
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String QueryPlatList() {
		try {
			PlatService service = new PlatService();
			items = service.QueryAllMaps();
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String QuerySeasonRound() {
		try {
			RoundInfoService rs = new RoundInfoService();
			round = rs.GetActiveRound();
			SeasonInfoService ss = new SeasonInfoService();
			season = ss.QuerySeasonsById( round.getSeasonId() );
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String QueryActiveMatchInfo() {
		try {
			MatchInfoService service = new MatchInfoService();
			activeMatchInfo = service.QueryActiveMatchInfo();
		} catch (Exception e) {
			activeMatchInfo = new ArrayList<MatchDayInfo>();
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}

}

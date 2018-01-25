package com.achilles.controller;

import java.util.ArrayList;
import java.util.List;

import com.achilles.dto.MatchDayInfo;
import com.achilles.dto.MatchRegistrationInfo;
import com.achilles.dto.MatchRegistrationInfoForEdit;
import com.achilles.model.Battle;
import com.achilles.model.MatchInfo;
import com.achilles.model.Plat;
import com.achilles.service.MatchInfoService;
import com.achilles.service.PlatService;
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
	
	private List<MatchRegistrationInfo> activeRegistrationInfo;
	
	private int playerId;
	private int adversaryId;
	private MatchRegistrationInfoForEdit regInfoForEdit;
	private List<Plat> plats;
	private MatchRegistrationInfo regInfoForSave;
	
	private List<MatchDayInfo> activeMatchInfo;
	private List<Battle> battleInMatchInfo;
	private MatchInfo matchInfoDetail;	
	
	private boolean allResultSaved;
	
	public int getAdversaryId() {
		return adversaryId;
	}

	public void setAdversaryId(int adversaryId) {
		this.adversaryId = adversaryId;
	}

	public List<Plat> getPlats() {
		return plats;
	}

	public void setPlats(List<Plat> plats) {
		this.plats = plats;
	}
	
	public List<Battle> getBattleInMatchInfo() {
		return battleInMatchInfo;
	}

	public void setBattleInMatchInfo(List<Battle> battleInMatchInfo) {
		this.battleInMatchInfo = battleInMatchInfo;
	}

	public MatchInfo getMatchInfoDetail() {
		return matchInfoDetail;
	}

	public void setMatchInfoDetail(MatchInfo matchInfoDetail) {
		this.matchInfoDetail = matchInfoDetail;
	}

	public MatchRegistrationInfo getRegInfoForSave() {
		return regInfoForSave;
	}

	public void setRegInfoForSave(MatchRegistrationInfo regInfoForSave) {
		this.regInfoForSave = regInfoForSave;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public MatchRegistrationInfoForEdit getRegInfoForEdit() {
		return regInfoForEdit;
	}

	public void setRegInfoForEdit(MatchRegistrationInfoForEdit regInfoForEdit) {
		this.regInfoForEdit = regInfoForEdit;
	}

	public boolean isAllResultSaved() {
		return allResultSaved;
	}

	public void setAllResultSaved(boolean allResultSaved) {
		this.allResultSaved = allResultSaved;
	}

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
	
	public String QueryMatchRegistrationDetailForEdit() {
		try {
			MatchInfoService service = new MatchInfoService();
			regInfoForEdit = service.QueryMatchRegistrationInfoForEditByPlayerId(playerId);
			PlatService ps = new PlatService();
			plats = ps.QueryAllMaps();
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String SaveMatchRegistrationDetail() {
		try {
			MatchInfoService service = new MatchInfoService();
			service.SavePlayerMatchRegistrationInfo(regInfoForSave);
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
	
	public String QueryMatchInfoDetailForEdit() {
		try {
			MatchInfoService service = new MatchInfoService();
			battleInMatchInfo = service.QueryMatchInfoForEditByPlayerIdAndAdversaryId(playerId, adversaryId);
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String SaveMatchInfoDetail() {
		try {
			MatchInfoService service = new MatchInfoService();
			service.SaveMatchInfoDetail(matchInfoDetail, battleInMatchInfo);
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
	
	public String TestCreateMatchResult() {
		try {
			MatchInfoService service = new MatchInfoService();
			service.TestCreateMatchResult();
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}	
}

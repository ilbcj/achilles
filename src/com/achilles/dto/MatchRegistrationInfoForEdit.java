package com.achilles.dto;

import java.util.List;

import com.achilles.model.Player;

public class MatchRegistrationInfoForEdit {
	private int playerId;
	private int matchPeriodId;
	private List<Player> adversaries;
	
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public int getMatchPeriodId() {
		return matchPeriodId;
	}
	public void setMatchPeriodId(int matchPeriodId) {
		this.matchPeriodId = matchPeriodId;
	}
	public List<Player> getAdversaries() {
		return adversaries;
	}
	public void setAdversaries(List<Player> adversaries) {
		this.adversaries = adversaries;
	}
}

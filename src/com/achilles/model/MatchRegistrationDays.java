package com.achilles.model;

public class MatchRegistrationDays {
	private int id;
	private int playerId;
	private int matchPeriodId;
	private int freeDay;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public int getFreeDay() {
		return freeDay;
	}
	public void setFreeDay(int freeDay) {
		this.freeDay = freeDay;
	}
}

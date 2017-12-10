package com.achilles.model;

public class MatchInfo {
	private int id;
	private int matchPeriodId;
	private int dayId;
	private int challengerId;
	private int adversaryId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMatchPeriodId() {
		return matchPeriodId;
	}
	public void setMatchPeriodId(int matchPeriodId) {
		this.matchPeriodId = matchPeriodId;
	}
	public int getDayId() {
		return dayId;
	}
	public void setDayId(int dayId) {
		this.dayId = dayId;
	}
	public int getChallengerId() {
		return challengerId;
	}
	public void setChallengerId(int challengerId) {
		this.challengerId = challengerId;
	}
	public int getAdversaryId() {
		return adversaryId;
	}
	public void setAdversaryId(int adversaryId) {
		this.adversaryId = adversaryId;
	}
}

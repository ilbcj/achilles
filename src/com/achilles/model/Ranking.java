package com.achilles.model;

public class Ranking {
	private int id;
	private int matchPeriodId;
	private int ranking;
	private int playerId;

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
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
}

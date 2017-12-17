package com.achilles.model;

public class MatchRegistrationDays {
	private int id;
	private int playerId;
	private int roundId;
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
	public int getRoundId() {
		return roundId;
	}
	public void setRoundId(int roundId) {
		this.roundId = roundId;
	}
	public int getFreeDay() {
		return freeDay;
	}
	public void setFreeDay(int freeDay) {
		this.freeDay = freeDay;
	}
}

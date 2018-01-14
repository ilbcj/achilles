package com.achilles.model;

public class MatchRegistrationAdversary {
	private int id;
	private int playerId;
	private int roundId;
	private int adversaryId;
	private String platId;
	
	public String getPlatId() {
		return platId;
	}
	public void setPlatId(String platId) {
		this.platId = platId;
	}
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
	public int getAdversaryId() {
		return adversaryId;
	}
	public void setAdversaryId(int adversaryId) {
		this.adversaryId = adversaryId;
	}
}

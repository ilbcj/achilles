package com.achilles.model;

public class Score {
	private int id;
	private int matchPeriodId;
	private int playerId;
	private int lastScore;
	private int challengerWin;
	private int challengerLose;
	private int adversaryWin;
	private int adversaryLose;
	private int attendance;
	private int reward;
	private int score;
	private String memo;

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
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public int getLastScore() {
		return lastScore;
	}
	public void setLastScore(int lastScore) {
		this.lastScore = lastScore;
	}
	public int getChallengerWin() {
		return challengerWin;
	}
	public void setChallengerWin(int challengerWin) {
		this.challengerWin = challengerWin;
	}
	public int getChallengerLose() {
		return challengerLose;
	}
	public void setChallengerLose(int challengerLose) {
		this.challengerLose = challengerLose;
	}
	public int getAdversaryWin() {
		return adversaryWin;
	}
	public void setAdversaryWin(int adversaryWin) {
		this.adversaryWin = adversaryWin;
	}
	public int getAdversaryLose() {
		return adversaryLose;
	}
	public void setAdversaryLose(int adversaryLose) {
		this.adversaryLose = adversaryLose;
	}
	public int getAttendance() {
		return attendance;
	}
	public void setAttendance(int attendance) {
		this.attendance = attendance;
	}
	public int getReward() {
		return reward;
	}
	public void setReward(int reward) {
		this.reward = reward;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}

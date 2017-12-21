package com.achilles.model;

public class Score {
	public final static int ABSENT_YES = 1;
	public final static int ABSENT_NO = 0;
	
	private int id;
	private int roundId;
	private int playerId;
	private int lastScore;
	private int challengerWin;
	private int challengerLose;
	private int adversaryWin;
	private int adversaryLose;
	private int absent;
	private int rewardAbandon;
	private int rewardSponsor;
	private int reward1;
	private int reward2;
	private int reward3;
	private int score;
	private String memo;
	private String rewardSponsorReason;

	public String getRewardSponsorReason() {
		return rewardSponsorReason;
	}
	public void setRewardSponsorReason(String rewardSponsorReason) {
		this.rewardSponsorReason = rewardSponsorReason;
	}
	public int getRoundId() {
		return roundId;
	}
	public void setRoundId(int roundId) {
		this.roundId = roundId;
	}
	public int getRewardAbandon() {
		return rewardAbandon;
	}
	public void setRewardAbandon(int rewardAbandon) {
		this.rewardAbandon = rewardAbandon;
	}
	public int getRewardSponsor() {
		return rewardSponsor;
	}
	public void setRewardSponsor(int rewardSponsor) {
		this.rewardSponsor = rewardSponsor;
	}
	public int getReward1() {
		return reward1;
	}
	public void setReward1(int reward1) {
		this.reward1 = reward1;
	}
	public int getReward2() {
		return reward2;
	}
	public void setReward2(int reward2) {
		this.reward2 = reward2;
	}
	public int getReward3() {
		return reward3;
	}
	public void setReward3(int reward3) {
		this.reward3 = reward3;
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
	public int getAbsent() {
		return absent;
	}
	public void setAbsent(int absent) {
		this.absent = absent;
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
	
	//non persistence attribute
	private String playerName;
	private int ranking;

	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
}

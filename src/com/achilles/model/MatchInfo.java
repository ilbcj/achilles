package com.achilles.model;

public class MatchInfo {
	public final static int RESULT_CHALLENGER_WIN = 1;
	public final static int RESULT_ADVERSARY_WIN = 2;
	public final static int RESULT_DRAW = 3;
	public final static int RESULT_CHALLENGER_ABSENT = 4;
	public final static int RESULT_ADVERSARY_ABSENT = 5;
	
	private int id;
	private int matchPeriodId;
	private int dayId;
	private int challengerId;
	private int challengerVranking;
	private int adversaryId;
	private int adversaryVranking;
	private String score;
	private int result;
	
	public int getChallengerVranking() {
		return challengerVranking;
	}
	public void setChallengerVranking(int challengerVranking) {
		this.challengerVranking = challengerVranking;
	}
	public int getAdversaryVranking() {
		return adversaryVranking;
	}
	public void setAdversaryVranking(int adversaryVranking) {
		this.adversaryVranking = adversaryVranking;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
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
	
	//non persistence attribute
	private String challengerName;
	private String adversaryName;

	public String getChallengerName() {
		return challengerName;
	}
	public void setChallengerName(String challengerName) {
		this.challengerName = challengerName;
	}
	public String getAdversaryName() {
		return adversaryName;
	}
	public void setAdversaryName(String adversaryName) {
		this.adversaryName = adversaryName;
	}
}

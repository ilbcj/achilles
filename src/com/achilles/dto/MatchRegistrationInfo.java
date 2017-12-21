package com.achilles.dto;

import java.util.List;

public class MatchRegistrationInfo {
	private int playerId;
	private String loginId;
	private String name;
	private String race;
	private int scoreReward;
	private String scoreRewardMemo;
	private List<Integer> adversaryIds;
	private String adversaries;
	private List<Integer> dayIds;
	private String days;
	
	public String getScoreRewardMemo() {
		return scoreRewardMemo;
	}
	public void setScoreRewardMemo(String scoreRewardMemo) {
		this.scoreRewardMemo = scoreRewardMemo;
	}
	public int getScoreReward() {
		return scoreReward;
	}
	public void setScoreReward(int scoreReward) {
		this.scoreReward = scoreReward;
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public List<Integer> getAdversaryIds() {
		return adversaryIds;
	}
	public void setAdversaryIds(List<Integer> adversaryIds) {
		this.adversaryIds = adversaryIds;
	}
	public String getAdversaries() {
		return adversaries;
	}
	public void setAdversaries(String adversaries) {
		this.adversaries = adversaries;
	}
	public List<Integer> getDayIds() {
		return dayIds;
	}
	public void setDayIds(List<Integer> dayIds) {
		this.dayIds = dayIds;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
}

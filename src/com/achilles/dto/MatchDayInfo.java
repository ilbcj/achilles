package com.achilles.dto;

public class MatchDayInfo {
	private int dayId;
	private String dayName;
	private String matchInfo;
	
	public int getDayId() {
		return dayId;
	}
	public void setDayId(int dayId) {
		this.dayId = dayId;
	}
	public String getDayName() {
		return dayName;
	}
	public void setDayName(String dayName) {
		this.dayName = dayName;
	}
	public String getMatchInfo() {
		return matchInfo;
	}
	public void setMatchInfo(String matchInfo) {
		this.matchInfo = matchInfo;
	}
}

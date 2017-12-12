package com.achilles.dto;

import java.util.List;

import com.achilles.model.MatchInfo;

public class MatchDayInfo {
	private int dayId;
	private String dayName;
	private List<MatchInfo> matchInfo;
	
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
	public List<MatchInfo> getMatchInfo() {
		return matchInfo;
	}
	public void setMatchInfo(List<MatchInfo> matchInfo) {
		this.matchInfo = matchInfo;
	}
}

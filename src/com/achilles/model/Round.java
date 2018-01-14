package com.achilles.model;

public class Round {
	public final static int STATUS_DEL = 0;
	public final static int STATUS_ACTIVE = 1;
	public final static int STATUS_LAST_ACTIVE = 2;
	public final static int STATUS_HISTORY = 3;
	public final static int STATUS_INIT = 9;
	public final static int PHASE_REGISTRATION = 0;
	public final static int PHASE_ARRANGED = 1;
	public final static int PHASE_CALCULATE = 2;
	
	private int id;
	private String year;
	private String month;
	private String week;
	private String name;
	private String memo;
	private String timestamp;
	private int seasonId;
	private int status;
	private int phase;
	private int lastRoundId;
	
	public int getPhase() {
		return phase;
	}
	public void setPhase(int phase) {
		this.phase = phase;
	}
	public int getLastRoundId() {
		return lastRoundId;
	}
	public void setLastRoundId(int lastRoundId) {
		this.lastRoundId = lastRoundId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getSeasonId() {
		return seasonId;
	}
	public void setSeasonId(int seasonId) {
		this.seasonId = seasonId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	// non persistence attribute
	private String seasonName;
	private String lastRoundName;

	public String getLastRoundName() {
		return lastRoundName;
	}
	public void setLastRoundName(String lastRoundName) {
		this.lastRoundName = lastRoundName;
	}
	public String getSeasonName() {
		return seasonName;
	}
	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}
}

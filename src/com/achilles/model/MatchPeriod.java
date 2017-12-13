package com.achilles.model;

public class MatchPeriod {
	public final static int STATUS_UNKNOW = 0;
	public final static int STATUS_ACTIVE = 1;
	public final static int STATUS_LAST_ACTIVE = 2;
	public final static int STATUS_HISTORY = 3;
	public final static int STATUS_INIT = 9;
	
	private int id;
	private String Year;
	private String Month;
	private String Week;
	private String name;
	private String memo;
	private int status;
	private int lastPeriodId;
	
	public int getLastPeriodId() {
		return lastPeriodId;
	}
	public void setLastPeriodId(int lastPeriodId) {
		this.lastPeriodId = lastPeriodId;
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
		return Year;
	}
	public void setYear(String year) {
		Year = year;
	}
	public String getMonth() {
		return Month;
	}
	public void setMonth(String month) {
		Month = month;
	}
	public String getWeek() {
		return Week;
	}
	public void setWeek(String week) {
		Week = week;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

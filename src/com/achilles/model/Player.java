package com.achilles.model;

import java.util.List;
import java.util.Map;

public class Player {
	public final static int STATUS_USING = 1;
	public final static int STATUS_DEL = 0;
	public final static String RACE_T = "T";
	public final static String RACE_P = "P";
	public final static String RACE_Z = "Z";
	
	private int id;
	private String loginId;
	private String pwd;
	private String name;
	private String race;
	private String tel;
	private String email;
	private String qq;
	private String wechat;
	private String timestamp;
	private int status;
	
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	//non persistence attribute
	private int remainingChallengeTimes;
	private List<Integer> adversaryIds;
	private Map<Integer, Integer> days;
	private int ranking;

	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public int getRemainingChallengeTimes() {
		return remainingChallengeTimes;
	}
	public void setRemainingChallengeTimes(int remainingChallengeTimes) {
		this.remainingChallengeTimes = remainingChallengeTimes;
	}
	public Map<Integer, Integer> getDays() {
		return days;
	}
	public void setDays(Map<Integer, Integer> days) {
		this.days = days;
	}
	public List<Integer> getAdversaryIds() {
		return adversaryIds;
	}
	public void setAdversaryIds(List<Integer> adversaryIds) {
		this.adversaryIds = adversaryIds;
	}
}

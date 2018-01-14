package com.achilles.model;

public class Plat {
	public final static int STATUS_DEL = 0;
	public final static int STATUS_ACTIVE = 1;
	
	private int id;
	private String name;
	private String filePath;
	private String timestamp;
	private int status;
	private int zvp;
	private int tvz;
	private int pvt;
	
	public int getZvp() {
		return zvp;
	}
	public void setZvp(int zvp) {
		this.zvp = zvp;
	}
	public int getPvt() {
		return pvt;
	}
	public int getTvz() {
		return tvz;
	}
	public void setTvz(int tvz) {
		this.tvz = tvz;
	}
	public void setPvt(int pvt) {
		this.pvt = pvt;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	// non persistence attribute
}

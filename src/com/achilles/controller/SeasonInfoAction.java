package com.achilles.controller;

import java.util.ArrayList;
import java.util.List;

import com.achilles.model.Season;
import com.achilles.service.SeasonInfoService;
import com.opensymphony.xwork2.ActionSupport;

public class SeasonInfoAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1757091083695576647L;

	private boolean result;
	private String message;
	
	private int draw;
	private long recordsTotal;
	
	private long recordsFiltered;
	private int start;
	private int length;
	
	private List<Season> items;
	private Season season;
	private List<String>  rankInfo;
	private List<Integer> delIds;
	
	
	public List<String> getRankInfo() {
		return rankInfo;
	}

	public void setRankInfo(List<String> rankInfo) {
		this.rankInfo = rankInfo;
	}

	public List<Integer> getDelIds() {
		return delIds;
	}

	public void setDelIds(List<Integer> delIds) {
		this.delIds = delIds;
	}

	public List<Season> getItems() {
		return items;
	}

	public void setItems(List<Season> items) {
		this.items = items;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String QuerySeasons() {
		try {
			SeasonInfoService service = new SeasonInfoService();
			items = new ArrayList<Season>();
			recordsTotal = service.QuerySeasons(start, length, items);
			recordsFiltered = recordsTotal;
			draw = draw + 1;
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String SaveSeason() {
		try {
			if(season == null || season.getName() == null || season.getName().trim().length() == 0) 
				throw new Exception("赛季数据错误，无法保存。");
			SeasonInfoService service = new SeasonInfoService();
			service.SaveSeason(season);
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String DeleteSeasons() {
		try {
			if(delIds == null) 
				throw new Exception("没有指定要删除的赛季。");
			SeasonInfoService service = new SeasonInfoService();
			service.DeleteSeasons(delIds);
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String InitSeason() {
		try {
			SeasonInfoService service = new SeasonInfoService();
			rankInfo = service.InitSeasons();
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
}

package com.achilles.controller;

import java.util.ArrayList;
import java.util.List;

import com.achilles.model.Round;
import com.achilles.service.RoundInfoService;
import com.opensymphony.xwork2.ActionSupport;

public class RoundInfoAction extends ActionSupport {

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
	
	private List<Round> items;
	private Round round;
	private List<Integer> delIds;
	
	private int roundId;
	
	public int getRoundId() {
		return roundId;
	}

	public void setRoundId(int roundId) {
		this.roundId = roundId;
	}

	public List<Integer> getDelIds() {
		return delIds;
	}

	public void setDelIds(List<Integer> delIds) {
		this.delIds = delIds;
	}

	public List<Round> getItems() {
		return items;
	}

	public void setItems(List<Round> items) {
		this.items = items;
	}

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
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

	public String QueryRounds() {
		try {
			RoundInfoService service = new RoundInfoService();
			items = new ArrayList<Round>();
			recordsTotal = service.QueryRounds(start, length, items);
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
	
	public String SaveRound() {
		try {
			if(round == null || round.getSeasonId() == 0 || round.getName() == null || round.getName().trim().length() == 0) 
				throw new Exception("场次数据错误，无法保存。");
			RoundInfoService service = new RoundInfoService();
			service.SaveRound(round);
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String DeleteRounds() {
		try {
			if(delIds == null) 
				throw new Exception("没有指定要删除的场次。");
			RoundInfoService service = new RoundInfoService();
			service.DeleteRounds(delIds);
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String ArchiveRound() {
		try {
			if( roundId == 0 ) 
				throw new Exception( "没有指定要归档的场次。" );
			RoundInfoService service = new RoundInfoService();
			service.ArchiveRound( roundId );
		} catch (Exception e) {
			message = e.getMessage();
			setResult( false );
			return SUCCESS;
		}
		setResult( true );
		return SUCCESS;
	}
}

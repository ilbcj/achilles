package com.achilles.controller;

import java.util.List;

import com.achilles.model.Round;
import com.achilles.model.Score;
import com.achilles.service.RoundInfoService;
import com.achilles.service.ScoreInfoService;
import com.opensymphony.xwork2.ActionSupport;

public class ScoreInfoAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8019315138304509903L;
	
	private boolean result;
	private String message;
	
	private List<Round> rounds; 
	private int roundId;
	private List<Score> items;
	
	public List<Score> getItems() {
		return items;
	}

	public void setItems(List<Score> items) {
		this.items = items;
	}

	public int getRoundId() {
		return roundId;
	}

	public void setRoundId(int roundId) {
		this.roundId = roundId;
	}

	public List<Round> getRounds() {
		return rounds;
	}

	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
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



	public String QueryRoundList() {
		try {
			RoundInfoService service = new RoundInfoService();
			rounds = service.QueryRoundListForDisplayScore();
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}
	
	public String QueryRoundScore() {
		try {
			ScoreInfoService service = new ScoreInfoService();
			items = service.QueryRoundCurrentByRanking(roundId);
		} catch (Exception e) {
			message = e.getMessage();
			setResult(false);
			return SUCCESS;
		}
		setResult(true);
		return SUCCESS;
	}

}

package com.achilles.dto;

public class ConfigInfo implements Cloneable {
	private int maxChallengeCount;// = 5;
	private int maxPlayersCount;// = 50;
	private int maxDateRange;// = 6;
	private int initRoundId;// = 1;
	private int maxPercentOfChallengerWin;// = 60;
	private int percentOfChallengerWinDiminishingStep;// = 5;
	private int maxInitTopOneScore;// = 1050;
	private int initScoreDiminishingStep;// = 1;
	private int rateOfChosenMondyToThursday;// = 30;
	private int rateOfChosenSaturdayToSunday;// = 80;
	private int firstPlayerAcceptChallengeCount;// = 2;
	private int minAcceptChallengeCount;// = 1;
	private String bonusPlat;
	private int bonusPlatScore;
	private String playerNotice;
	
	public ConfigInfo() {
		
	}
	
	public ConfigInfo(ConfigInfo ci) {
		this.maxChallengeCount = ci.getMaxChallengeCount();
		this.maxPlayersCount = ci.getMaxPlayersCount();
		this.maxDateRange = ci.getMaxDateRange();
		this.initRoundId = ci.getInitRoundId();
		this.maxPercentOfChallengerWin = ci.getMaxPercentOfChallengerWin();
		this.percentOfChallengerWinDiminishingStep = ci.getPercentOfChallengerWinDiminishingStep();
		this.maxInitTopOneScore = ci.getMaxInitTopOneScore();
		this.initScoreDiminishingStep = ci.getInitScoreDiminishingStep();
		this.rateOfChosenMondyToThursday = ci.getRateOfChosenMondyToThursday();
		this.rateOfChosenSaturdayToSunday = ci.getRateOfChosenSaturdayToSunday();
		this.firstPlayerAcceptChallengeCount = ci.getFirstPlayerAcceptChallengeCount();
		this.minAcceptChallengeCount = ci.getMinAcceptChallengeCount();
		this.bonusPlat = ci.getBonusPlat();
		this.bonusPlatScore = ci.getBonusPlatScore();
		this.playerNotice = ci.getPlayerNotice();
	}
	
	public String getPlayerNotice() {
		return playerNotice;
	}

	public void setPlayerNotice(String playerNotice) {
		this.playerNotice = playerNotice;
	}

	public int getBonusPlatScore() {
		return bonusPlatScore;
	}

	public void setBonusPlatScore(int bonusPlatScore) {
		this.bonusPlatScore = bonusPlatScore;
	}

	public String getBonusPlat() {
		return bonusPlat;
	}

	public void setBonusPlat(String bonusPlat) {
		this.bonusPlat = bonusPlat;
	}

	public int getFirstPlayerAcceptChallengeCount() {
		return firstPlayerAcceptChallengeCount;
	}
	public void setFirstPlayerAcceptChallengeCount(
			int firstPlayerAcceptChallengeCount) {
		this.firstPlayerAcceptChallengeCount = firstPlayerAcceptChallengeCount;
	}
	public int getMinAcceptChallengeCount() {
		return minAcceptChallengeCount;
	}
	public void setMinAcceptChallengeCount(int minAcceptChallengeCount) {
		this.minAcceptChallengeCount = minAcceptChallengeCount;
	}
	public int getMaxChallengeCount() {
		return maxChallengeCount;
	}
	public void setMaxChallengeCount(int maxChallengeCount) {
		this.maxChallengeCount = maxChallengeCount;
	}
	public int getMaxPlayersCount() {
		return maxPlayersCount;
	}
	public void setMaxPlayersCount(int maxPlayersCount) {
		this.maxPlayersCount = maxPlayersCount;
	}
	public int getMaxDateRange() {
		return maxDateRange;
	}
	public void setMaxDateRange(int maxDateRange) {
		this.maxDateRange = maxDateRange;
	}
	public int getInitRoundId() {
		return initRoundId;
	}

	public void setInitRoundId(int initRoundId) {
		this.initRoundId = initRoundId;
	}

	public int getMaxPercentOfChallengerWin() {
		return maxPercentOfChallengerWin;
	}
	public void setMaxPercentOfChallengerWin(int maxPercentOfChallengerWin) {
		this.maxPercentOfChallengerWin = maxPercentOfChallengerWin;
	}
	public int getPercentOfChallengerWinDiminishingStep() {
		return percentOfChallengerWinDiminishingStep;
	}
	public void setPercentOfChallengerWinDiminishingStep(
			int percentOfChallengerWinDiminishingStep) {
		this.percentOfChallengerWinDiminishingStep = percentOfChallengerWinDiminishingStep;
	}
	public int getMaxInitTopOneScore() {
		return maxInitTopOneScore;
	}
	public void setMaxInitTopOneScore(int maxInitTopOneScore) {
		this.maxInitTopOneScore = maxInitTopOneScore;
	}
	public int getInitScoreDiminishingStep() {
		return initScoreDiminishingStep;
	}
	public void setInitScoreDiminishingStep(int initScoreDiminishingStep) {
		this.initScoreDiminishingStep = initScoreDiminishingStep;
	}
	public int getRateOfChosenMondyToThursday() {
		return rateOfChosenMondyToThursday;
	}
	public void setRateOfChosenMondyToThursday(int rateOfChosenMondyToThursday) {
		this.rateOfChosenMondyToThursday = rateOfChosenMondyToThursday;
	}
	public int getRateOfChosenSaturdayToSunday() {
		return rateOfChosenSaturdayToSunday;
	}
	public void setRateOfChosenSaturdayToSunday(int rateOfChosenSaturdayToSunday) {
		this.rateOfChosenSaturdayToSunday = rateOfChosenSaturdayToSunday;
	}
	
	// Copy data
	@Override
    public Object clone()  {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e){
            return new ConfigInfo(this);
        }
    }
}

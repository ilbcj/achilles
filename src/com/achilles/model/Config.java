package com.achilles.model;

public class Config {
	public final static String NAME_MAXCHALLENGECOUNT = "MaxChallengeCount";//5
	public final static String NAME_MAXPLAYERSCOUNT = "MaxPlayersCount";//50
	public final static String NAME_MAXDATERANGE = "MaxDateRange";//6
	public final static String NAME_INITROUNDID = "InitRoundId";//1
	public final static String NAME_MAXPERCENTOFCHALLENGERWIN = "MaxPercentOfChallengerWin";//60
	public final static String NAME_PERCENTOFCHALLENGERWINDIMINISHINGSTEP = "PercentOfChallengerWinDiminishingStep";//5
	public final static String NAME_MAXINITTOPONESCORE = "MaxInitTopOneScore";//1050
	public final static String NAME_INITSCOREDIMINISHINGSTEP = "InitScoreDiminishingStep";//1
	public final static String NAME_RATEOFCHOSENMONDYTOTHURSDAY = "RateOfChosenMondyToThursday";//30
	public final static String NAME_RATEOFCHOSENSATURDAYTOSUNDAY = "RateOfChosenSaturdayToSunday";//80
	public final static String NAME_FIRSTPLAYERACCEPTCHALLENGECOUNT = "FirstPlayerAcceptChallengeCount";//2
	public final static String NAME_MINACCEPTCHALLENGECOUNT = "MinAcceptChallengeCount";//1
	public final static String NAME_ACTIVEPLAT = "ActivePlat";
	public final static String NAME_BONUSPLAT = "BonusPlat";
	public final static String NAME_BONUSPLATSCORE = "BonusPlatScore";
	public final static String NAME_PLAYERNOTICE = "PlayerNotice";
	
	private int id;
	private String name;
	private String sval;
	private int ival;
	
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
	public String getSval() {
		return sval;
	}
	public void setSval(String sval) {
		this.sval = sval;
	}
	public int getIval() {
		return ival;
	}
	public void setIval(int ival) {
		this.ival = ival;
	}
}



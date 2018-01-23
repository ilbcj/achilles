package com.achilles.model;

public class Battle {
	public static final int RESULT_CHALLENGER_WIN = 1;
	public static final int RESULT_ADVERSARY_WIN = 2;
	public static final int RESULT_DRAW = 3;
	public static final int RESULT_CHALLENGER_ABSENT = 4;
	public static final int RESULT_ADVERSARY_ABSENT = 5;
	
	private int id;
	private int challengerId;
	private String challengerName;
	private String challengerLoginId;
	private String challengerRace;
	private int challengerRank;
	private int result;
	private int adversaryId;
	private String adversaryName;
	private String adversaryLoginId;
	private String adversaryRace;
	private int adversaryRank;
	private int mapId;
	private String mapName;
	private String vod;
	private String memo;
	private int roundId;
	private String timestamp;
	
	public int getRoundId() {
		return roundId;
	}
	public void setRoundId(int roundId) {
		this.roundId = roundId;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getMapId() {
		return mapId;
	}
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	public String getVod() {
		return vod;
	}
	public void setVod(String vod) {
		this.vod = vod;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
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
	public int getChallengerId() {
		return challengerId;
	}
	public void setChallengerId(int challengerId) {
		this.challengerId = challengerId;
	}
	public String getChallengerName() {
		return challengerName;
	}
	public void setChallengerName(String challengerName) {
		this.challengerName = challengerName;
	}
	public String getChallengerLoginId() {
		return challengerLoginId;
	}
	public void setChallengerLoginId(String challengerLoginId) {
		this.challengerLoginId = challengerLoginId;
	}
	public String getChallengerRace() {
		return challengerRace;
	}
	public void setChallengerRace(String challengerRace) {
		this.challengerRace = challengerRace;
	}
	public int getChallengerRank() {
		return challengerRank;
	}
	public void setChallengerRank(int challengerRank) {
		this.challengerRank = challengerRank;
	}
	public int getAdversaryId() {
		return adversaryId;
	}
	public void setAdversaryId(int adversaryId) {
		this.adversaryId = adversaryId;
	}
	public String getAdversaryName() {
		return adversaryName;
	}
	public void setAdversaryName(String adversaryName) {
		this.adversaryName = adversaryName;
	}
	public String getAdversaryLoginId() {
		return adversaryLoginId;
	}
	public void setAdversaryLoginId(String adversaryLoginId) {
		this.adversaryLoginId = adversaryLoginId;
	}
	public String getAdversaryRace() {
		return adversaryRace;
	}
	public void setAdversaryRace(String adversaryRace) {
		this.adversaryRace = adversaryRace;
	}
	public int getAdversaryRank() {
		return adversaryRank;
	}
	public void setAdversaryRank(int adversaryRank) {
		this.adversaryRank = adversaryRank;
	}	
}

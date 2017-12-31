package com.achilles.dao;

import java.util.List;

import com.achilles.model.MatchInfo;
import com.achilles.model.MatchRegistrationAdversary;
import com.achilles.model.MatchRegistrationDays;

public interface MatchDAO {
	//MatchPeriod
	public boolean IsRoundUsedInMatch(int roundId) throws Exception;
	
	//MatchRegistrationAdversary
	public void ClearAdversaries(int roundId, int playerId) throws Exception;
	public MatchRegistrationAdversary SaveMatchRegistrationAdversary(MatchRegistrationAdversary adversary) throws Exception;
	public List<MatchRegistrationAdversary> GetRegistrationAdversaryByPlayer(int roundId, int playerId) throws Exception;
	
	//MatchRegistrationDays
	public void ClearDays(int roundId, int playerId) throws Exception;
	public MatchRegistrationDays SaveMatchRegistrationDay(MatchRegistrationDays day) throws Exception;
	public List<MatchRegistrationDays> GetRegistrationDayByPlayer(int roundId, int playerId) throws Exception;
	
	//MatchInfo
	public void ClearMatchInfos(int roundId) throws Exception;
	public List<MatchInfo> GetMatchInfosByAdversary(int roundId, int adversaryId) throws Exception;
	public MatchInfo SaveMatchInfo(MatchInfo matchinfo) throws Exception;
	public MatchInfo GetMatchInfoById(int id) throws Exception;
	public List<MatchInfo> GetActiveMatchInfo() throws Exception;
	public List<MatchInfo> GetActiveMatchInfoByChallenger(int playerId) throws Exception;
	public List<MatchInfo> GetActiveMatchInfoByadversary(int playerId) throws Exception;
	public int GetMaxMatchCountByPlayer(int roundId) throws Exception;
	
}

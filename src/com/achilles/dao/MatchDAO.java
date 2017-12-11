package com.achilles.dao;

import java.util.List;

import com.achilles.model.MatchInfo;
import com.achilles.model.MatchPeriod;
import com.achilles.model.MatchRegistrationAdversary;
import com.achilles.model.MatchRegistrationDays;

public interface MatchDAO {
	//MatchPeriod
	public MatchPeriod GetLastActivePeriod() throws Exception;
	public MatchPeriod GetActivePeriod() throws Exception;
	
	//MatchRegistrationAdversary
	public void ClearAdversaries(int matchPeriodId, int playerId) throws Exception;
	public MatchRegistrationAdversary SaveMatchRegistrationAdversary(MatchRegistrationAdversary adversary) throws Exception;
	public List<MatchRegistrationAdversary> GetRegistrationAdversaryByPlayer(int matchPeriodId, int playerId) throws Exception;
	
	//MatchRegistrationDays
	public void ClearDays(int matchPeriodId, int playerId) throws Exception;
	public MatchRegistrationDays SaveMatchRegistrationDay(MatchRegistrationDays day) throws Exception;
	public List<MatchRegistrationDays> GetRegistrationDayByPlayer(int matchPeriodId, int playerId) throws Exception;
	
	//MatchInfo
	public void ClearMatchInfos(int matchPeriodId) throws Exception;
	public List<MatchInfo> GetMatchInfosByAdversary(int matchPeriodId, int adversaryId) throws Exception;
	public MatchInfo SaveMatchInfo(MatchInfo matchinfo) throws Exception;
	public List<MatchInfo> GetActiveMatchInfo() throws Exception;
}

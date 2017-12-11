package com.achilles.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.achilles.dao.MatchDAO;
import com.achilles.dao.PlayerDAO;
import com.achilles.dao.impl.MatchDAOImpl;
import com.achilles.dao.impl.PlayerDAOImpl;
import com.achilles.dto.MatchDayInfo;
import com.achilles.dto.MatchRegistrationInfo;
import com.achilles.model.MatchInfo;
import com.achilles.model.MatchPeriod;
import com.achilles.model.MatchRegistrationAdversary;
import com.achilles.model.MatchRegistrationDays;
import com.achilles.model.Player;
import com.achilles.model.Ranking;
import com.achilles.util.ConstValue;
import com.achilles.util.DateTimeUtil;
import com.achilles.util.RandomUtil;

public class MatchInfoService {
	
	public List<MatchRegistrationInfo> QueryActiveMatchRegistrationInfo() throws Exception {
		MatchDAO matchDao = new MatchDAOImpl();
		MatchPeriod active = matchDao.GetActivePeriod();
		
		List<MatchRegistrationInfo> result = QueryMatchRegistrationInfo(active.getId());
		return result;
	}

	public void TestInitRegistration() throws Exception {
		PlayerDAO playerDao = new PlayerDAOImpl();
		MatchDAO matchDao = new MatchDAOImpl();
		// get matchperiod
		MatchPeriod active = matchDao.GetActivePeriod();
		MatchPeriod last = matchDao.GetLastActivePeriod();
		
		// get all players
		Player criteria = new Player();
		criteria.setStatus(Player.STATUS_USING);
		List<Player> players = playerDao.GetPlayers(criteria, 0, ConstValue.MaxPlayersCount);
		
		// get ranking info
		RankingService rankingService = new RankingService();
		Map<Integer, Integer> ranking_playerMap = rankingService.QueryActivePlayerRanking();
		Player iter = null;
		for(int i = 0; i< players.size(); i++) {
			//1. get adversary
			iter = players.get(i);
			List<Integer> adversaries = queryAdversariesByPlayerId(last.getId(), iter.getId());
			
			//2. pick up from 1 to 5 and update MatchRegistrationAdversary
			List<Integer> challenge = new RandomUtil().RandomPickUp(adversaries);
			matchDao.ClearAdversaries(active.getId(), iter.getId());
			for(int j = 0; j<challenge.size(); j++) {
				MatchRegistrationAdversary adversary = new MatchRegistrationAdversary();
				adversary.setMatchPeriodId(active.getId());
				adversary.setPlayerId(iter.getId());
				adversary.setAdversaryId(challenge.get(j));
				matchDao.SaveMatchRegistrationAdversary(adversary);
			}
			
			//3. pick up day from 1 to 6 and update MatchRegistrationDays
			List<Integer> dateRange = new ArrayList<Integer>();
			for(int o = 0; o<ConstValue.MaxDateRange; o++) {
				dateRange.add(o);
			}
			List<Integer> days = new RandomUtil().RandomPickUp(dateRange);
			while(days.size() == 0) {
				days = new RandomUtil().RandomPickUp(dateRange);
			}
			matchDao.ClearDays(active.getId(), iter.getId());
			for(int k = 0; k<days.size(); k++) {
				MatchRegistrationDays day = new MatchRegistrationDays();
				day.setMatchPeriodId(active.getId());
				day.setPlayerId(iter.getId());
				day.setFreeDay(days.get(k));
				matchDao.SaveMatchRegistrationDay(day);
			}
		}
		
		return;
	}
	
	
	
	
	private List<Integer> queryAdversariesByPlayerId(int matchPeriodId, int playerId) throws Exception {
		//1. get ranking by id
		RankingService service = new RankingService();
		Ranking ranking = service.QueryRankingByPlayerid(matchPeriodId, playerId);
		
		List<Integer> result = new ArrayList<Integer>();
		//2. get adversaries by ranking
		if(ranking.getRanking() > ConstValue.MaxChallengeTime) {
			for(int i = 0; i < ConstValue.MaxChallengeTime; i++) {
				Ranking adversaryRanking = service.QuyeryRankingByRanking(matchPeriodId, ranking.getRanking() - i -1);
				result.add(adversaryRanking.getPlayerId());
			}
		}
		else {
			// handle the top player of ranking
			int challengeRanking = ranking.getRanking() - 1;
			while( challengeRanking > 0) {
				Ranking adversaryRanking = service.QuyeryRankingByRanking(matchPeriodId, challengeRanking--);
				result.add(adversaryRanking.getPlayerId());
			}
				
		}
		
		return result;
	}
	
	private List<MatchRegistrationInfo> QueryMatchRegistrationInfo(int matchPeriodId) throws Exception {
		// get all players
		PlayerDAO playerDAO = new PlayerDAOImpl();
		Player criteria = new Player();
		criteria.setStatus(Player.STATUS_USING);
		List<Player> players = playerDAO.GetPlayers(criteria, 0, ConstValue.MaxPlayersCount);
		Map<Integer, Player> playerMap = new HashMap<Integer,Player>();
		Player player = null;
		for(int x = 0; x<players.size(); x++) {
			player = players.get(x);
			playerMap.put(player.getId(), player);
		}
		
		//handle every player's registration info
		List<MatchRegistrationInfo> result = new ArrayList<MatchRegistrationInfo>();
		MatchRegistrationInfo mri = null;
		
		MatchDAO matchDao = new MatchDAOImpl();
		for(int i=0; i<players.size(); i++) {
			player = players.get(i);
			mri = new MatchRegistrationInfo();
			mri.setPlayerId(player.getId());
			mri.setLoginid(player.getLoginId());
			mri.setName(player.getName());
			mri.setRace(player.getRace());

			// get player's adversaries
			List<MatchRegistrationAdversary> adversaries = matchDao.GetRegistrationAdversaryByPlayer(matchPeriodId, player.getId());
			String adversariesName = "";
			List<Integer> adversaryIds = new ArrayList<Integer>();
			MatchRegistrationAdversary advIter = null;
			for(int j = 0; j < adversaries.size(); j++) {
				advIter = adversaries.get(j);
				adversaryIds.add(advIter.getAdversaryId());
				adversariesName += playerMap.get(advIter.getAdversaryId()).getName() + "[" + playerMap.get(advIter.getAdversaryId()).getLoginId() + "]，";
			}
			mri.setAdversaryIds(adversaryIds);
			mri.setAdversaries(adversariesName);
			
			// get player's free days
			List<MatchRegistrationDays> days = matchDao.GetRegistrationDayByPlayer(matchPeriodId, player.getId());
			String daysName = "";
			List<Integer> dayIds = new ArrayList<Integer>();
			MatchRegistrationDays dayIter = null;
			for(int k = 0; k < days.size(); k++) {
				dayIter = days.get(k);
				dayIds.add(dayIter.getFreeDay());
				daysName += DateTimeUtil.GetDayDesc(dayIter.getFreeDay()) + "，";
			}
			mri.setDayIds(dayIds);
			mri.setDays(daysName);
			result.add(mri);
		}
			
		return result;
	}

	public void UpdateActiveMatchInfo() throws Exception {
		// get match period
		MatchDAO matchDao = new MatchDAOImpl();
		MatchPeriod active = matchDao.GetActivePeriod();
		MatchPeriod last = matchDao.GetLastActivePeriod();
		
		// get all players
		PlayerDAO playerDAO = new PlayerDAOImpl();
		Player criteria = new Player();
		criteria.setStatus(Player.STATUS_USING);
		List<Player> players = playerDAO.GetPlayers(criteria, 0, ConstValue.MaxPlayersCount);
		
		// get registration info of every players
		Player player = null;
		RankingService rankingService = new RankingService();
		for(int i = 0; i<players.size(); i++) {
			player = players.get(i);
			// get player's adversaries
			List<MatchRegistrationAdversary> adversaries = matchDao.GetRegistrationAdversaryByPlayer(active.getId(), player.getId());
			List<Integer> adversaryIds = new ArrayList<Integer>();
			MatchRegistrationAdversary advIter = null;
			for(int j = 0; j < adversaries.size(); j++) {
				advIter = adversaries.get(j);
				adversaryIds.add(advIter.getAdversaryId());
			}
			player.setAdversaryIds(adversaryIds);
			player.setRemainingChallengeTimes(adversaryIds.size());
			
			// get player's free day
			List<MatchRegistrationDays> days = matchDao.GetRegistrationDayByPlayer(active.getId(), player.getId());
			Map<Integer,Integer> dayCounts = new HashMap<Integer,Integer>();
			MatchRegistrationDays dayIter = null;
			for(int k = 0; k < days.size(); k++) {
				dayIter = days.get(k);
				dayCounts.put(dayIter.getFreeDay(), 0);
			}
			player.setDays(dayCounts);
			
			// get Player's ranking
			Ranking ranking = rankingService.QueryRankingByPlayerid(last.getId(), player.getId());
			player.setRanking(ranking.getRanking());
		}
		
		// create player map
		Map<Integer, Player> playerMap = new HashMap<Integer,Player>();
		for(int x = 0; x<players.size(); x++) {
			player = players.get(x);
			playerMap.put(player.getId(), player);
		}
		
		//handle every player's Match info
		matchDao.ClearMatchInfos(active.getId());
		for(int o = 0; o<players.size(); o++) {
			player = players.get(o);
			
			for(int p = 0; p<player.getAdversaryIds().size(); p++) {
				tryToArrangeMatch(active.getId(), player, playerMap.get(player.getAdversaryIds().get(p)), playerMap);
			}			
		}
		
		return;
	}	
	
	private void tryToArrangeMatch(int matchPeriodId, Player challenger, Player adversary, Map<Integer, Player> playerMap) throws Exception {
		MatchDAO matchDao = new MatchDAOImpl();
		
		// a vs b
		Map<Integer, Integer> daysOfA = challenger.getDays();
		Map<Integer, Integer> daysOfB = adversary.getDays();
		Set<Integer> keys = daysOfA.keySet();
		List<Integer> matchDays = new ArrayList<Integer>();
		for (Integer dayOfA : keys) {  
			if(daysOfB.containsKey(dayOfA)) {
				matchDays.add(dayOfA);
			}
		}
		
		if(matchDays.size() == 0) {
			return;
		}
		
		if(adversary.getRemainingChallengeTimes() <= 0) {
			List<MatchInfo> adversaryMatches = matchDao.GetMatchInfosByAdversary(matchPeriodId, adversary.getId());
			for(int i = 0; i< adversaryMatches.size(); i++) {
				MatchInfo adversaryMatch = adversaryMatches.get(i);
				Player compare = playerMap.get(adversaryMatch.getChallengerId());
				if(compare.getRanking() > challenger.getRanking()) {
					adversaryMatch.setChallengerId(challenger.getId());
					matchDao.SaveMatchInfo(adversaryMatch);
					return;
				}
			}
		}
		else {
			int choosenDay = matchDays.get(0);
			for(int j = 1; j<matchDays.size(); j++) {
				int day = matchDays.get(j);
				
				int choosenA = daysOfA.get(choosenDay);
				int choosenB = daysOfB.get(choosenDay);
				
				int compareA = daysOfA.get(day);
				int compareB = daysOfB.get(day);
				
				if( choosenA > compareA ) {
					if( choosenB >= compareB ) {
						choosenDay = day;
					}
					else {
						//choosenB < compareB
						if( choosenA > compareB ) {
							choosenDay = day;
						}
						else if( choosenA < compareB ) {
							//choosenDay = choosenDay;
						}
						else {
							//choosenA == compareB
							if( compareA >= choosenB ) {
								//choosenDay = choosenDay;
							}
							else {
								//compareA < choosenB
								choosenDay = day;
							}
						}
					}
				}
				else if( choosenA == compareA ) {
					if( choosenB > compareB ) {
						choosenDay = day;
					}
					else {
						//choosenB <= compareB
						//choosenDay = choosenDay;
					}
				}
				else {
					//choosenA < compareA
					if( choosenB <= compareB ) {
						//choosenDay = choosenDay;
					}
					else{
						//choosenB > compareB
						if( compareA > choosenB ) {
							//choosenDay = choosenDay;
						}
						else if( compareA < choosenB ) {
							choosenDay = day;
						}
						else {
							//compareA == choosenB
							if( choosenA <= compareB ) {
								//choosenDay = choosenDay;
							}
							else {
								//choosenA > compareB
								choosenDay = day;
							}
						}
					}
				}
			}
			
			MatchInfo info = new MatchInfo();
			info.setMatchPeriodId(matchPeriodId);
			info.setChallengerId(challenger.getId());
			info.setAdversaryId(adversary.getId());
			info.setDayId(choosenDay);
			
			matchDao.SaveMatchInfo(info);
			adversary.setRemainingChallengeTimes(adversary.getRemainingChallengeTimes() - 1);
			System.out.println(daysOfA.get(choosenDay));
			daysOfA.put(choosenDay, daysOfA.get(choosenDay) + 1);
			System.out.println(daysOfA.get(choosenDay));
			System.out.println(challenger.getDays().get(choosenDay));
			daysOfB.put(choosenDay, daysOfB.get(choosenDay) + 1);
		}
		
		return;
	}

	public List<MatchDayInfo> QueryActiveMatchInfo() throws Exception {
		MatchDAO matchDao = new MatchDAOImpl();
		List<MatchInfo> infos = matchDao.GetActiveMatchInfo();
		
		List<MatchDayInfo> dayInfos = new ArrayList<MatchDayInfo>();
		Map<Integer, MatchDayInfo> dayInfosMap = new HashMap<Integer, MatchDayInfo>();
		for(int i = 0; i<ConstValue.MaxDateRange; i++) {
			MatchDayInfo dayInfo = new MatchDayInfo();
			dayInfo.setDayId(i);
			dayInfo.setDayName(DateTimeUtil.GetDayDesc(i));
			dayInfos.add(dayInfo);
			dayInfosMap.put(i, dayInfo);
		}
		
		// get all players
		PlayerDAO playerDAO = new PlayerDAOImpl();
		Player criteria = new Player();
		criteria.setStatus(Player.STATUS_USING);
		List<Player> players = playerDAO.GetPlayers(criteria, 0, ConstValue.MaxPlayersCount);
		Map<Integer, Player> playerMap = new HashMap<Integer,Player>();
		Player player = null;
		for(int x = 0; x<players.size(); x++) {
			player = players.get(x);
			playerMap.put(player.getId(), player);
		}
				
		MatchInfo info = null;
		MatchDayInfo dayInfo = null;
		for(int j = 0; j<infos.size(); j++) {
			info = infos.get(j);
			Player challenger = playerMap.get(info.getChallengerId());
			Player adversary = playerMap.get(info.getAdversaryId());
			dayInfo = dayInfosMap.get(info.getDayId());
			
			String matchInfoDesc = dayInfo.getMatchInfo() == null ? "" : dayInfo.getMatchInfo();
			matchInfoDesc += "<span class=\"matchInfoItem fa fa-crosshairs\">" + challenger.getName() + "(" + challenger.getLoginId() + ") vs " + adversary.getName() + "(" + adversary.getLoginId() + ")</span>"; 
			dayInfo.setMatchInfo(matchInfoDesc);
		}
		
		return dayInfos;
	}
	
}

package com.achilles.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.achilles.dao.MatchDAO;
import com.achilles.dao.impl.MatchDAOImpl;
import com.achilles.dto.MatchDayInfo;
import com.achilles.dto.MatchRegistrationInfo;
import com.achilles.dto.MatchRegistrationInfoForEdit;
import com.achilles.model.MatchInfo;
import com.achilles.model.MatchPeriod;
import com.achilles.model.MatchRegistrationAdversary;
import com.achilles.model.MatchRegistrationDays;
import com.achilles.model.Player;
import com.achilles.model.Score;
import com.achilles.util.ConfigUtil;
import com.achilles.util.DateTimeUtil;
import com.achilles.util.RandomUtil;

public class MatchInfoService {
	
	public List<MatchRegistrationInfo> QueryActiveMatchRegistrationInfo() throws Exception {
		MatchDAO matchDao = new MatchDAOImpl();
		MatchPeriod active = matchDao.GetActivePeriod();
		if( active == null ) {
			return new ArrayList<MatchRegistrationInfo>();
		}
		
		List<MatchRegistrationInfo> result = QueryMatchRegistrationInfo(active.getId());
		return result;
	}

	public void TestInitRegistration() throws Exception {
		MatchDAO matchDao = new MatchDAOImpl();
		// get matchperiod
		MatchPeriod active = matchDao.GetActivePeriod();
				
		// get all players
		List<Player> players = new PlayerInfoService().QueryAllActivePlayer();
		
		// get ranking info
		RankingService rankingService = new RankingService();
		Map<Integer, Integer> playerRankingMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> rankingPlayerMap = new HashMap<Integer, Integer>();
		rankingService.QueryActivePlayerRanking(playerRankingMap, rankingPlayerMap);
		Player iter = null;
		for(int i = 0; i< players.size(); i++) {
			//1. get adversary
			iter = players.get(i);
			List<Integer> adversaries = queryAdversariesByPlayerId(iter.getId(), playerRankingMap, rankingPlayerMap);
			
			//2. pick up from 1 to 5 and update MatchRegistrationAdversary
			List<Integer> chosenAdversaries = new RandomUtil().RandomPickUp(adversaries);
//			while(playerRankingMap.get(iter.getId())!= 1 && chosenAdversaries.size() == 0) {
//				chosenAdversaries = new RandomUtil().RandomPickUp(adversaries);
//			}
			chosenAdversaries = adversaries;
			matchDao.ClearAdversaries(active.getId(), iter.getId());
			for(int j = 0; j<chosenAdversaries.size(); j++) {
				MatchRegistrationAdversary adversary = new MatchRegistrationAdversary();
				adversary.setMatchPeriodId(active.getId());
				adversary.setPlayerId(iter.getId());
				adversary.setAdversaryId(chosenAdversaries.get(j));
				matchDao.SaveMatchRegistrationAdversary(adversary);
			}
			
			//3. pick up day from 1 to 6 and update MatchRegistrationDays
			if( chosenAdversaries.size()>0 || playerRankingMap.get(iter.getId()) == 1) {
				List<Integer> days = new ArrayList<Integer>();
				while(days.size() <2 ) {
					days.clear();
					for(int o = 0; o < ConfigUtil.getInstance().getMaxDateRange(); o++) {
						int chosenPercent = 0;
						if( o < 4 ) {
							chosenPercent = ConfigUtil.getInstance().getRateOfChosenMondyToThursday();
						}
						else {
							chosenPercent = ConfigUtil.getInstance().getRateOfChosenSaturdayToSunday();
						}
						RandomUtil r = new RandomUtil();
						if( r.ProbabilityGenerator(chosenPercent) ) {
							days.add(o+1);
						}
					}
				}
				if(days.size() < 2){
					System.out.println("days.size small than 2!!!!");
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
		}
		
		return;
	}
	
	
	
	
	private List<Integer> queryAdversariesByPlayerId(int playerId, Map<Integer, Integer> playerMap, Map<Integer, Integer> rankingMap) throws Exception {
		//1. get ranking by id
		int ranking = playerMap.get(playerId);
		
		List<Integer> result = new ArrayList<Integer>();
		//2. get adversaries by ranking
		if(ranking > ConfigUtil.getInstance().getMaxChallengeCount()) {
			for(int i = 0; i < ConfigUtil.getInstance().getMaxChallengeCount(); i++) {
				int adversaryId = rankingMap.get(ranking-i-1);
				result.add(adversaryId);
			}
		}
		else {
			// handle the top player of ranking
			int challengeRanking = ranking - 1;
			while( challengeRanking > 0) {
				int adversaryId = rankingMap.get(challengeRanking--);
				result.add(adversaryId);
			}
		}
		
		return result;
	}
	
	private List<MatchRegistrationInfo> QueryMatchRegistrationInfo(int matchPeriodId) throws Exception {
		// get all players
		List<Player> players = new PlayerInfoService().QueryAllActivePlayer();
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
		ScoreInfoService ss = new ScoreInfoService();
		Score s = null;
		for(int i=0; i<players.size(); i++) {
			player = players.get(i);
			mri = new MatchRegistrationInfo();
			mri.setPlayerId(player.getId());
			mri.setLoginId(player.getLoginId());
			mri.setName(player.getName());
			mri.setRace(player.getRace());
			s = ss.QueryActiveMatchPeriodScoreByPlayer(player.getId());
			mri.setScoreReward(s == null ? 0 : s.getRewardSponsor());

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
		
		// get all players
		List<Player> players = new PlayerInfoService().QueryAllActivePlayer();
		
		// get registration info of every players
		Player player = null;
		RankingService rankingService = new RankingService();
		Map<Integer, Integer> playerRankingMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> rankingPlayerMap = new HashMap<Integer, Integer>();
		rankingService.QueryActivePlayerRanking(playerRankingMap, rankingPlayerMap);
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
			//for challenge strategy
			if(adversaryIds.size() > 0) {
				player.setRemainingChallengeTimes(ConfigUtil.getInstance().getMinAcceptChallengeCount());
			}
			
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
			int ranking = playerRankingMap.get(player.getId());
			player.setRanking(ranking);
		}
		
		Collections.sort(players);
		//for challenge strategy
		if(players.size() > 0 && players.get(0).getRanking() == 1) {
			players.get(0).setRemainingChallengeTimes(ConfigUtil.getInstance().getFirstPlayerAcceptChallengeCount());
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
					adversaryMatch.setChallengerVranking(challenger.getRanking());
					matchDao.SaveMatchInfo(adversaryMatch);
					//for challenge strategy
					challenger.setRemainingChallengeTimes(challenger.getRemainingChallengeTimes() + 1);
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
			info.setChallengerVranking(challenger.getRanking());
			info.setAdversaryId(adversary.getId());
			info.setAdversaryVranking(adversary.getRanking());
			info.setDayId(choosenDay);
			
			matchDao.SaveMatchInfo(info);
			adversary.setRemainingChallengeTimes(adversary.getRemainingChallengeTimes() - 1);
			//for challenge strategy
			challenger.setRemainingChallengeTimes(challenger.getRemainingChallengeTimes() + 1);
			//System.out.println(daysOfA.get(choosenDay));
			daysOfA.put(choosenDay, daysOfA.get(choosenDay) + 1);
			//System.out.println(daysOfA.get(choosenDay));
			//System.out.println(challenger.getDays().get(choosenDay));
			daysOfB.put(choosenDay, daysOfB.get(choosenDay) + 1);
		}
		
		return;
	}

	public List<MatchDayInfo> QueryActiveMatchInfo() throws Exception {
		MatchDAO matchDao = new MatchDAOImpl();
		List<MatchInfo> infos = matchDao.GetActiveMatchInfo();
		
		List<MatchDayInfo> dayInfos = new ArrayList<MatchDayInfo>();
		Map<Integer, MatchDayInfo> dayInfosMap = new HashMap<Integer, MatchDayInfo>();
		for(int i = 0; i < ConfigUtil.getInstance().getMaxDateRange(); i++) {
			int dayId = i + 1;//monday is rest day
			MatchDayInfo dayInfo = new MatchDayInfo();
			dayInfo.setDayId(dayId);
			dayInfo.setDayName(DateTimeUtil.GetDayDesc(dayId));
			dayInfos.add(dayInfo);
			List<MatchInfo> infosInDayInfo = new ArrayList<MatchInfo>();
			dayInfo.setMatchInfo(infosInDayInfo);
			dayInfosMap.put(dayId, dayInfo);
		}
		
		// get all players
		List<Player> players = new PlayerInfoService().QueryAllActivePlayer();
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
			
			info.setChallengerName(challenger.getName()+ "(" + challenger.getLoginId() + "-" + info.getChallengerVranking() + ")");
			info.setAdversaryName(adversary.getName() + "(" + adversary.getLoginId() + "-" + info.getAdversaryVranking() + ")");
			
			dayInfo.getMatchInfo().add(info);
		}
		
		return dayInfos;
	}

	public void TestCreateMatchResult() throws Exception {
		MatchDAO matchDao = new MatchDAOImpl();
		List<MatchInfo> infos = matchDao.GetActiveMatchInfo();
		int matchResult = 0;
		MatchInfo info = null;
		for( int i = 0; i < infos.size(); i++ ) {
			info = infos.get(i);
			matchResult = new RandomUtil().ProbabilityGenerator(ConfigUtil.getInstance().getMaxPercentOfChallengerWin() - ConfigUtil.getInstance().getPercentOfChallengerWinDiminishingStep() * (info.getChallengerVranking() - info.getAdversaryVranking() - 1)) ?  MatchInfo.RESULT_CHALLENGER_WIN : MatchInfo.RESULT_ADVERSARY_WIN;
			info.setResult(matchResult);
			String score = matchResult == MatchInfo.RESULT_CHALLENGER_WIN ? "2:1" : "0:2";
			info.setScore(score);
			matchDao.SaveMatchInfo(info);
		}
		
		return;
	}

	public MatchRegistrationInfoForEdit QueryMatchRegistrationInfoForEditByPlayerId(int playerId) throws Exception {
		// get ranking info
		RankingService rankingService = new RankingService();
		Map<Integer, Integer> playerRankingMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> rankingPlayerMap = new HashMap<Integer, Integer>();
		rankingService.QueryActivePlayerRanking(playerRankingMap, rankingPlayerMap);

		List<Integer> adversaryIds = queryAdversariesByPlayerId(playerId, playerRankingMap, rankingPlayerMap);
		
		// get all players
		List<Player> players = new PlayerInfoService().QueryAllActivePlayer();
		Map<Integer, Player> playerMap = new HashMap<Integer,Player>();
		Player player = null;
		for(int x = 0; x<players.size(); x++) {
			player = players.get(x);
			playerMap.put(player.getId(), player);
		}
		
		// make result object
		MatchRegistrationInfoForEdit result = new MatchRegistrationInfoForEdit();
		result.setPlayerId(playerId);
		
		MatchDAO matchDao = new MatchDAOImpl();
		MatchPeriod active = matchDao.GetActivePeriod();
		result.setMatchPeriodId(active.getId());
		
		List<Player> adversaries = new ArrayList<Player>();
		Player adversary = null;
		for(int i = 0; i < adversaryIds.size(); i++) {
			int adversaryId = adversaryIds.get(i);
			adversary = playerMap.get(adversaryId);
			adversary.setRanking(playerRankingMap.get(adversaryId));
			adversaries.add(adversary);
		}
		result.setAdversaries(adversaries);
		return result;
	}
	
	public void ArchiveActiveMatchInfo() throws Exception {
		if( !checkActiveMatchInfoResult() ) {
			throw new Exception("仍有比赛结果没有保存，不能归档本轮比赛!");
		}
		
		// 1. caculate ranking of the round
		
		// 2. delete init match_period ranking, if exist
		
		// 3. change match_period status
		return;
	}
	
	private boolean checkActiveMatchInfoResult() throws Exception {
		// if all of result has been saved ,then return true, otherwise return false
		boolean result = true;
		MatchDAO matchDao = new MatchDAOImpl();
		List<MatchInfo> infos = matchDao.GetActiveMatchInfo();
		for(int i = 0; i < infos.size(); i++) {
			if(infos.get(i).getResult() == 0) {
				result = false;
				break;
			}
		}
		return result;
	}

	public void SavePlayerMatchRegistrationInfo(MatchRegistrationInfo regInfo) throws Exception {
		// get matchperiod
		MatchDAO matchDao = new MatchDAOImpl();
		MatchPeriod active = matchDao.GetActivePeriod();
		
		matchDao.ClearAdversaries(active.getId(), regInfo.getPlayerId());
		for(int i = 0; i < regInfo.getAdversaryIds().size(); i++) {
			MatchRegistrationAdversary adversary = new MatchRegistrationAdversary();
			adversary.setMatchPeriodId(active.getId());
			adversary.setPlayerId(regInfo.getPlayerId());
			adversary.setAdversaryId(regInfo.getAdversaryIds().get(i));
			matchDao.SaveMatchRegistrationAdversary(adversary);
		}
		
		matchDao.ClearDays(active.getId(), regInfo.getPlayerId());
		for(int j = 0; j < regInfo.getDayIds().size(); j++) {
			MatchRegistrationDays day = new MatchRegistrationDays();
			day.setMatchPeriodId(active.getId());
			day.setPlayerId(regInfo.getPlayerId());
			day.setFreeDay(regInfo.getDayIds().get(j));
			matchDao.SaveMatchRegistrationDay(day);
		}
		
		ScoreInfoService ss = new ScoreInfoService();
		ss.SavePlayerActiveMatchPeriodSponsorReward(regInfo.getPlayerId(), regInfo.getScoreReward());
		return;
	}

	
	
}

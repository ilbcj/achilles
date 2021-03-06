package com.achilles.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.achilles.dao.BattleInfoDAO;
import com.achilles.dao.MatchDAO;
import com.achilles.dao.PlatDAO;
import com.achilles.dao.PlayerDAO;
import com.achilles.dao.impl.BattleInfoDAOImpl;
import com.achilles.dao.impl.MatchDAOImpl;
import com.achilles.dao.impl.PlatDAOImpl;
import com.achilles.dao.impl.PlayerDAOImpl;
import com.achilles.dto.ConfigInfo;
import com.achilles.dto.MatchDayInfo;
import com.achilles.dto.MatchRegistrationInfo;
import com.achilles.dto.MatchRegistrationInfoForEdit;
import com.achilles.model.Battle;
import com.achilles.model.MatchInfo;
import com.achilles.model.Plat;
import com.achilles.model.Ranking;
import com.achilles.model.Round;
import com.achilles.model.MatchRegistrationAdversary;
import com.achilles.model.MatchRegistrationDays;
import com.achilles.model.Player;
import com.achilles.model.Score;
import com.achilles.util.ConfigUtil;
import com.achilles.util.ConstValue;
import com.achilles.util.DateTimeUtil;
import com.achilles.util.RandomUtil;

public class MatchInfoService {
	
	public List<MatchRegistrationInfo> QueryActiveMatchRegistrationInfo() throws Exception {
		RoundInfoService roundService = new RoundInfoService();
		Round active = roundService.GetActiveRound();
		if( active == null ) {
			return new ArrayList<MatchRegistrationInfo>();
		}
		
		List<MatchRegistrationInfo> result = QueryMatchRegistrationInfo(active.getId());
		return result;
	}

	public void TestInitRegistration() throws Exception {
		MatchDAO matchDao = new MatchDAOImpl();
		// get matchperiod
		RoundInfoService roundService = new RoundInfoService();
		Round active = roundService.GetActiveRound();
				
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
			List<Integer> chosenAdversaries = new RandomUtil().RandomPickUp(adversaries, ConfigUtil.getInstance().getMaxChallengeCount());
//			while(playerRankingMap.get(iter.getId())!= 1 && chosenAdversaries.size() == 0) {
//				chosenAdversaries = new RandomUtil().RandomPickUp(adversaries);
//			}
			matchDao.ClearAdversaries(active.getId(), iter.getId());
			for(int j = 0; j<chosenAdversaries.size(); j++) {
				MatchRegistrationAdversary adversary = new MatchRegistrationAdversary();
				adversary.setRoundId(active.getId());
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
					day.setRoundId(active.getId());
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
		//if(ranking > ConfigUtil.getInstance().getMaxChallengeCount()) {
		//	for(int i = 0; i < ConfigUtil.getInstance().getMaxChallengeCount(); i++) {
		if(ranking > ConstValue.MaxChallengeRange) {
			for(int i = 0; i < ConstValue.MaxChallengeRange; i++) {
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
	
	private List<MatchRegistrationInfo> QueryMatchRegistrationInfo(int roundId) throws Exception {
		// get all players
		List<Player> players = new PlayerInfoService().QueryAllActivePlayer();
		
		// get registration info of every players
		RankingService rankingService = new RankingService();
		Map<Integer, Integer> playerRankingMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> rankingPlayerMap = new HashMap<Integer, Integer>();
		rankingService.QueryActivePlayerRanking(playerRankingMap, rankingPlayerMap);
		
		// gerenate player map
		Map<Integer, Player> playerMap = new HashMap<Integer,Player>();
		Player player = null;
		for(int x = 0; x<players.size(); x++) {
			player = players.get( x );
			player.setRanking( playerRankingMap.get( player.getId() ) );
			playerMap.put( player.getId(), player );
		}
		
		Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return o1.getRanking()-o2.getRanking();
            }
        });
		
		//handle every player's registration info
		List<MatchRegistrationInfo> result = new ArrayList<MatchRegistrationInfo>();
		MatchRegistrationInfo mri = null;
		
		MatchDAO matchDao = new MatchDAOImpl();
		ScoreInfoService ss = new ScoreInfoService();
		Score s = null;
		for( int i=0; i<players.size(); i++ ) {
//			if(i == 45) {
//				System.out.println(i);
//			}
			player = players.get( i );
			mri = new MatchRegistrationInfo();
			mri.setPlayerId( player.getId() );
			mri.setLoginId( player.getLoginId() );
			mri.setName( player.getName() );
			mri.setRace( player.getRace() );
			s = ss.QueryCurrentRoundScoreByPlayer( player.getId() );
			mri.setScoreReward( s == null ? 0 : s.getRewardSponsor() );
			mri.setScoreRewardMemo( s == null ? "" : s.getRewardSponsorReason() );

			// get player's adversaries
			List<MatchRegistrationAdversary> adversaries = matchDao.GetRegistrationAdversaryByPlayer(roundId, player.getId());
			String adversariesName = "";
			List<Integer> adversaryIds = new ArrayList<Integer>();
			List<String> platIds = new ArrayList<String>();
			MatchRegistrationAdversary advIter = null;
			for(int j = 0; j < adversaries.size(); j++) {
				advIter = adversaries.get(j);
				adversaryIds.add(advIter.getAdversaryId());
				platIds.add(advIter.getPlatId());
				adversariesName += playerMap.get(advIter.getAdversaryId()).getLoginId() + "[" + playerMap.get(advIter.getAdversaryId()).getRace() + "]，";
			}
			mri.setAdversaryIds(adversaryIds);
			mri.setPlatIds(platIds);
			mri.setAdversaries(adversariesName);
			
			// get player's free days
			List<MatchRegistrationDays> days = matchDao.GetRegistrationDayByPlayer(roundId, player.getId());
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
		RoundInfoService roundService = new RoundInfoService();
		Round active = roundService.GetActiveRound();
		
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
		
		int count = 0;
		boolean isRemove = false;
		do {
			removeOneAdversary(players, isRemove);
			//handle every player's Match info
			matchDao.ClearMatchInfos(active.getId());
			for(int o = 0; o<players.size(); o++) {
				player = players.get(o);
				
				for(int p = 0; p<player.getAdversaryIds().size(); p++) {
					tryToArrangeMatch(active.getId(), player, playerMap.get(player.getAdversaryIds().get(p)), playerMap);
				}
				
				if( player.getRemainingChallengeTimes() == 0 ) {
					//for challenge strategy
					player.setRemainingChallengeTimes(ConfigUtil.getInstance().getMinAcceptChallengeCount());
				}
			}
			count = matchDao.GetMaxMatchCountByPlayer(active.getId());
			isRemove = true;
		}
		while(count > ConstValue.MaxMatchCountPerDay);
		
		// update round status
		active.setPhase(Round.PHASE_ARRANGED);
		roundService.SaveRound(active);
		return;
	}
	
	private void removeOneAdversary(List<Player> players, boolean isRemove ) {
		if( !isRemove ) 
			return;
		
		int maxAdversaryCount = 0;
		Player player = null;
		for( int i = 0; i < players.size(); i++ ) {
			player = players.get(i);
			if( player.getAdversaryIds().size() > maxAdversaryCount ) {
				maxAdversaryCount = player.getAdversaryIds().size();
			}
		}
		
		if( maxAdversaryCount > 0 ) {
			for( int j = 0; j < players.size(); j++ ) {
				player = players.get(j);
				if( player.getAdversaryIds().size() == maxAdversaryCount ) {
					player.getAdversaryIds().remove( player.getAdversaryIds().size() - 1 );
					//System.out.println(player.getAdversaryIds().size());
				}
			}
		}
		return;
	}
	
	private void tryToArrangeMatch(int roundId, Player challenger, Player adversary, Map<Integer, Player> playerMap) throws Exception {
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
			List<MatchInfo> adversaryMatches = matchDao.GetMatchInfosByAdversary(roundId, adversary.getId());
			for(int i = 0; i< adversaryMatches.size(); i++) {
				MatchInfo adversaryMatch = adversaryMatches.get(i);
				Player compare = playerMap.get(adversaryMatch.getChallengerId());
				if(compare.getRanking() > challenger.getRanking()) {
					adversaryMatch.setChallengerId(challenger.getId());
					adversaryMatch.setChallengerVranking(challenger.getRanking());
					matchDao.SaveMatchInfo(adversaryMatch);
					//for challenge strategy
					challenger.setRemainingChallengeTimes(challenger.getRemainingChallengeTimes() + 1);
					compare.setRemainingChallengeTimes(compare.getRemainingChallengeTimes() - 1);
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
			info.setRoundId(roundId);
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
	public List<Battle> QueryMatchInfoForEditByPlayerIdAndAdversaryId(int playerId, int adversaryId) throws Exception {
		RoundInfoService roundService = new RoundInfoService();
		Round active = roundService.GetActiveRound();
		BattleInfoDAO battlehDao = new BattleInfoDAOImpl();
		List<Battle> result = battlehDao.GetBattleInfoByChallengerAndAdversary(playerId, adversaryId, active.getId());
		return result;
	}
	
	public List<MatchDayInfo> QueryActiveMatchInfo() throws Exception {
		MatchDAO matchDao = new MatchDAOImpl();
		List<MatchInfo> infos = matchDao.GetActiveMatchInfo();
		
		List<MatchDayInfo> dayInfos = new ArrayList<MatchDayInfo>();
		Map<Integer, MatchDayInfo> dayInfosMap = new HashMap<Integer, MatchDayInfo>();
		ConfigInfoService cs = new ConfigInfoService();
		ConfigInfo config = cs.QuerySystemConfigInfo();
		String strRestDays = config.getRestDay();
		List<Integer> restDayIds = new ArrayList<Integer>();
		if(strRestDays != null) {
			String[] restDays = strRestDays.split(",");
			for(String strRestDay : restDays) {
				if(strRestDay.trim().length() > 0) {
					restDayIds.add(Integer.parseInt(strRestDay));
				}
			}
		}
		List<Integer> dayIds = new ArrayList<Integer>();//add week days except rest day
		for(int x = 0; x<7; x++) {
			if(!restDayIds.contains(x)) {
				dayIds.add(x);
			}
		}
		for(int i = 0; i < dayIds.size(); i++) {
			int dayId = dayIds.get(i);
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
		
		// get all match registration adversarys
		List<MatchRegistrationAdversary> adversaries = matchDao.GetActiveRoundRegistrationAdversary();
		PlatDAO pdao = new PlatDAOImpl();
		List<Plat> plats = pdao.GetPlatByStatus(Plat.STATUS_ACTIVE);
		Map<String, String> platMap = new HashMap<String, String>();
		for( int x = 0; x < plats.size(); x++ ) {
			Plat plat = plats.get(x);
			platMap.put( "" + plat.getId(), plat.getName() );
		}
		MatchInfo info = null;
		MatchDayInfo dayInfo = null;
		for(int j = 0; j<infos.size(); j++) {
			info = infos.get(j);
			Player challenger = playerMap.get(info.getChallengerId());
			Player adversary = playerMap.get(info.getAdversaryId());
			dayInfo = dayInfosMap.get(info.getDayId());
			
			info.setChallengerName(challenger.getLoginId() + "[" + challenger.getRace() + "]");
			info.setChallengerRace( challenger.getRace() );
			info.setAdversaryName(adversary.getLoginId() + "[" + adversary.getRace() + "]");
			info.setAdversaryRace( adversary.getRace() );
			info.setPlatName( getPlatName(challenger.getId(), adversary.getId(), adversaries, platMap) );
			
			dayInfo.getMatchInfo().add(info);
		}
		
		return dayInfos;
	}
	
	private String getPlatName(int challengerId, int adversaryId, List<MatchRegistrationAdversary> adversaries, Map<String, String> platMap) throws Exception {
		String name = "";
		
		String plats = null;
		for( int i = 0; i < adversaries.size(); i++ ) {
			MatchRegistrationAdversary item = adversaries.get(i);
			if(item.getPlayerId() == challengerId && item.getAdversaryId() == adversaryId) {
				plats = item.getPlatId();
				break;
			}
		}
		if( plats == null || plats.isEmpty() ) {
			throw new Exception("no plat found in match info [playerId:" + challengerId + "; adversaryId:" + adversaryId + "]");
		}
		String [] platIds = plats.split(",");
		for( int j = 0; j < platIds.length; j++ ) {
			name += platMap.get(platIds[j]) + ",";
		}
		return name;
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
		
		RoundInfoService roundService = new RoundInfoService();
		Round active = roundService.GetActiveRound();
		result.setRoundId(active.getId());
		
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
	
	public void ArchiveActiveMatchInfo(int roundId) throws Exception {
		if( !checkActiveMatchInfoResult() ) {
			throw new Exception("仍有比赛结果没有保存，不能归档本轮比赛!");
		}
		
		// 1. caculate player score of the round
		List<Player> players = new PlayerInfoService().QueryAllActivePlayer();
		Map<Integer, Player> playerMap = new HashMap<Integer,Player>();
		Player player = null;
		for(int x = 0; x<players.size(); x++) {
			player = players.get(x);
			playerMap.put(player.getId(), player);
		}
		for( int i = 0; i < players.size(); i++ ) {
			caculatePlayerScoreOfCurrentRound(players.get(i), playerMap);
		}
		
		// 2. gerenate ranking of the round
		ScoreInfoService ss = new ScoreInfoService();
		List<Score> scores = ss.QueryCurrentRoundScoreByRanking();
		RankingService rs = new RankingService();
		Ranking ranking = null;
		for( int j = 0; j < scores.size(); j++ ) {
			Score score = scores.get(j);
			ranking = new Ranking();
			ranking.setPlayerId(score.getPlayerId());
			ranking.setRoundId(score.getRoundId());
			ranking.setScore(score.getScore());
			ranking.setRanking(j+1);
			rs.SaveRanking(ranking);
		}
		
		
		// 3. remove ranking of init round
		Ranking target = new Ranking();
		for( int i = 0; i < players.size(); i++ ) {
			player = players.get( i );
			target.setPlayerId( player.getId() );
			target.setRoundId( new RoundInfoService().getInitRound().getId() );
			rs.RemoveInitRoundRanking( target );
		}
		
		return;
	}
	
	private void caculatePlayerScoreOfCurrentRound(Player player, Map<Integer, Player> playerMap) throws Exception {
		int score = 0;
		int lastScore = 0;
		int challengerWin = 0;
		int challengerLose = 0;
		int adversaryWin = 0;
		int adversaryLose = 0;
		int rewardAbandon = 0;
		int rewardSponsor = 0;
		int isAbandon = 0;
		int reward1 = 0;//for bonus plats
		String memo = "";
		
		//0. get last score
		Round active = new RoundInfoService().GetActiveRound();
		Ranking lastRanking = new RankingService().QueryRankingByPlayerid(active.getLastRoundId(), player.getId());
		if(lastRanking == null) 
			lastRanking = new RankingService().QueryRankingByPlayerid(new RoundInfoService().getInitRound().getId(), player.getId());
		lastScore = lastRanking.getScore();
		memo += "[上轮积分" + lastScore + "],";
		
		//1. caculate score as challenger
		MatchDAO mdao = new MatchDAOImpl();
		List<MatchInfo> challengeInfos = mdao.GetActiveMatchInfoByChallenger(player.getId());
		MatchInfo info = null;
		for( int i = 0; i < challengeInfos.size(); i++ ) {
			info = challengeInfos.get(i);
			
			memo += "[挑战-" + playerMap.get(info.getAdversaryId()).getLoginId() + "(" + playerMap.get(info.getAdversaryId()).getRace() + "-" + info.getAdversaryVranking() + ")，挑战";
			int battleResult = info.getResult();
			if( MatchInfo.RESULT_ADVERSARY_ABSENT == battleResult || MatchInfo.RESULT_CHALLENGER_WIN == battleResult ) {
				memo += "成功，";
				challengerWin++;
				int scoreTemp = 0;
				scoreTemp += ConstValue.ScoreChallengerWin;
				
				scoreTemp += (info.getChallengerVranking()  - info.getAdversaryVranking() - 1 ) * ConstValue.ScoreChallengerRankingStep;
				
				if( info.getAdversaryVranking() < 6 ) {
					scoreTemp += ( 6 - info.getAdversaryVranking() ) * ConstValue.ScoreAdversaryRankingStep;
				}
				score += scoreTemp;
				memo += "积分" + scoreTemp + "],";
			}
			else if ( MatchInfo.RESULT_DRAW == battleResult ){
				//pass
			}
			else {
				memo += "失败，";
				challengerLose++;
				int scoreTemp = 0;
				if( MatchInfo.RESULT_CHALLENGER_ABSENT == battleResult ) {
					memo += "本场缺席，";
					scoreTemp -= ConstValue.ScoreAbsent;
					isAbandon = Score.ABSENT_YES;
				}
				
				if( info.getAdversaryVranking() < 6 ) {
					scoreTemp -= ( 6 - info.getAdversaryVranking() ) * ConstValue.ScoreAdversaryRankingStep;
				}
				score += scoreTemp;
				memo += "积分" + scoreTemp + "],";
			}
		}
		
		//2. caculate score as adversary
		List<MatchInfo> adversaryInfos = mdao.GetActiveMatchInfoByadversary( player.getId() );
		for( int j = 0; j < adversaryInfos.size(); j++ ) {
			info = adversaryInfos.get(j);
			
			memo += "[被-" + playerMap.get(info.getChallengerId()).getLoginId() + "(" + playerMap.get(info.getChallengerId()).getRace() + "-" + info.getChallengerVranking() + ")挑战，守擂";
			int battleResult = info.getResult();
			if( MatchInfo.RESULT_CHALLENGER_ABSENT == battleResult || MatchInfo.RESULT_ADVERSARY_WIN == battleResult ) {
				memo += "成功，";
				adversaryWin++;
				int scoreTemp = 0;
				scoreTemp += ConstValue.ScoreAdversaryWin;
				score += scoreTemp;
				memo += "积分" + scoreTemp + "],";
			}
			else if ( MatchInfo.RESULT_DRAW == battleResult ){
				//pass
			}
			else {
				memo += "失败，";
				adversaryLose++;
				int scoreTemp = 0;
				scoreTemp -= ConstValue.ScoreAdversaryLose;
				if( MatchInfo.RESULT_ADVERSARY_ABSENT == battleResult ) {
					memo += "本场缺席，";
					scoreTemp -= ConstValue.ScoreAbsent;
					isAbandon = Score.ABSENT_YES;
				}
				score += scoreTemp;
				memo += "积分" + scoreTemp + "],";
			}
		}
		
		//3. caculate score of bonus plat
		BattleInfoDAO bdao = new BattleInfoDAOImpl();
		List<Battle> battles = bdao.GetBattleInfoByPlayerId(player.getId(), active.getId());
		Battle battle = null;
		String bonusPlat = ConfigUtil.getInstance().getBonusPlat();
		if( bonusPlat == null) {
			bonusPlat = "";
		}
		String []bonusPlats = bonusPlat.split(",");
		List<Integer> platIds = new ArrayList<Integer>();
		for( String plat : bonusPlats ) {
			if( plat != null && plat.length() > 0 ) {
				platIds.add( Integer.parseInt(plat) );
			}
		}
		for( int k = 0; k < battles.size(); k++ ) {
			battle = battles.get(k);
			if( platIds.contains( battle.getMapId() ) ) {
				if( battle.getChallengerId() == player.getId() ) {
					if( battle.getResult() == Battle.RESULT_CHALLENGER_WIN || battle.getResult() == Battle.RESULT_ADVERSARY_ABSENT ) {
						reward1 += ConfigUtil.getInstance().getBonusPlatScore();
						memo += "[在加分地图(" + battle.getMapName() + ")上挑战  " + battle.getAdversaryLoginId() + "(" + battle.getAdversaryRace() + "-" + battle.getAdversaryRank() + ")" + " ，挑战成功，积分 " + ConfigUtil.getInstance().getBonusPlatScore() + "],";
					}
				}
				else if( battle.getAdversaryId() == player.getId() ) {
					if( battle.getResult() == Battle.RESULT_ADVERSARY_WIN || battle.getResult() == Battle.RESULT_CHALLENGER_ABSENT ) {
						reward1 += ConfigUtil.getInstance().getBonusPlatScore();
						memo += "[在加分地图(" + battle.getMapName() + ")上，被  " + battle.getChallengerLoginId() + "(" + battle.getChallengerRace() + "-" + battle.getChallengerRank() + ")" + " 挑战，守擂成功，积分 " + ConfigUtil.getInstance().getBonusPlatScore() + "],";
					}
				}
			}
		}
		//score += reward1; // add reward at the end 
		
		//4. caculate score of arrange day counts
		memo += "[本周报名比赛";
		List<MatchRegistrationDays> days = mdao.GetRegistrationDayByPlayer( active.getId(), player.getId() );
		if(days == null || days.size() == 0) {
			memo += "0天，" ;
			rewardAbandon -= ConstValue.ScoreArrangeDayCountZero;
			score += rewardAbandon;
			memo += "积分" + rewardAbandon;
		}
		else if( days.size() == 1 ) {
			memo += "1天，";
			rewardAbandon -= ConstValue.ScoreArrangeDayCountOne;
			score += rewardAbandon;
			memo += "积分" + rewardAbandon;
		}
		else {
			memo += days.size() + "天，";
			if( isAbandon == Score.ABSENT_NO ) {
				rewardAbandon = ( days.size() - 2 ) * ConstValue.ScoreArrangeDayCountStep;
				score += rewardAbandon;
				memo += "积分" + rewardAbandon;
			}
			else {
				rewardAbandon = 0;
				memo += "由于缺席比赛，无出勤积分";
			}
		}
		memo += "],";
		
		//5. get score record which contains sponsor's reward if exist.
		ScoreInfoService ss = new ScoreInfoService();
		Score scoreObj = ss.QueryCurrentRoundScoreByPlayer(player.getId());
		if( scoreObj == null ) {
			scoreObj = new Score();
			scoreObj.setRoundId(active.getId());
			scoreObj.setPlayerId(player.getId());
		}
		
		scoreObj.setLastScore(lastScore);
		scoreObj.setChallengerWin(challengerWin);
		scoreObj.setChallengerLose(challengerLose);
		scoreObj.setAdversaryWin(adversaryWin);
		scoreObj.setAdversaryLose(adversaryLose);
		scoreObj.setAbsent(isAbandon);
		scoreObj.setRewardAbandon(rewardAbandon);
		scoreObj.setReward1(reward1);
		rewardSponsor = scoreObj.getRewardSponsor();
		score += scoreObj.getLastScore() + rewardSponsor + scoreObj.getReward1() + scoreObj.getReward2() + scoreObj.getReward3();
		scoreObj.setScore(score);
		if(rewardSponsor != 0) {
			memo += "[本轮赛事委员会奖惩积分" +  rewardSponsor + "，" + scoreObj.getRewardSponsorReason() +"],";
		}
		scoreObj.setMemo(memo);
		
		//6. save score
		ss.CaculateAndSaveCurrentRoundPlayerScoreBy(scoreObj);
	}
	
	private boolean checkActiveMatchInfoResult() throws Exception {
		// if all of result has been saved ,then return true, otherwise return false
		boolean result = true;
		MatchDAO matchDao = new MatchDAOImpl();
		List<MatchInfo> infos = matchDao.GetActiveMatchInfo();
		if( infos == null || infos.size() == 0 ) {
			result = false;
		}
		else {
			for(int i = 0; i < infos.size(); i++) {
				if(infos.get(i).getResult() == 0) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	public void SavePlayerMatchRegistrationInfo(MatchRegistrationInfo regInfo) throws Exception {
		// get matchperiod
		MatchDAO matchDao = new MatchDAOImpl();
		RoundInfoService roundService = new RoundInfoService();
		Round active = roundService.GetActiveRound();
		
		matchDao.ClearAdversaries(active.getId(), regInfo.getPlayerId());
		if(regInfo.getAdversaryIds() != null) {
			for(int i = 0; i < regInfo.getAdversaryIds().size(); i++) {
				MatchRegistrationAdversary adversary = new MatchRegistrationAdversary();
				adversary.setRoundId(active.getId());
				adversary.setPlayerId(regInfo.getPlayerId());
				adversary.setAdversaryId(regInfo.getAdversaryIds().get(i));
				adversary.setPlatId(regInfo.getPlatIds().get(i));
				matchDao.SaveMatchRegistrationAdversary(adversary);
			}
		}
		
		matchDao.ClearDays(active.getId(), regInfo.getPlayerId());
		if( regInfo.getDayIds() != null ) {
			for(int j = 0; j < regInfo.getDayIds().size(); j++) {
				MatchRegistrationDays day = new MatchRegistrationDays();
				day.setRoundId(active.getId());
				day.setPlayerId(regInfo.getPlayerId());
				day.setFreeDay(regInfo.getDayIds().get(j));
				matchDao.SaveMatchRegistrationDay(day);
			}
		}
		
		
		ScoreInfoService ss = new ScoreInfoService();
		ss.SavePlayerActiveRoundSponsorReward(regInfo.getPlayerId(), regInfo.getScoreReward(), regInfo.getScoreRewardMemo());
		return;
	}

	public boolean IsRoundUsed( int roundId ) throws Exception {
		MatchDAO matchDao = new MatchDAOImpl();
		boolean result = matchDao.IsRoundUsedInMatch( roundId );
		return result;
	}

	public void SaveMatchInfoDetail( MatchInfo matchInfoDetail, List<Battle> battles ) throws Exception {
		if( matchInfoDetail == null ) {
			throw new Exception( "要保存的数据不能是空" );
		}
		
		// get matchinfo object by id
		MatchDAO dao = new MatchDAOImpl();
		MatchInfo info = dao.GetMatchInfoById( matchInfoDetail.getId() );
		if( info == null ) {
			throw new Exception( "can not found match info by id: " + matchInfoDetail.getId() );
		}
		
		// save match result 
		info.setResult( matchInfoDetail.getResult() );
		info.setScore( matchInfoDetail.getScore() );
		dao.SaveMatchInfo( info );
		
		BattleInfoDAO bdao = new BattleInfoDAOImpl();
		bdao.ClearBattleInfoByChallengerAndAdversary(info.getChallengerId(), info.getAdversaryId(), info.getRoundId());
		PlayerDAO pdao = new PlayerDAOImpl();
		PlatDAO platdao = new PlatDAOImpl();
		for(int i = 0; i<battles.size(); i++) {
			Player  player = pdao.GetPlayerById(info.getChallengerId());
			Battle battle = battles.get(i);
			if( player == null || player.getId() != battle.getChallengerId() ) {
				throw new Exception("对战信息不正确[挑战者信息与报名信息不匹配！]");
			}
			battle.setChallengerLoginId(player.getLoginId());
			battle.setChallengerName(player.getName());
			battle.setChallengerRank(info.getChallengerVranking());
			player = pdao.GetPlayerById(info.getAdversaryId());
			if( player == null || player.getId() != battle.getAdversaryId() ) {
				throw new Exception("对战信息不正确[擂主信息与报名信息不匹配！]");
			}
			battle.setAdversaryLoginId(player.getLoginId());
			battle.setAdversaryName(player.getName());
			battle.setAdversaryRank(info.getAdversaryVranking());
			battle.setRoundId(info.getRoundId());
			Plat plat = platdao.GetPlatByName(battle.getMapName());
			battle.setMapId(plat.getId());
			battle.setTimestamp(DateTimeUtil.GetCurrentTime());
			bdao.SaveBattleInfo(battle);
		}
		return;
	}

	public MatchRegistrationInfo QueryActiveMatchRegistrationInfoByPlayerId(
			int playerId) throws Exception {
		List<MatchRegistrationInfo> matchRegistrationInfos =  QueryActiveMatchRegistrationInfo();
		MatchRegistrationInfo result = null;
		MatchRegistrationInfo tmp = null;
		for(int i = 0; i < matchRegistrationInfos.size(); i++ ) {
			tmp = matchRegistrationInfos.get(i);
			if( playerId == tmp.getPlayerId() ) {
				result = tmp;
				break;
			}
		}
		return result;
	}
	
}

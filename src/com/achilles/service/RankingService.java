package com.achilles.service;

import java.util.List;
import java.util.Map;

import com.achilles.dao.RankingDAO;
import com.achilles.dao.impl.RankingDAOImpl;
import com.achilles.model.Ranking;

public class RankingService {
	
//	public Ranking QueryRankingByPlayerid(int roundId, int playerId) throws Exception {
//		RankingDAO dao = new RankingDAOImpl();
//		Ranking result = dao.GetRankingByPlayerid(roundId, playerId);
//		return result;
//	}
//	
//	public Ranking QuyeryRankingByRanking(int roundId, int ranking) throws Exception {
//		RankingDAO dao = new RankingDAOImpl();
//		Ranking result = dao.GetRankingByRanking(roundId, ranking);
//		return result;
//	}

	public void QueryActivePlayerRanking( Map<Integer, Integer> playerMap,  Map<Integer, Integer> indexMap) throws Exception {
		if(playerMap == null || indexMap == null) {
			throw new Exception("排名容器对象错误");
		}
		RankingDAO dao = new RankingDAOImpl();
		List<Ranking> rankings = dao.GetRankingOfActivePlayer();
		for(int i = 0; i<rankings.size(); i++) {
			playerMap.put(rankings.get(i).getPlayerId(), i+1);
			indexMap.put(i+1, rankings.get(i).getPlayerId());
		}
		return;
	}
	
	public void SaveRanking( Ranking ranking ) throws Exception {
		if(ranking == null ) {
			throw new Exception("排名容器对象错误");
		}
		RankingDAO dao = new RankingDAOImpl();
		Ranking isExist = dao.GetRankingByPlayerid(ranking.getRoundId(), ranking.getPlayerId());
		if(isExist != null) {
			ranking.setId(isExist.getId());
		}
		dao.SaveRanking(ranking);
		return;
	}
	
	public Ranking QueryRankingByPlayerid(int roundId, int playerId) throws Exception {
		RankingDAO dao = new RankingDAOImpl();
		Ranking result = dao.GetRankingByPlayerid(roundId, playerId);
		return result;
	}

	public void RemoveInitRoundRanking(Ranking target) throws Exception {
		RankingDAO dao = new RankingDAOImpl();
		dao.DelRankingByPlayerid(target.getRoundId(), target.getPlayerId());
		return;
	}

	public void ClearInitRanking(int roundId) throws Exception {
		RankingDAO dao = new RankingDAOImpl();
		dao.DelRankingByRoundId(roundId);
		return;
	}
}

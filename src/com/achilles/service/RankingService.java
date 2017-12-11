package com.achilles.service;

import com.achilles.dao.RankingDAO;
import com.achilles.dao.impl.RankingDAOImpl;
import com.achilles.model.Ranking;

public class RankingService {
	
	public Ranking QueryRankingByPlayerid(int matchPeriodId, int playerId) throws Exception {
		RankingDAO dao = new RankingDAOImpl();
		Ranking result = dao.GetRankingByPlayerid(matchPeriodId, playerId);
		return result;
	}
	
	public Ranking QuyeryRankingByRanking(int matchPeriodId, int ranking) throws Exception {
		RankingDAO dao = new RankingDAOImpl();
		Ranking result = dao.GetRankingByRanking(matchPeriodId, ranking);
		return result;
	}
	
//	public void CalculateActiveScore() {
//		
//	}
}

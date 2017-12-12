package com.achilles.dao;

import java.util.List;

import com.achilles.model.Ranking;

public interface RankingDAO {
	
	public Ranking SaveRanking(Ranking ranking) throws Exception;
	
	public Ranking GetRankingByPlayerid(int matchPeriodId, int playerId) throws Exception;

	public Ranking GetRankingByRanking(int matchPeriodId, int ranking) throws Exception;

	public List<Ranking> GetRankingOfActivePlayer() throws Exception;
}

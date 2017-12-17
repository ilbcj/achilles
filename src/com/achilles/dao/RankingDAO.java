package com.achilles.dao;

import java.util.List;

import com.achilles.model.Ranking;

public interface RankingDAO {
	
	public Ranking SaveRanking(Ranking ranking) throws Exception;
	
	public Ranking GetRankingByPlayerid(int roundId, int playerId) throws Exception;

	public Ranking GetRankingByRanking(int roundId, int ranking) throws Exception;

	public List<Ranking> GetRankingOfActivePlayer() throws Exception;
}

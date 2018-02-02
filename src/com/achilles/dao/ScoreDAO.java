package com.achilles.dao;


import java.util.List;

import com.achilles.model.Score;

public interface ScoreDAO {
	
	public Score SaveScore(Score score) throws Exception;
	
	public Score GetScoreByPlayerid(int roundId, int playerId) throws Exception;

	public List<Score> GetScoreByRanking() throws Exception;

	public List<Score> GetRoundScoreByRanking(int roundId) throws Exception;
	
	public List<Score> GetScoreByLastRoundRanking(int roundId) throws Exception;

//	public List<Score> GetScoreOfActivePlayer() throws Exception;
}

package com.achilles.dao;


import com.achilles.model.Score;

public interface ScoreDAO {
	
	public Score SaveScore(Score score) throws Exception;
	
	public Score GetScoreByPlayerid(int roundId, int playerId) throws Exception;

//	public List<Score> GetScoreOfActivePlayer() throws Exception;
}

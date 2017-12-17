package com.achilles.service;

import com.achilles.dao.ScoreDAO;
import com.achilles.dao.impl.ScoreDAOImpl;
import com.achilles.model.Round;
import com.achilles.model.Score;

public class ScoreInfoService {
	public Score QueryActiveMatchPeriodScoreByPlayer(int playerId) throws Exception {
		RoundInfoService roundService = new RoundInfoService();
		Round active = roundService.GetActiveRound();
		
		ScoreDAO scoreDao = new ScoreDAOImpl();
		Score score = scoreDao.GetScoreByPlayerid(active.getId(), playerId);
		return score;
	}

	public void SavePlayerActiveRoundSponsorReward(int playerId, int sponsorReward) throws Exception {
		RoundInfoService roundService = new RoundInfoService();
		Round active = roundService.GetActiveRound();
		
		ScoreDAO scoreDao = new ScoreDAOImpl();
		Score score = scoreDao.GetScoreByPlayerid(active.getId(), playerId);
		
		if(score == null) {
			score = new Score();
			score.setRoundId(active.getId());
			score.setPlayerId(playerId);
		}
		
		score.setRewardSponsor(sponsorReward);
		scoreDao.SaveScore(score);
		return;
	}
}

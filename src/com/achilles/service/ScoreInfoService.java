package com.achilles.service;

import com.achilles.dao.MatchDAO;
import com.achilles.dao.ScoreDAO;
import com.achilles.dao.impl.MatchDAOImpl;
import com.achilles.dao.impl.ScoreDAOImpl;
import com.achilles.model.MatchPeriod;
import com.achilles.model.Score;

public class ScoreInfoService {
	public Score QueryActiveMatchPeriodScoreByPlayer(int playerId) throws Exception {
		MatchDAO matchDao = new MatchDAOImpl();
		MatchPeriod active = matchDao.GetActivePeriod();
		
		ScoreDAO scoreDao = new ScoreDAOImpl();
		Score score = scoreDao.GetScoreByPlayerid(active.getId(), playerId);
		return score;
	}

	public void SavePlayerActiveMatchPeriodSponsorReward(int playerId, int sponsorReward) throws Exception {
		MatchDAO matchDao = new MatchDAOImpl();
		MatchPeriod active = matchDao.GetActivePeriod();
		
		ScoreDAO scoreDao = new ScoreDAOImpl();
		Score score = scoreDao.GetScoreByPlayerid(active.getId(), playerId);
		
		if(score == null) {
			score = new Score();
			score.setMatchPeriodId(active.getId());
			score.setPlayerId(playerId);
		}
		
		score.setRewardSponsor(sponsorReward);
		scoreDao.SaveScore(score);
		return;
	}
}

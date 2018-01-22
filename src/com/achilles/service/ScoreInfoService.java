package com.achilles.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.achilles.dao.ScoreDAO;
import com.achilles.dao.impl.ScoreDAOImpl;
import com.achilles.model.Player;
import com.achilles.model.Round;
import com.achilles.model.Score;

public class ScoreInfoService {
	public Score QueryCurrentRoundScoreByPlayer(int playerId) throws Exception {
		RoundInfoService roundService = new RoundInfoService();
		Round active = roundService.GetActiveRound();
		
		ScoreDAO scoreDao = new ScoreDAOImpl();
		Score score = scoreDao.GetScoreByPlayerid(active.getId(), playerId);
		return score;
	}

	public void SavePlayerActiveRoundSponsorReward(int playerId, int sponsorReward, String sponsorRewardMemo) throws Exception {
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
		score.setRewardSponsorReason(sponsorRewardMemo);
		scoreDao.SaveScore(score);
		return;
	}
	
	public List<Score> QueryCurrentRoundScoreByRanking() throws Exception {
		ScoreDAO scoreDao = new ScoreDAOImpl();
		List<Score> scores = scoreDao.GetScoreByRanking();
		return scores;
	}

	public void CaculateAndSaveCurrentRoundPlayerScoreBy(Score score) throws Exception {
		ScoreDAO scoreDao = new ScoreDAOImpl();
		scoreDao.SaveScore(score);
	}

	public List<Score> QueryRoundCurrentByRanking(int roundId) throws Exception {
		ScoreDAO scoreDao = new ScoreDAOImpl();
		List<Score> scores = scoreDao.GetRoundScoreByRanking(roundId);
		
		List<Player> players = new PlayerInfoService().QueryAllActivePlayer();
		Map<Integer, Player> playerMap = new HashMap<Integer,Player>();
		Player player = null;
		for(int x = 0; x<players.size(); x++) {
			player = players.get(x);
			playerMap.put(player.getId(), player);
		}
		
		Score score = null;
		for(int i = 0; i < scores.size(); i++) {
			score = scores.get( i );
			score.setRanking( i + 1 );
			score.setPlayerName( playerMap.get( score.getPlayerId() ).getLoginId() + "[" + playerMap.get( score.getPlayerId() ).getRace() + "]" );
		}
		
		Collections.sort(scores, new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return o2.getLastScore()-o1.getLastScore();
            }
        });
		
		for(int i = 0; i < scores.size(); i++) {
			score = scores.get( i );
			score.setLastRanking( i + 1 );
		}
		
		Collections.sort(scores, new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return o2.getScore()-o1.getScore();            }
        });
		return scores;
	}

}

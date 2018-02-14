package com.achilles.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.achilles.dao.SeasonDAO;
import com.achilles.dao.impl.SeasonDAOImpl;
import com.achilles.model.Player;
import com.achilles.model.Ranking;
import com.achilles.model.Round;
import com.achilles.model.Season;
import com.achilles.util.ConfigUtil;
import com.achilles.util.DateTimeUtil;

public class SeasonInfoService {

	public long QuerySeasons(int start, int length, List<Season> seasons) throws Exception {
		SeasonDAO dao = new SeasonDAOImpl();
		List<Season> tmp = dao.GetSeasons(start, length);
		seasons.addAll(tmp);
		
		long count = 0;
		count = dao.GetSeasonsCount();
		return count;
	}

	public Season QuerySeasonsById(int seasonId) throws Exception {
		SeasonDAO dao = new SeasonDAOImpl();
		Season season = dao.GetSeasonById(seasonId);
		return season;
	}
	
	public void SaveSeason(Season season) throws Exception {
		SeasonDAO dao = new SeasonDAOImpl();
		
		boolean isCreate = false;
		if(season.getId() == 0) {
			// create new season
			isCreate = true;
			season.setTimestamp(DateTimeUtil.GetCurrentTime());
		}
		season.setStatus(Season.STATUS_USING);
		season = dao.AddSeason(season);
		
		if( isCreate ) {
			//clean last season's round
			RoundInfoService rs = new RoundInfoService();
			Round active = rs.GetActiveRound();
			if( active != null ) {
				throw new Exception("仍有比赛场次正在进行中，无法创建新赛季！");
			}
			Round last = rs.getLastRound();
			if( last != null ) {
				last.setStatus(Round.STATUS_HISTORY);
				rs.SaveRound(last);
			}
			Round init = rs.getInitRound();
			if( init != null ) {
				init.setStatus(Round.STATUS_OLD_INIT);
				rs.SaveRound(init);
			}
			//create current season init round
			rs.GenerateInitRound(season.getId());
		}
		return;
	}

	public void DeleteSeasons(List<Integer> delIds) throws Exception {
		SeasonDAO dao = new SeasonDAOImpl();
		Season target;
		for(int i = 0; i < delIds.size(); i++) {
			target = new Season();
			target.setId(delIds.get(i));
			
			dao.DelSeason(target);
		}
		return;
	}

	public List<String> InitSeasons() throws Exception {
		// get init round
		RoundInfoService rs = new RoundInfoService();
		Round init = rs.getInitRound();
		if( init == null ) {
			throw new Exception("赛季信息不正常，无法进行初始排名操作。");
		}
		
		// get all active players
		PlayerInfoService ps = new PlayerInfoService();
		List<Player> players = ps.QueryAllActivePlayer();
		// generate random which use for ranking
		for(int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			Random r = new Random();
			player.setRanking(r.nextInt(10000));
		}
		// score by ranking
		Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return o1.getRanking()-o2.getRanking();
            }
        });
		// generate init ranking record
		RankingService rankingService = new RankingService();
		Ranking ranking = null;
		List<String> rankInfo = new ArrayList<String>();
		rankingService.ClearInitRanking(init.getId());
		for( int j = 0; j < players.size(); j++ ) {
			Player player = players.get( j );
			ranking = new Ranking();
			ranking.setPlayerId(player.getId());
			ranking.setRoundId(init.getId());
			int score = ConfigUtil.getInstance().getMaxInitTopOneScore() - j * ConfigUtil.getInstance().getInitScoreDiminishingStep();
			ranking.setScore(score);
			ranking.setRanking(j+1);
			rankingService.SaveRanking(ranking);
			String message = "第" + ranking.getRanking() + "名 -- " + player.getLoginId() + ", 初始积分：" + ranking.getScore();
			rankInfo.add(message);
		}
		
		return rankInfo;
	}

}

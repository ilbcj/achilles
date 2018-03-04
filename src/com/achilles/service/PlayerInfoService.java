package com.achilles.service;

import java.util.ArrayList;
import java.util.List;

import com.achilles.dao.PlayerDAO;
import com.achilles.dao.RankingDAO;
import com.achilles.dao.impl.PlayerDAOImpl;
import com.achilles.dao.impl.RankingDAOImpl;
import com.achilles.dto.PlayerDetail;
import com.achilles.model.Player;
import com.achilles.model.Ranking;
import com.achilles.model.Round;
import com.achilles.util.ConfigUtil;
import com.achilles.util.DateTimeUtil;

public class PlayerInfoService {
	public long QueryPlayer(String loginId, String name, int start, int length, List<Player> players) throws Exception {
		PlayerDAO dao = new PlayerDAOImpl();
		Player criteria = new Player();
		criteria.setLoginId(loginId);
		criteria.setName(name);
		criteria.setStatus(Player.STATUS_USING);
		List<Player> tmp = dao.GetPlayers(criteria, start, length);
		players.addAll(tmp);
		
		long count = 0;
		count = dao.QueryPlayersCount(criteria);
		return count;
	}

	public void SavePlayer(Player player) throws Exception {
		PlayerDAO dao = new PlayerDAOImpl();
		Player criteria = new Player();
		criteria.setStatus(Player.STATUS_USING);
		long count = 0;
		count = dao.QueryPlayersCount(criteria);
		if(count > ConfigUtil.getInstance().getMaxPlayersCount()) {
			throw new Exception("目前已达到选手数额上限，不能创建选手信息。");
		}
		
		player.setStatus(Player.STATUS_USING);
		player.setTimestamp(DateTimeUtil.GetCurrentTime());
		player = dao.AddPlayer(player);
		
		// still need to add default ranking of add player when season has already start
		//// no more need to add default ranking of season's init round
		////// add init match period ranking
		RoundInfoService rs = new RoundInfoService();
		Round initRound = rs.getInitRound();
		Ranking ranking = new Ranking();
		ranking.setRoundId(initRound.getId());
		ranking.setPlayerId(player.getId());
		ranking.setRanking((int)count);
		int score = (int)(ConfigUtil.getInstance().getMaxInitTopOneScore() - count * ConfigUtil.getInstance().getInitScoreDiminishingStep());
		ranking.setScore(score);
		RankingDAO rDao = new RankingDAOImpl();
		rDao.SaveRanking(ranking);		
		return;
	}

	public void DeletePlayers(List<Integer> delIds) throws Exception {
		PlayerDAO dao = new PlayerDAOImpl();
		Player target;
		for(int i = 0; i < delIds.size(); i++) {
			target = new Player();
			target.setId(delIds.get(i));
			
			dao.DelPlayer(target);
		}
		return;
	}

	public PlayerDetail QueryPlayerDetailInfo(int id) throws Exception {
		PlayerDAO dao = new PlayerDAOImpl();
		Player player = dao.GetPlayerById(id);
		PlayerDetail detail = new PlayerDetail();
		detail.setBase(player);
		
		//TODO: query and fill other info
		return detail;
	}
	
	public List<Player> QueryAllPlayer() throws Exception {
		List<Player> players = new ArrayList<Player>();
		PlayerDAO playerDAO = new PlayerDAOImpl();
		Player criteria = new Player();
		List<Player> tmp = playerDAO.GetPlayers(criteria, 0, ConfigUtil.getInstance().getMaxPlayersCount());
		players.addAll(tmp);
		criteria.setStatus(Player.STATUS_USING);
		tmp = playerDAO.GetPlayers(criteria, 0, ConfigUtil.getInstance().getMaxPlayersCount());
		players.addAll(tmp);
		return players;
	}
	
	public List<Player> QueryAllActivePlayer() throws Exception {
		PlayerDAO playerDAO = new PlayerDAOImpl();
		Player criteria = new Player();
		criteria.setStatus(Player.STATUS_USING);
		List<Player> players = playerDAO.GetPlayers(criteria, 0, ConfigUtil.getInstance().getMaxPlayersCount());
		return players;
	}
	
	public void TestInitPlayers() throws Exception {
		PlayerDAO dao = new PlayerDAOImpl();
		Player criteria = new Player();
		criteria.setStatus(Player.STATUS_USING);
		long count = 0;
		count = dao.QueryPlayersCount(criteria);
		int index = 0;
		String[] names = {"李允烈", "廉宝成", "马在允", "安相元", "姜珉", "金泽龙", "朴圣浚", "林耀焕", "宋炳具", "朱指导", "徐志勋", "洪臻浩", "朴粲守", "晋永守", "炮哥", "李永浩", "李济东", "金俊永", "许勇石", "吴泳钟", "金允焕", "尹龙泰", "朴泰民", "崔然星", "朴志浩", "朴正石", "吴中勋", "朴永珉", "申熙升", "郑明勋", "全太阳", "许英茂", "申东元", "申大根", "金正宇", "李在浩", "张允哲", "韩相奉", "金古贤", "金贤宇", "申相文", "朴世程", "孙书兴", "金敏哲", "全相昱", "秦永华", "边衡泰", "朴俊吾", "李升石", "朴成钧"};
		String[] loginIds = {"Nada", "Sea", "Savior", "Shine", "nal_ra", "Bisu", "July", "Boxer", "Stork", "zhuzhidao", "Xellos", "YellOw", "Luxury", "Hwasin", "sanpao", "Flash", "Jaedong", "GGPlay", "Attack", "Anytime", "Calm", "free", "GoRush", "iloveoov", "Pusan", "Reach", "Shudder", "Much", "UpMagiC", "Fantasy", "TY", "Jangbi", "Hydra", "hyvaa", "effort", "Light", "snow", "Kwanro", "KaL", "Modesty", "leta", "Pure", "Lomo", "SoulKey", "Midas", "Movie", "Iris", "Killer", "s2", "mind"};
		while( count < ConfigUtil.getInstance().getMaxPlayersCount() && index < names.length && index < loginIds.length ) {
			try{
				Player tmp = new Player();
				tmp.setLoginId( loginIds[index] );
				tmp.setPwd(tmp.getLoginId());
				tmp.setName( names[index] );
				tmp.setRace(index%3 == 0 ? "T" : (index%3 == 1 ? "P" : "Z"));
				SavePlayer(tmp);
			}
			catch(Exception e) {
				System.out.print(e.getMessage());
			}
			index++;
			count = dao.QueryPlayersCount(criteria);
		}
		return;
	}
}

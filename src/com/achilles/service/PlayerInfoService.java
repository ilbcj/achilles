package com.achilles.service;

import java.util.List;

import com.achilles.dao.PlayerDAO;
import com.achilles.dao.impl.PlayerDAOImpl;
import com.achilles.dto.PlayerDetail;
import com.achilles.model.Player;
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
		player.setStatus(Player.STATUS_USING);
		player.setTimestamp(DateTimeUtil.GetCurrentTime());
		dao.AddPlayer(player);
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

	public void TestInitPlayers() throws Exception {
		PlayerDAO dao = new PlayerDAOImpl();
		Player criteria = new Player();
		criteria.setStatus(Player.STATUS_USING);
		long count = 0;
		count = dao.QueryPlayersCount(criteria);
		int number = 1;
		while( count < 50 ) {
			Player tmp = new Player();
			tmp.setLoginId("tester" + number);
			tmp.setPwd("" + number);
			tmp.setName("tester" + number);
			tmp.setRace(number%3 == 0 ? "T" : (number%3 == 1 ? "P" : "Z"));
			tmp.setTimestamp(DateTimeUtil.GetCurrentTime());
			tmp.setStatus(Player.STATUS_USING);
			dao.AddPlayer(tmp);
			number++;
			count = dao.QueryPlayersCount(criteria);
		}
		return;
	}
}

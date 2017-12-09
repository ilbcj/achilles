package com.achilles.service;

import java.util.List;

import com.achilles.dao.PlayerDAO;
import com.achilles.dao.impl.PlayerDAOImpl;
import com.achilles.model.Player;

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
}

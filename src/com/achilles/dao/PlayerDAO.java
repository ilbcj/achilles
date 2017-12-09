package com.achilles.dao;

import java.util.List;
import com.achilles.model.Player;

public interface PlayerDAO {
	public List<Player> GetPlayers(Player criteria, int start, int length) throws Exception;
	public long QueryPlayersCount(Player criteria)throws Exception;
	public void AddPlayer(Player player) throws Exception;
	public void DelPlayer(Player player) throws Exception;
	public Player GetPlayerById(int id) throws Exception;

}

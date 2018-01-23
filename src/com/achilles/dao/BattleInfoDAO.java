package com.achilles.dao;

import java.util.List;

import com.achilles.model.Battle;

public interface BattleInfoDAO {
	public Battle SaveBattleInfo(Battle battle) throws Exception;
	public void ClearBattleInfoByChallengerAndAdversary(int challengerId, int adversaryId, int roundId) throws Exception;
	public List<Battle> GetBattleInfoByChallengerAndAdversary(int challengerId, int adversaryId, int roundId) throws Exception;
	public List<Battle> GetCurrentRoundBattleInfo() throws Exception;
	public Battle GetBattleInfoById(int id) throws Exception;
}

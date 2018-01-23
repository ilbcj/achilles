package com.achilles.service;

import java.util.List;

import com.achilles.model.Battle;
import com.achilles.dao.BattleInfoDAO;
import com.achilles.dao.impl.BattleInfoDAOImpl;

public class BattleInfoService {

	public List<Battle> QueryCurrentRoundBattleInfos() throws Exception {
		BattleInfoDAO dao = new BattleInfoDAOImpl();
		List<Battle> result = dao.GetCurrentRoundBattleInfo();
		return result;
	}

	public void SaveBattleRepName(int battleId, String repName) throws Exception {
		BattleInfoDAO dao = new BattleInfoDAOImpl();
		Battle battle = dao.GetBattleInfoById(battleId);
		if(battle == null) {
			throw new Exception("要保存信息的战役不存在。[id: " + battleId + "]");
		}
		
		battle.setVod(repName);
		dao.SaveBattleInfo(battle);
		return;
	}

}

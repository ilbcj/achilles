package com.achilles.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.achilles.dao.ConfigDAO;
import com.achilles.dao.impl.ConfigDAOImpl;
import com.achilles.dto.ConfigInfo;
import com.achilles.model.Config;
import com.achilles.util.ConfigUtil;

public class ConfigInfoService {

	public ConfigInfo QuerySystemConfigInfo() throws Exception {
		ConfigDAO dao = new ConfigDAOImpl();
		List<Config> items = dao.GetAllConfigItems();
		Map<String, Config> itemsMap = new HashMap<String, Config>();
		
		Config item = null;
		for(int i = 0; i < items.size(); i++) {
			item = items.get(i);
			itemsMap.put(item.getName(), item);
		}
		
		ConfigInfo info = new ConfigInfo();
		
		item = itemsMap.get(Config.NAME_FIRSTPLAYERACCEPTCHALLENGECOUNT);
		if(item != null) {
			info.setFirstPlayerAcceptChallengeCount(item.getIval());
		}
		
		item = itemsMap.get(Config.NAME_INITMATCHPERIODID);
		if(item != null) {
			info.setInitMatchPeriodId(item.getIval());
		}
		
		item = itemsMap.get(Config.NAME_INITSCOREDIMINISHINGSTEP);
		if(item != null) {
			info.setInitScoreDiminishingStep(item.getIval());
		}
		
		item = itemsMap.get(Config.NAME_MAXCHALLENGECOUNT);
		if(item != null) {
			info.setMaxChallengeCount(item.getIval());
		}
		
		item = itemsMap.get(Config.NAME_MAXDATERANGE);
		if(item != null) {
			info.setMaxDateRange(item.getIval());
		}
		
		item = itemsMap.get(Config.NAME_MAXINITTOPONESCORE);
		if(item != null) {
			info.setMaxInitTopOneScore(item.getIval());
		}
		
		item = itemsMap.get(Config.NAME_MAXPERCENTOFCHALLENGERWIN);
		if(item != null) {
			info.setMaxPercentOfChallengerWin(item.getIval());
		}
		
		item = itemsMap.get(Config.NAME_MAXPLAYERSCOUNT);
		if(item != null) {
			info.setMaxPlayersCount(item.getIval());
		}
		
		item = itemsMap.get(Config.NAME_MINACCEPTCHALLENGECOUNT);
		if(item != null) {
			info.setMinAcceptChallengeCount(item.getIval());
		}
		
		item = itemsMap.get(Config.NAME_PERCENTOFCHALLENGERWINDIMINISHINGSTEP);
		if(item != null) {
			info.setPercentOfChallengerWinDiminishingStep(item.getIval());
		}
		
		item = itemsMap.get(Config.NAME_RATEOFCHOSENMONDYTOTHURSDAY);
		if(item != null) {
			info.setRateOfChosenMondyToThursday(item.getIval());
		}
		
		item = itemsMap.get(Config.NAME_RATEOFCHOSENSATURDAYTOSUNDAY);
		if(item != null) {
			info.setRateOfChosenSaturdayToSunday(item.getIval());
		}
		
		return info;
	}

	public void SaveSystemConfigInfo(ConfigInfo config) throws Exception {
		ConfigDAO dao = new ConfigDAOImpl();
		Config configItem = null;
		
		configItem = new Config();
		configItem.setName(Config.NAME_FIRSTPLAYERACCEPTCHALLENGECOUNT);
		configItem.setIval(config.getFirstPlayerAcceptChallengeCount());
		dao.SaveConfigItem(configItem);
		
		configItem = new Config();
		configItem.setName(Config.NAME_INITMATCHPERIODID);
		configItem.setIval(config.getInitMatchPeriodId());
		dao.SaveConfigItem(configItem);
		
		configItem = new Config();
		configItem.setName(Config.NAME_INITSCOREDIMINISHINGSTEP);
		configItem.setIval(config.getInitScoreDiminishingStep());
		dao.SaveConfigItem(configItem);
		
		configItem = new Config();
		configItem.setName(Config.NAME_MAXCHALLENGECOUNT);
		configItem.setIval(config.getMaxChallengeCount());
		dao.SaveConfigItem(configItem);
		
		configItem = new Config();
		configItem.setName(Config.NAME_MAXDATERANGE);
		configItem.setIval(config.getMaxDateRange());
		dao.SaveConfigItem(configItem);
		
		configItem = new Config();
		configItem.setName(Config.NAME_MAXINITTOPONESCORE);
		configItem.setIval(config.getMaxInitTopOneScore());
		dao.SaveConfigItem(configItem);
		
		configItem = new Config();
		configItem.setName(Config.NAME_MAXPERCENTOFCHALLENGERWIN);
		configItem.setIval(config.getMaxPercentOfChallengerWin());
		dao.SaveConfigItem(configItem);
		
		configItem = new Config();
		configItem.setName(Config.NAME_MAXPLAYERSCOUNT);
		configItem.setIval(config.getMaxPlayersCount());
		dao.SaveConfigItem(configItem);
		
		configItem = new Config();
		configItem.setName(Config.NAME_MINACCEPTCHALLENGECOUNT);
		configItem.setIval(config.getMinAcceptChallengeCount());
		dao.SaveConfigItem(configItem);
		
		configItem = new Config();
		configItem.setName(Config.NAME_PERCENTOFCHALLENGERWINDIMINISHINGSTEP);
		configItem.setIval(config.getPercentOfChallengerWinDiminishingStep());
		dao.SaveConfigItem(configItem);
		
		configItem = new Config();
		configItem.setName(Config.NAME_RATEOFCHOSENMONDYTOTHURSDAY);
		configItem.setIval(config.getRateOfChosenMondyToThursday());
		dao.SaveConfigItem(configItem);
		
		configItem = new Config();
		configItem.setName(Config.NAME_RATEOFCHOSENSATURDAYTOSUNDAY);
		configItem.setIval(config.getRateOfChosenSaturdayToSunday());
		dao.SaveConfigItem(configItem);
		
		ConfigUtil.UpdateConfig(config);
	}

}

package com.achilles.util;

import com.achilles.dto.ConfigInfo;
import com.achilles.service.ConfigInfoService;

public class ConfigUtil {
	
	private static ConfigInfo inst;
	
	private ConfigUtil() {
	}
	
	public static ConfigInfo getInstance() throws Exception {
		if( inst == null ) {
			inst = new ConfigInfoService().QuerySystemConfigInfo();
		}
		
		return inst;
	}
	
	public static void UpdateConfig(ConfigInfo newConfig) {
		inst = (ConfigInfo) newConfig.clone();
	}
}

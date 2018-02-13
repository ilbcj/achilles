package com.achilles.service;

import java.util.ArrayList;
import java.util.List;

import com.achilles.dao.PlatDAO;
import com.achilles.dao.impl.PlatDAOImpl;
import com.achilles.dto.ConfigInfo;
import com.achilles.model.Plat;

public class PlatService {
	
	public List<Plat> QueryAllMaps() throws Exception {
		PlatDAO dao = new PlatDAOImpl();
		List<Plat> active = dao.GetPlatByStatus(Plat.STATUS_ACTIVE);
		return active;
	}

	public List<Plat> QueryActiveMaps() throws Exception {
		
		
		PlatDAO dao = new PlatDAOImpl();
		List<Plat> plats = dao.GetPlatByStatus(Plat.STATUS_ACTIVE);
		ConfigInfoService service = new ConfigInfoService();
		ConfigInfo config = service.QuerySystemConfigInfo();
		String strIds = config.getActivePlat();
		if(strIds == null || strIds.length() == 0) {
			return new ArrayList<Plat>();
		}
		
		String[] strArrayIds = strIds.split(",");
		List<Integer> activeIds = new ArrayList<Integer>();
		for(String strid : strArrayIds) {
			try{
				activeIds.add(Integer.parseInt(strid));
			}
			catch(Exception e) {
				System.out.println("ActivePlat has no id value:" + strIds);
			}
		}
		List<Plat> active = new ArrayList<Plat>();
		for( Plat plat : plats) {
			Integer id = plat.getId();
			if( activeIds.contains(id) ) {
				active.add(plat);
			}
		}
		return active;
	}
	
}

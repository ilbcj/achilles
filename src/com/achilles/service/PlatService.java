package com.achilles.service;

import java.util.List;

import com.achilles.dao.PlatDAO;
import com.achilles.dao.impl.PlatDAOImpl;
import com.achilles.model.Plat;

public class PlatService {
	
	public List<Plat> QueryAllMaps() throws Exception {
		PlatDAO dao = new PlatDAOImpl();
		List<Plat> plats = dao.GetPlatByStatus(Plat.STATUS_ACTIVE);
		return plats;
	}
	
}

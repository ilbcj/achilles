package com.achilles.dao;

import java.util.List;

import com.achilles.model.Plat;


public interface PlatDAO {

	public Plat AddPlat(Plat plat) throws Exception;

	public void DelPlat(Plat target) throws Exception;

	public List<Plat> GetPlatByStatus(int status) throws Exception;

	public Plat GetPlatById(int platId) throws Exception;
	
}

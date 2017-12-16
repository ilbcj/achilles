package com.achilles.dao;

import java.util.List;

import com.achilles.model.Season;

public interface SeasonDAO {

	public List<Season> GetSeasons(int start, int length) throws Exception;

	public long GetSeasonsCount() throws Exception;

	public Season AddSeason(Season season) throws Exception;

	public void DelSeason(Season target) throws Exception;

}

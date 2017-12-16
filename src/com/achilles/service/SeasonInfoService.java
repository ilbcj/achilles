package com.achilles.service;

import java.util.List;

import com.achilles.dao.SeasonDAO;
import com.achilles.dao.impl.SeasonDAOImpl;
import com.achilles.model.Season;
import com.achilles.util.DateTimeUtil;

public class SeasonInfoService {

	public long QuerySeasons(int start, int length, List<Season> seasons) throws Exception {
		SeasonDAO dao = new SeasonDAOImpl();
		List<Season> tmp = dao.GetSeasons(start, length);
		seasons.addAll(tmp);
		
		long count = 0;
		count = dao.GetSeasonsCount();
		return count;
	}

	public void SaveSeason(Season season) throws Exception {
		SeasonDAO dao = new SeasonDAOImpl();
		
		season.setStatus(Season.STATUS_USING);
		season.setTimestamp(DateTimeUtil.GetCurrentTime());
		dao.AddSeason(season);
		
		return;		
	}

	public void DeleteSeasons(List<Integer> delIds) throws Exception {
		SeasonDAO dao = new SeasonDAOImpl();
		Season target;
		for(int i = 0; i < delIds.size(); i++) {
			target = new Season();
			target.setId(delIds.get(i));
			
			dao.DelSeason(target);
		}
		return;
	}

}

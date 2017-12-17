package com.achilles.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.achilles.dao.MatchDAO;
import com.achilles.dao.RoundDAO;
import com.achilles.dao.SeasonDAO;
import com.achilles.dao.impl.MatchDAOImpl;
import com.achilles.dao.impl.RoundDAOImpl;
import com.achilles.dao.impl.SeasonDAOImpl;
import com.achilles.model.Round;
import com.achilles.model.Season;
import com.achilles.util.ConfigUtil;
import com.achilles.util.DateTimeUtil;

public class RoundInfoService {

	public long QueryRounds( int start, int length, List<Round> scenes ) throws Exception {
		RoundDAO dao = new RoundDAOImpl();
		List<Round> tmp = dao.GetRounds( start, length );
		if( tmp != null && tmp.size() > 0 ) {
			SeasonDAO sdao = new SeasonDAOImpl();
			List<Season> seasons = sdao.GetSeasons(0, 0);
			Map<Integer, String> seasonNameMap = new HashMap<Integer, String>();
			for(int i = 0; i < seasons.size(); i++) {
				Season season = seasons.get(i);
				seasonNameMap.put(season.getId(), season.getName());
			}
			
			for(int j = 0; j < tmp.size(); j++) {
				Round round = tmp.get(j);
				round.setSeasonName( seasonNameMap.get( round.getSeasonId()) );
				if( round.getLastRoundId() == 0 ) {
					round.setLastRoundName( "没有上一场比赛" );
				}
				else {
					Round last = dao.GetRoundById(round.getLastRoundId());
					round.setLastRoundName(last == null ? "查询上一场信息出错" : last.getName());
				}
			}
		}
		scenes.addAll( tmp );
		
		long count = 0;
		count = dao.GetRoundsCount();
		return count;
	}

	public void SaveRound( Round round ) throws Exception {
		RoundDAO dao = new RoundDAOImpl();
		Round current = GetActiveRound();
		
		if( round.getId() == 0 ) {
			if( current != null ) {
				throw new Exception("当前轮比赛尚未归档，不能新建赛程");
			}
			round.setTimestamp(DateTimeUtil.GetCurrentTime());
			round.setStatus(Round.STATUS_ACTIVE);
			Round last = getLastRound();
			round.setLastRoundId(last == null ? ConfigUtil.getInstance().getInitRoundId() : last.getId());
		}
		dao.AddRound(round);
		
		return;
	}
	
	public void DeleteRounds( List<Integer> delIds ) throws Exception {
		RoundDAO dao = new RoundDAOImpl();
		Round target;
		MatchDAO mdao = new MatchDAOImpl();
		for(int i = 0; i < delIds.size(); i++) {
			int id = delIds.get( i );
			if( id == ConfigUtil.getInstance().getInitRoundId() || mdao.IsRoundUsedInMatch( id ) ) {
				throw new Exception("场次[id:" + id + "]已经在使用中，不能被删除");
			}
			target = new Round();
			target.setId( id );
			
			dao.DelRound( target );
		}
		return;
	}
	
	public Round GetActiveRound() throws Exception {
		RoundDAO dao = new RoundDAOImpl();
		List<Round> rounds = dao.GetRoundByStatus( Round.STATUS_ACTIVE );
		Round result = null;
		
		if( rounds.size() == 0 ) {
			result = null;
		}
		else if( rounds.size() == 1 ) {
			result = rounds.get( 0 );
		}
		else if( rounds.size() > 1 ) {
			throw new Exception( "存在多个当前活动场次，请联系系统维护人员" );
		}
		return result;
	}
	
	private Round getLastRound() throws Exception {
		RoundDAO dao = new RoundDAOImpl();
		List<Round> rounds = dao.GetRoundByStatus( Round.STATUS_LAST_ACTIVE );
		Round result = null;
		
		if( rounds.size() == 0 ) {
			result = null;
		}
		else if( rounds.size() == 1 ) {
			result = rounds.get(0);
		}
		else if( rounds.size() > 1 ) {
			throw new Exception( "存在多个上一轮活动场次，请联系系统维护人员" );
		}
		return result;
	}

	public void ArchiveRound(int roundId) throws Exception {
		RoundDAO dao = new RoundDAOImpl();
		Round active = dao.GetRoundById(roundId);
		if( active.getStatus() != Round.STATUS_ACTIVE ) {
			throw new Exception("指定场次不是当前比赛场次，无法进行归档操作！");
		}
		
		MatchInfoService service = new MatchInfoService();
		service.ArchiveActiveMatchInfo(roundId);
		
		Round last = this.getLastRound();
		if( last != null ) {
			last.setStatus( Round.STATUS_HISTORY );
			dao.AddRound( last );
		}
		
		active.setStatus(Round.STATUS_LAST_ACTIVE);
		dao.AddRound( active );
		
		return;
	}

}

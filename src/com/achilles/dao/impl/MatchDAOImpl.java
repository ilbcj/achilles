package com.achilles.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.achilles.dao.MatchDAO;
import com.achilles.model.Battle;
import com.achilles.model.HibernateUtil;
import com.achilles.model.MatchInfo;
import com.achilles.model.Round;
import com.achilles.model.MatchRegistrationAdversary;
import com.achilles.model.MatchRegistrationDays;

public class MatchDAOImpl implements MatchDAO {

	@SuppressWarnings("unchecked")
	@Override
	public MatchRegistrationAdversary SaveMatchRegistrationAdversary(MatchRegistrationAdversary adversary) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<MatchRegistrationAdversary> rs = null;
		String sqlString = "SELECT * FROM match_registration_adversary WHERE player_id=:player_id and round_id = :round_id and adversary_id = :adversary_id ";
		
		try
		{
			Query q = session.createSQLQuery(sqlString).addEntity(MatchRegistrationAdversary.class);
			q.setInteger("player_id", adversary.getPlayerId());
			q.setInteger("round_id", adversary.getRoundId());
			q.setInteger("adversary_id", adversary.getAdversaryId());
			rs = q.list();
			if(rs.size() == 0) {
				adversary = (MatchRegistrationAdversary)session.merge(adversary);
			}
			else if(rs.size() == 1) {
				adversary = rs.get(0);
			}
			else {
				throw new Exception("选手[id:" + adversary.getPlayerId() + "在比赛[id:" + adversary.getRoundId() + "]中要挑战的对手[id:" + adversary.getAdversaryId() + "]信息重复，请管理员检查后台数据" );
			}
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return adversary;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MatchRegistrationDays SaveMatchRegistrationDay(MatchRegistrationDays day) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<MatchRegistrationDays> rs = null;
		String sqlString = "SELECT * FROM match_registration_days WHERE player_id=:player_id and round_id = :round_id and free_day = :free_day ";
		
		try
		{
			Query q = session.createSQLQuery(sqlString).addEntity(MatchRegistrationDays.class);
			q.setInteger("player_id", day.getPlayerId());
			q.setInteger("round_id", day.getRoundId());
			q.setInteger("free_day", day.getFreeDay());
			rs = q.list();
			if(rs.size() == 0) {
				day = (MatchRegistrationDays)session.merge(day);
			}
			else if(rs.size() == 1) {
				day = rs.get(0);
			}
			else {
				throw new Exception("选手[id:" + day.getPlayerId() + "在比赛[id:" + day.getRoundId() + "]中选择的空闲时间[id:" + day.getFreeDay() + "]信息重复，请管理员检查后台数据" );
			}
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return day;
	}

	@Override
	public void ClearAdversaries(int roundId, int playerId)
			throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		String sqlString = "delete from match_registration_adversary WHERE player_id=:player_id and round_id = :round_id ";
		
		try {
			Query q = session.createSQLQuery(sqlString);
			q.setInteger("player_id", playerId);
			q.setInteger("round_id", roundId);
			q.executeUpdate();
			
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return;		
	}

	@Override
	public void ClearDays(int roundId, int playerId) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		String sqlString = "delete from match_registration_days WHERE player_id=:player_id and round_id = :round_id ";
		
		try {
			Query q = session.createSQLQuery(sqlString);
			q.setInteger("player_id", playerId);
			q.setInteger("round_id", roundId);
			q.executeUpdate();
			
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MatchRegistrationAdversary> GetRegistrationAdversaryByPlayer(
			int roundId, int playerId) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<MatchRegistrationAdversary> rs = null;
		String sqlString = "SELECT * FROM match_registration_adversary WHERE player_id=:player_id and round_id = :round_id ";
		
		try
		{
			Query q = session.createSQLQuery(sqlString).addEntity(MatchRegistrationAdversary.class);
			q.setInteger("player_id", playerId);
			q.setInteger("round_id", roundId);
			rs = q.list();
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return rs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MatchRegistrationDays> GetRegistrationDayByPlayer(
			int roundId, int playerId) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<MatchRegistrationDays> rs = null;
		String sqlString = "SELECT * FROM match_registration_days WHERE player_id=:player_id and round_id = :round_id ";
		
		try
		{
			Query q = session.createSQLQuery(sqlString).addEntity(MatchRegistrationDays.class);
			q.setInteger("player_id", playerId);
			q.setInteger("round_id", roundId);
			rs = q.list();
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return rs;
	}

	@Override
	public void ClearMatchInfos(int roundId) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		String sqlString = "delete from match_info WHERE round_id = :round_id ";
		
		try {
			Query q = session.createSQLQuery(sqlString);
			q.setInteger("round_id", roundId);
			q.executeUpdate();
			
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MatchInfo> GetMatchInfosByAdversary(int roundId, int adversaryId)
			throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<MatchInfo> rs = null;
		String sqlString = "SELECT * from match_info WHERE round_id = :round_id and adversary_id=:adversary_id ";
		
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(MatchInfo.class);;
			q.setInteger("round_id", roundId);
			q.setInteger("adversary_id", adversaryId);
			rs = q.list();
			
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return rs;
	}

	@Override
	public MatchInfo SaveMatchInfo(MatchInfo matchinfo) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		
		try
		{
			matchinfo = (MatchInfo)session.merge(matchinfo);
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return matchinfo;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MatchInfo> GetActiveMatchInfo()
			throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<MatchInfo> rs = null;
		String sqlString = "SELECT info.* FROM match_info info join round rd on info.round_id = rd.id and rd.status = :status ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(MatchInfo.class);;
			q.setInteger("status", Round.STATUS_ACTIVE);
			rs = q.list();
			
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return rs;
	}

	@Override
	public boolean IsRoundUsedInMatch(int roundId) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Long rs = null;
		String sqlString = "SELECT count(*) FROM MatchRegistrationDays WHERE round_id = :round_id ";
		boolean result = true;
		try {
			Query q = session.createQuery(sqlString);
			q.setInteger("round_id", roundId);
			rs = (Long)q.uniqueResult();
			if( rs == 0 ) {
				result = false;
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			result = true;
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MatchInfo> GetActiveMatchInfoByChallenger(int playerId) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<MatchInfo> rs = null;
		String sqlString = "SELECT info.* FROM match_info info join round rd on info.round_id = rd.id and rd.status = :status and info.challenger_id=:challenger ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(MatchInfo.class);;
			q.setInteger("status", Round.STATUS_ACTIVE);
			q.setInteger("challenger", playerId);
			rs = q.list();
			
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return rs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MatchInfo> GetActiveMatchInfoByadversary(int playerId)
			throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<MatchInfo> rs = null;
		String sqlString = "SELECT info.* FROM match_info info join round rd on info.round_id = rd.id and rd.status = :status and info.adversary_id=:adversary ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(MatchInfo.class);;
			q.setInteger("status", Round.STATUS_ACTIVE);
			q.setInteger("adversary", playerId);
			rs = q.list();
			
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return rs;
	}

	@Override
	public MatchInfo GetMatchInfoById(int id) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		MatchInfo rs = null;
		String sqlString = "SELECT * FROM match_info where id = :id ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(MatchInfo.class);;
			q.setInteger("id", id);
			rs = (MatchInfo)q.uniqueResult();
			
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return rs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int GetMaxMatchCountByPlayer(int roundId) throws Exception {
	Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		int rs = 0;
		
		//String sqlString = "SELECT COUNT(*) FROM Player WHERE 1=1 ";
		String sqlString = "SELECT count(*)  FROM MatchInfo i , Player p where (p.id = i.challengerId or p.id = i.adversaryId) and i.roundId = :round_id group by i.dayId, p.id order by col_0_0_ desc";

		try {
			Query q = session.createQuery(sqlString);
			q.setInteger("round_id", roundId);
			List<Object> list = q.list();
			Iterator<Object> it = list.iterator();
			Long maxCount = 0L;
			while(it.hasNext()){
				Long count = (Long) it.next();
			    if( count > maxCount ) {
			    	maxCount = count;
			    }
			}
			rs = maxCount.intValue();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		return rs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MatchRegistrationAdversary> GetActiveRoundRegistrationAdversary() throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<MatchRegistrationAdversary> rs = null;
		String sqlString = "SELECT m.* FROM match_registration_adversary m join round r on r.id = m.round_id and r.status=:status ";
		
		try
		{
			Query q = session.createSQLQuery(sqlString).addEntity(MatchRegistrationAdversary.class);
			q.setInteger("status", Round.STATUS_ACTIVE);
			rs = q.list();
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return rs;
	}

	@Override
	public Battle SaveBattleInfo(Battle battle) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		
		try
		{
			battle = (Battle)session.merge(battle);
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return battle;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Battle> GetBattleInfoByChallengerAndAdversary(int challengerId,
			int adversaryId, int roundId) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Battle> rs = null;
		String sqlString = "SELECT * from battle WHERE challenger_id = :challenger_id and adversary_id = :adversary_id and round_id = :round_id ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Battle.class);;
			q.setInteger("challenger_id", challengerId);
			q.setInteger("adversary_id", adversaryId);
			q.setInteger("round_id", roundId);
			rs = q.list();
			
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return rs;
	}

	@Override
	public void ClearBattleInfoByChallengerAndAdversary(int challengerId,
			int adversaryId, int roundId) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		String sqlString = "delete from battle WHERE challenger_id = :challenger_id and adversary_id = :adversary_id and round_id = :round_id ";
		
		try {
			Query q = session.createSQLQuery(sqlString);
			q.setInteger("challenger_id", challengerId);
			q.setInteger("adversary_id", adversaryId);
			q.setInteger("round_id", roundId);
			q.executeUpdate();
			
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return;		
	}

}

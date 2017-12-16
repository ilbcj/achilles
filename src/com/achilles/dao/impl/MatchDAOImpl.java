package com.achilles.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.achilles.dao.MatchDAO;
import com.achilles.model.HibernateUtil;
import com.achilles.model.MatchInfo;
import com.achilles.model.MatchPeriod;
import com.achilles.model.MatchRegistrationAdversary;
import com.achilles.model.MatchRegistrationDays;

public class MatchDAOImpl implements MatchDAO {

	@SuppressWarnings("unchecked")
	@Override
	public MatchPeriod GetActivePeriod() throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<MatchPeriod> rs = null;
		String sqlString = "SELECT * FROM match_period WHERE status=:status ";
		MatchPeriod result = null;
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(MatchPeriod.class);
			q.setInteger("status", MatchPeriod.STATUS_ACTIVE);
			rs = q.list();
			if(rs.size() == 0) {
				result = null;
			}
			else if(rs.size() > 1) {
				throw new Exception("当前活动赛程数大于1，请检查系统状态");
			}
			else {
				result = rs.get(0);
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			result = null;
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MatchRegistrationAdversary SaveMatchRegistrationAdversary(
			MatchRegistrationAdversary adversary) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<MatchRegistrationAdversary> rs = null;
		String sqlString = "SELECT * FROM match_registration_adversary WHERE player_id=:player_id and match_period_id = :match_period_id and adversary_id = :adversary_id ";
		
		try
		{
			Query q = session.createSQLQuery(sqlString).addEntity(MatchRegistrationAdversary.class);
			q.setInteger("player_id", adversary.getPlayerId());
			q.setInteger("match_period_id", adversary.getMatchPeriodId());
			q.setInteger("adversary_id", adversary.getAdversaryId());
			rs = q.list();
			if(rs.size() == 0) {
				adversary = (MatchRegistrationAdversary)session.merge(adversary);
			}
			else if(rs.size() == 1) {
				adversary = rs.get(0);
			}
			else {
				throw new Exception("选手[id:" + adversary.getPlayerId() + "在比赛[id:" + adversary.getMatchPeriodId() + "]中要挑战的对手[id:" + adversary.getAdversaryId() + "]信息重复，请管理员检查后台数据" );
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
	public MatchRegistrationDays SaveMatchRegistrationDay(
			MatchRegistrationDays day) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<MatchRegistrationDays> rs = null;
		String sqlString = "SELECT * FROM match_registration_days WHERE player_id=:player_id and match_period_id = :match_period_id and free_day = :free_day ";
		
		try
		{
			Query q = session.createSQLQuery(sqlString).addEntity(MatchRegistrationDays.class);
			q.setInteger("player_id", day.getPlayerId());
			q.setInteger("match_period_id", day.getMatchPeriodId());
			q.setInteger("free_day", day.getFreeDay());
			rs = q.list();
			if(rs.size() == 0) {
				day = (MatchRegistrationDays)session.merge(day);
			}
			else if(rs.size() == 1) {
				day = rs.get(0);
			}
			else {
				throw new Exception("选手[id:" + day.getPlayerId() + "在比赛[id:" + day.getMatchPeriodId() + "]中选择的空闲时间[id:" + day.getFreeDay() + "]信息重复，请管理员检查后台数据" );
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

	@SuppressWarnings("unchecked")
	@Override
	public MatchPeriod GetLastActivePeriod() throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<MatchPeriod> rs = null;
		String sqlString = "SELECT * FROM match_period WHERE status=:status ";
		MatchPeriod result = null;
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(MatchPeriod.class);
			q.setInteger("status", MatchPeriod.STATUS_LAST_ACTIVE);
			rs = q.list();
			if(rs.size() != 1) {
				throw new Exception("上一次活动赛程数大于1，请检查系统状态");
			}
			result = rs.get(0);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			result = null;
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		return result;
	}

	@Override
	public void ClearAdversaries(int matchPeriodId, int playerId)
			throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		String sqlString = "delete from match_registration_adversary WHERE player_id=:player_id and match_period_id = :match_period_id ";
		
		try {
			Query q = session.createSQLQuery(sqlString);
			q.setInteger("player_id", playerId);
			q.setInteger("match_period_id", matchPeriodId);
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
	public void ClearDays(int matchPeriodId, int playerId) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		String sqlString = "delete from match_registration_days WHERE player_id=:player_id and match_period_id = :match_period_id ";
		
		try {
			Query q = session.createSQLQuery(sqlString);
			q.setInteger("player_id", playerId);
			q.setInteger("match_period_id", matchPeriodId);
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
			int matchPeriodId, int playerId) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<MatchRegistrationAdversary> rs = null;
		String sqlString = "SELECT * FROM match_registration_adversary WHERE player_id=:player_id and match_period_id = :match_period_id ";
		
		try
		{
			Query q = session.createSQLQuery(sqlString).addEntity(MatchRegistrationAdversary.class);
			q.setInteger("player_id", playerId);
			q.setInteger("match_period_id", matchPeriodId);
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
			int matchPeriodId, int playerId) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<MatchRegistrationDays> rs = null;
		String sqlString = "SELECT * FROM match_registration_days WHERE player_id=:player_id and match_period_id = :match_period_id ";
		
		try
		{
			Query q = session.createSQLQuery(sqlString).addEntity(MatchRegistrationDays.class);
			q.setInteger("player_id", playerId);
			q.setInteger("match_period_id", matchPeriodId);
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
	public void ClearMatchInfos(int matchPeriodId) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		String sqlString = "delete from match_info WHERE match_period_id = :match_period_id ";
		
		try {
			Query q = session.createSQLQuery(sqlString);
			q.setInteger("match_period_id", matchPeriodId);
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
	public List<MatchInfo> GetMatchInfosByAdversary(int matchPeriodId, int adversaryId)
			throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<MatchInfo> rs = null;
		String sqlString = "SELECT * from match_info WHERE match_period_id = :match_period_id and adversary_id=:adversary_id ";
		
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(MatchInfo.class);;
			q.setInteger("match_period_id", matchPeriodId);
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
		String sqlString = "SELECT info.* FROM match_info info join match_period period on info.match_period_id = period.id and period.status = :status ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(MatchInfo.class);;
			q.setInteger("status", MatchPeriod.STATUS_ACTIVE);
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

}

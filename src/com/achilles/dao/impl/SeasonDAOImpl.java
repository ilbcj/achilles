package com.achilles.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.achilles.dao.SeasonDAO;
import com.achilles.model.HibernateUtil;
import com.achilles.model.Season;

public class SeasonDAOImpl implements SeasonDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Season> GetSeasons(int start, int length) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Season> rs = null;
		String sqlString = "SELECT * FROM Season WHERE status =:status ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Season.class);
			q.setInteger("status", Season.STATUS_USING);
			if( start > 0 || length >0 ) {
				q.setFirstResult(start);
				q.setMaxResults(length);
			}
			rs = q.list();
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

	@Override
	public long GetSeasonsCount() throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		int rs;
		String sqlString = "SELECT COUNT(*) FROM Season WHERE status = :status ";
		
		try {
			Query q = session.createQuery(sqlString);
			q.setInteger("status", Season.STATUS_USING);
			rs = ((Long)q.uniqueResult()).intValue();
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

	@Override
	public Season AddSeason(Season season) throws Exception {
		//打开线程安全的session对象
		Session session = HibernateUtil.currentSession();
		//打开事务
		Transaction tx = session.beginTransaction();
		try
		{
			season = (Season)session.merge(season);
			tx.commit();
		}
		catch(org.hibernate.exception.SQLGrammarException e)
		{
			tx.rollback();
			System.out.println(e.getSQLException().getMessage());
			throw e.getSQLException();
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
		return season;
	}

	@Override
	public void DelSeason(Season season) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Season rs = null;
		String sqlString = "SELECT * FROM Season WHERE id=:id ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Season.class);
			q.setInteger("id", season.getId());
			rs = (Season)q.uniqueResult();
			rs.setName(rs.getName() + ".archive." + new Date().getTime());
			rs.setStatus(Season.STATUS_DEL);
			session.merge(rs);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		return;
		
	}

	@Override
	public Season GetSeasonById(int seasonId) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Season rs = null;
		String sqlString = "SELECT * FROM Season WHERE status =:status and id=:id ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Season.class);
			q.setInteger("status", Season.STATUS_USING);
			q.setInteger("id", seasonId);
			rs = (Season)q.uniqueResult();
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

}

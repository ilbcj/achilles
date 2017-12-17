package com.achilles.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.achilles.dao.RoundDAO;
import com.achilles.model.HibernateUtil;
import com.achilles.model.Round;

public class RoundDAOImpl implements RoundDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Round> GetRounds(int start, int length) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Round> rs = null;
		String sqlString = "SELECT * FROM Round WHERE status!=:status ";
		                    
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Round.class);
			q.setInteger("status", Round.STATUS_DEL);
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
	public long GetRoundsCount() throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		int rs;
		String sqlString = "SELECT COUNT(*) FROM Round WHERE status!=:status ";
		
		try {
			Query q = session.createQuery(sqlString);
			q.setInteger("status", Round.STATUS_DEL);
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
	public Round AddRound(Round round) throws Exception {
		//打开线程安全的session对象
		Session session = HibernateUtil.currentSession();
		//打开事务
		Transaction tx = session.beginTransaction();
		try
		{
			round = (Round)session.merge(round);
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
		return round;
	}

	@Override
	public void DelRound(Round round) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Round rs = null;
		String sqlString = "SELECT * FROM Round WHERE id=:id ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Round.class);
			q.setInteger("id", round.getId());
			rs = (Round)q.uniqueResult();
			rs.setName(rs.getName() + ".archive." + new Date().getTime());
			rs.setStatus(Round.STATUS_DEL);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Round> GetRoundByStatus(int status) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Round> result = null;
		String sqlString = "SELECT * FROM Round WHERE status=:status ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Round.class);
			q.setInteger("status", status);
			result = q.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		
		return result;
	}

	@Override
	public Round GetRoundById(int roundId) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Round rs = null;
		String sqlString = "SELECT * FROM Round WHERE id=:id ";
		                    
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Round.class);
			q.setInteger("id", roundId);
			rs = (Round)q.uniqueResult();
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

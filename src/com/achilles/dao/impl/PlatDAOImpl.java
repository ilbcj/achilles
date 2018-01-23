package com.achilles.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.achilles.dao.PlatDAO;
import com.achilles.model.HibernateUtil;
import com.achilles.model.Plat;

public class PlatDAOImpl implements PlatDAO {

	@Override
	public Plat AddPlat(Plat plat) throws Exception {
		//打开线程安全的session对象
		Session session = HibernateUtil.currentSession();
		//打开事务
		Transaction tx = session.beginTransaction();
		try
		{
			plat = (Plat)session.merge(plat);
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
		return plat;
	}

	@Override
	public void DelPlat(Plat plat) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Plat rs = null;
		String sqlString = "SELECT * FROM Plat WHERE id=:id ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Plat.class);
			q.setInteger("id", plat.getId());
			rs = (Plat)q.uniqueResult();
			rs.setName(rs.getName() + ".archive." + new Date().getTime());
			rs.setStatus(Plat.STATUS_DEL);
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
	public List<Plat> GetPlatByStatus(int status) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Plat> result = null;
		String sqlString = "SELECT * FROM Plat WHERE status=:status ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Plat.class);
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
	public Plat GetPlatById(int platId) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Plat rs = null;
		String sqlString = "SELECT * FROM Plat WHERE id=:id ";
		                    
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Plat.class);
			q.setInteger("id", platId);
			rs = (Plat)q.uniqueResult();
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
	public Plat GetPlatByName(String name) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Plat> rs = null;
		Plat result = null;
		String sqlString = "SELECT * FROM Plat WHERE name like :name ";
		                    
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Plat.class);
			q.setString("name", "%" + name + "%");
			rs = q.list();
			if( rs == null || rs.size() == 0 ) {
				result = null;
			}
			else if( rs.size() == 1 ) {
				result = rs.get(0);
			}
			else {
				throw new Exception("more than one plat found.[name: " + name + " ]" );
			}
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

}

package com.achilles.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.achilles.dao.PlayerDAO;
import com.achilles.model.HibernateUtil;
import com.achilles.model.Player;

public class PlayerDAOImpl implements PlayerDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Player> GetPlayers(Player criteria, int start, int length)
			throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Player> rs = null;
		String sqlString = "SELECT * FROM Player WHERE 1=1 ";
		
		if( criteria != null ) {
			sqlString += " and status =:status ";
			if(criteria.getLoginId() != null && criteria.getLoginId().length() > 0) {
				sqlString += " and login_id like :login_id ";
			}
			if(criteria.getName() != null && criteria.getName().length() > 0) {
				sqlString += " and name like :name ";
			}
		}
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Player.class);
			if( criteria != null ) {
				q.setInteger("status", criteria.getStatus());
				if(criteria.getLoginId() != null && criteria.getLoginId().length() > 0) {
					q.setString( "login_id", "%" + criteria.getLoginId() + "%" );
				}
				if(criteria.getName() != null && criteria.getName().length() > 0) {
					q.setString( "name", "%" + criteria.getName() + "%" );
				}
			}
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
	public long QueryPlayersCount(Player criteria) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		int rs;
		String sqlString = "SELECT COUNT(*) FROM Player WHERE 1=1 ";
		
		if( criteria != null ) {
			sqlString += " and status = :status ";
			if(criteria.getLoginId() != null && criteria.getLoginId().length() > 0) {
				sqlString += " and login_id like :login_id ";
			}
			if(criteria.getName() != null && criteria.getName().length() > 0) {
				sqlString += " and name = :name ";
			}
		}
		try {
			Query q = session.createQuery(sqlString);
			if( criteria != null ) {
				q.setInteger("status", criteria.getStatus());
				if(criteria.getLoginId() != null && criteria.getLoginId().length() > 0) {
					q.setString( "login_id", "%" + criteria.getLoginId() + "%" );
				}
				if(criteria.getName() != null && criteria.getName().length() > 0) {
					q.setString( "name", "%" + criteria.getName() + "%" );
				}
			}
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
	public void AddPlayer(Player player) throws Exception {
		//打开线程安全的session对象
		Session session = HibernateUtil.currentSession();
		//打开事务
		Transaction tx = session.beginTransaction();
		try
		{
			session.save(player);
			tx.commit();
		}
		catch(ConstraintViolationException cne){
			tx.rollback();
			System.out.println(cne.getSQLException().getMessage());
			throw new Exception("存在重名选手");
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
		return;		
	}

	@Override
	public void DelPlayer(Player player) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Player rs = null;
		String sqlString = "SELECT * FROM Player WHERE id=:id ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Player.class);
			q.setInteger("id", player.getId());
			rs = (Player)q.uniqueResult();
			rs.setStatus(Player.STATUS_DEL);
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
	public Player GetPlayerById(int id) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Player rs = null;
		String sqlString = "SELECT * FROM Player WHERE id=:id ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Player.class);
			q.setInteger("id", id);
			rs = (Player)q.uniqueResult();
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

package com.achilles.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.achilles.dao.BattleInfoDAO;
import com.achilles.model.Battle;
import com.achilles.model.HibernateUtil;
import com.achilles.model.Round;

public class BattleInfoDAOImpl implements BattleInfoDAO {

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

	@Override
	public Battle GetBattleInfoById(int id) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Battle rs = null;
		String sqlString = "SELECT * from battle WHERE id = :id ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Battle.class);;
			q.setInteger("id", id);
			rs = (Battle)q.uniqueResult();
			
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
	public List<Battle> GetCurrentRoundBattleInfo() throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Battle> rs = null;
		String sqlString = "SELECT battle.* FROM battle join round on battle.round_id=round.id and round.status=:status ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Battle.class);;
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

}

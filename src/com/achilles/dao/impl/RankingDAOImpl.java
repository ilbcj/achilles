package com.achilles.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.achilles.dao.RankingDAO;
import com.achilles.model.HibernateUtil;
import com.achilles.model.Ranking;

public class RankingDAOImpl implements RankingDAO {

	@Override
	public Ranking GetRankingByPlayerid(int matchPeriodId, int playerId)
			throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Ranking rs = null;
		String sqlString = "SELECT * FROM Ranking WHERE match_period_id=:match_period_id and player_id = :player_id ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Ranking.class);
			q.setInteger("match_period_id", matchPeriodId);
			q.setInteger("player_id", playerId);
			rs = (Ranking)q.uniqueResult();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			rs = null;
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		return rs;
	}

	@Override
	public Ranking GetRankingByRanking(int matchPeriodId, int ranking)
			throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Ranking rs = null;
		String sqlString = "SELECT * FROM Ranking WHERE match_period_id=:match_period_id and ranking = :ranking ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Ranking.class);
			q.setInteger("match_period_id", matchPeriodId);
			q.setInteger("ranking", ranking);
			rs = (Ranking)q.uniqueResult();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			rs = null;
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		return rs;
	}
	
	

}

package com.achilles.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.achilles.dao.RankingDAO;
import com.achilles.model.HibernateUtil;
import com.achilles.model.MatchPeriod;
import com.achilles.model.Player;
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

	@Override
	public Ranking SaveRanking(Ranking ranking) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		try
		{
			ranking = (Ranking)session.merge(ranking);
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
		return ranking;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ranking> GetRankingOfActivePlayer() throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Ranking> rs = null;
		String sqlString = "SELECT r.* FROM ranking r join player p on r.player_id=p.id and p.status = :pstatus join match_period m on r.match_period_id=m.id and m.status in(:lastactive, :initperiod) order by ranking ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Ranking.class);
			q.setInteger("pstatus", Player.STATUS_USING);
			q.setInteger("lastactive", MatchPeriod.STATUS_LAST_ACTIVE);
			q.setInteger("initperiod", MatchPeriod.STATUS_INIT);
			rs = q.list();
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

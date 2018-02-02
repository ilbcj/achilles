package com.achilles.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.achilles.dao.ScoreDAO;
import com.achilles.model.HibernateUtil;
import com.achilles.model.Round;
import com.achilles.model.Score;

public class ScoreDAOImpl implements ScoreDAO {

	@Override
	public Score GetScoreByPlayerid(int roundId, int playerId)
			throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Score rs = null;
		String sqlString = "SELECT * FROM Score WHERE round_id=:round_id and player_id = :player_id ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Score.class);
			q.setInteger("round_id", roundId);
			q.setInteger("player_id", playerId);
			rs = (Score)q.uniqueResult();
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
	public Score SaveScore(Score score) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		try
		{
			score = (Score)session.merge(score);
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
		return score;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Score> GetScoreByRanking() throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Score> rs = null;
		String sqlString = "SELECT s.* FROM Score s join Round r on s.round_id = r.id and r.status = :status order by score desc";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Score.class);
			q.setInteger("status", Round.STATUS_ACTIVE);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Score> GetRoundScoreByRanking(int roundId) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Score> rs = null;
		String sqlString = "SELECT * FROM Score where round_id = :round_id order by score desc";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Score.class);
			q.setInteger("round_id", roundId);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Score> GetScoreByLastRoundRanking(int roundId) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Score> rs = null;
		String sqlString = "SELECT * FROM Score where round_id = :round_id order by last_score desc";

		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Score.class);
			q.setInteger("round_id", roundId);
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

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Score> GetScoreOfActivePlayer() throws Exception {
//		Session session = HibernateUtil.currentSession();
//		Transaction tx = session.beginTransaction();
//		List<Ranking> rs = null;
//		String sqlString = "SELECT r.* FROM ranking r join player p on r.player_id=p.id and p.status = :pstatus join round rd on r.round_id=rd.id and rd.status in(:lastactive, :initperiod) order by ranking ";
//		
//		try {
//			Query q = session.createSQLQuery(sqlString).addEntity(Ranking.class);
//			q.setInteger("pstatus", Player.STATUS_USING);
//			q.setInteger("lastactive", MatchPeriod.STATUS_LAST_ACTIVE);
//			q.setInteger("initperiod", MatchPeriod.STATUS_INIT);
//			rs = q.list();
//			tx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			tx.rollback();
//			System.out.println(e.getMessage());
//			rs = null;
//			throw e;
//		} finally {
//			HibernateUtil.closeSession();
//		}
//		return rs;
//	}

}

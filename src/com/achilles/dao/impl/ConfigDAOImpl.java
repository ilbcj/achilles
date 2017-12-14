package com.achilles.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.achilles.dao.ConfigDAO;
import com.achilles.model.Config;
import com.achilles.model.HibernateUtil;

public class ConfigDAOImpl implements ConfigDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Config> GetAllConfigItems() throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Config> rs = null;
		String sqlString = "SELECT * FROM Config ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Config.class);
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
	public Config SaveConfigItem(Config configItem) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Config rs;
		String sqlString = "SELECT * FROM Config WHERE name = :name ";
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Config.class);
			q.setString( "name", configItem.getName() );
			rs = (Config)q.uniqueResult();
			if(rs != null) {
				configItem.setId(rs.getId());
			}
			configItem = (Config)session.merge(configItem);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		return configItem;
	}

}

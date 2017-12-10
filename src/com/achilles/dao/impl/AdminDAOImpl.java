package com.achilles.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.achilles.dao.AdminDAO;
import com.achilles.model.Admin;
import com.achilles.model.HibernateUtil;

public class AdminDAOImpl implements AdminDAO{

	public Admin GetAdminById(String id) throws Exception {
	Session session = HibernateUtil.currentSession();
	Transaction tx = session.beginTransaction();
	Admin rs = null;
	String sqlString = "select * from admin where id = :id";
	
	try {
		Query q = session.createSQLQuery(sqlString).addEntity(Admin.class);
		q.setString("id", id);

		rs = (Admin)q.uniqueResult();
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
	
//	public void AdminAdd(Admin admin) throws Exception {
//		//打开线程安全的session对象
//		Session session = HibernateUtil.currentSession();
//		//打开事务
//		Transaction tx = session.beginTransaction();
//		try
//		{
//			session.save(admin);
//			tx.commit();
//		}
//		catch(ConstraintViolationException cne){
//			tx.rollback();
//			System.out.println(cne.getSQLException().getMessage());
//			
//			throw new Exception("存在重名管理员");
//		}
//		catch(org.hibernate.exception.SQLGrammarException e)
//		{
//			tx.rollback();
//			System.out.println(e.getSQLException().getMessage());
//			throw e.getSQLException();
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			tx.rollback();
//			System.out.println(e.getMessage());
//			throw e;
//		}
//		finally
//		{
//			HibernateUtil.closeSession();
//		}
//		return;
//	}
//	
//	public Admin AdminsAdd(Admin admin) throws Exception {
//		//打开线程安全的session对象
//		Session session = HibernateUtil.currentSession();
//		//打开事务
//		Transaction tx = session.beginTransaction();
//		try
//		{
//			admin = (Admin) session.merge(admin);
//			tx.commit();
//		}
//		catch(ConstraintViolationException cne){
//			tx.rollback();
//			System.out.println(cne.getSQLException().getMessage());
//			throw new Exception("存在重名群体。");
//		}
//		catch(org.hibernate.exception.SQLGrammarException e)
//		{
//			tx.rollback();
//			System.out.println(e.getSQLException().getMessage());
//			throw e.getSQLException();
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			tx.rollback();
//			System.out.println(e.getMessage());
//			throw e;
//		}
//		finally
//		{
//			HibernateUtil.closeSession();
//		}
//		return admin;
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<Admin> GetAllAdmins() throws Exception {
//		Session session = HibernateUtil.currentSession();
//		Transaction tx = session.beginTransaction();
//		List<Admin> rs = null;
//		String sqlString = "select * from admin ";
//
//		try {
//			Query q = session.createSQLQuery(sqlString).addEntity(Admin.class);
//			
//			rs = q.list();
//			tx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			tx.rollback();
//			System.out.println(e.getMessage());
//			throw e;
//		} finally {
//			HibernateUtil.closeSession();
//		}
//		return rs;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<Admin> GetAdminsBAK(Admin criteria, String business_type, int page, int rows) throws Exception {
//		Session session = HibernateUtil.currentSession();
//		Transaction tx = session.beginTransaction();
//		List<Admin> rs = null;
////		String sqlString = "select * from admin where 1=1 ";
////		String sqlString = " SELECT DISTINCT a.* FROM admin a, adminbusinesstype b WHERE 1=1  ";
////		 SELECT  *  FROM   admin a  LEFT   JOIN   adminbusinesstype b  ON   a.id=b.aid 
////		String sqlString = " SELECT DISTINCT a.* FROM admin a left join adminbusinesstype b on a.id=b.aid  WHERE 1=1 ";
////		String sqlString = " SELECT DISTINCT a.* FROM admin a left join adminbusinesstype b on a.id=b.aid  WHERE 1=1 ";
//		String sqlString = " SELECT * FROM admin WHERE 1=1 ";
////		SELECT * FROM admin WHERE 1=1 AND id IN (SELECT aid FROM adminbusinesstype WHERE )
//		if( criteria != null ) {
//			if(criteria.getId() != null && criteria.getId().length() > 0) {
//				sqlString += " and id = :id ";
//			}
//			if(criteria.getAdmin_type() != null && criteria.getAdmin_type().length() > 0) {
//				sqlString += " and admin_Type = :admin_Type ";
//			}
//			if(business_type != null && business_type.length() > 0){
////				sqlString += " and a.id = b.aid and businessAdmin_type = :businessAdmin_type";
//				sqlString += " and id IN (SELECT aid FROM adminbusinesstype WHERE businessAdmin_type = :businessAdmin_type) ";
//			}
//		}
//		try {
//			Query q = session.createSQLQuery(sqlString).addEntity(Admin.class);
//			if( criteria != null ) {
//				if(criteria.getId() != null && criteria.getId().length() > 0) {
//					q.setString( "id", criteria.getId() );
//				}
//				if(criteria.getAdmin_type() != null && criteria.getAdmin_type().length() > 0) {
//					q.setString( "admin_Type", criteria.getAdmin_type() );
//				}
//				if(business_type != null && business_type.length() > 0){
//					q.setString( "businessAdmin_type", business_type );
//				}
//			}
//			if( page > 0 && rows >0 ) {
//				q.setFirstResult((page-1) * rows);   
//				q.setMaxResults(rows);   
//			}
//			rs = q.list();
//			tx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			tx.rollback();
//			System.out.println(e.getMessage());
//			throw e;
//		} finally {
//			HibernateUtil.closeSession();
//		}
//		return rs;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<Admin> GetAdmins(Admin criteria, String business_type, int start, int length) throws Exception {
//		Session session = HibernateUtil.currentSession();
//		Transaction tx = session.beginTransaction();
//		List<Admin> rs = null;
////		String sqlString = "select * from admin where 1=1 ";
////		String sqlString = " SELECT DISTINCT a.* FROM admin a, adminbusinesstype b WHERE 1=1  ";
////		 SELECT  *  FROM   admin a  LEFT   JOIN   adminbusinesstype b  ON   a.id=b.aid 
////		String sqlString = " SELECT DISTINCT a.* FROM admin a left join adminbusinesstype b on a.id=b.aid  WHERE 1=1 ";
////		String sqlString = " SELECT DISTINCT a.* FROM admin a left join adminbusinesstype b on a.id=b.aid  WHERE 1=1 ";
//		String sqlString = " SELECT * FROM admin WHERE 1=1 ";
////		SELECT * FROM admin WHERE 1=1 AND id IN (SELECT aid FROM adminbusinesstype WHERE )
//		if( criteria != null ) {
//			if(criteria.getId() != null && criteria.getId().length() > 0) {
//				sqlString += " and id = :id ";
//			}
//			if(criteria.getAdmin_type() != null && criteria.getAdmin_type().length() > 0) {
//				sqlString += " and admin_Type = :admin_Type ";
//			}
//			if(business_type != null && business_type.length() > 0){
////				sqlString += " and a.id = b.aid and businessAdmin_type = :businessAdmin_type";
//				sqlString += " and id IN (SELECT aid FROM adminbusinesstype WHERE businessAdmin_type = :businessAdmin_type) ";
//			}
//		}
//		try {
//			Query q = session.createSQLQuery(sqlString).addEntity(Admin.class);
//			if( criteria != null ) {
//				if(criteria.getId() != null && criteria.getId().length() > 0) {
//					q.setString( "id", criteria.getId() );
//				}
//				if(criteria.getAdmin_type() != null && criteria.getAdmin_type().length() > 0) {
//					q.setString( "admin_Type", criteria.getAdmin_type() );
//				}
//				if(business_type != null && business_type.length() > 0){
//					q.setString( "businessAdmin_type", business_type );
//				}
//			}
//			if( start > 0 || length >0 ) {
//				q.setFirstResult(start);   
//				q.setMaxResults(length);   
//			}
//			rs = q.list();
//			tx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			tx.rollback();
//			System.out.println(e.getMessage());
//			throw e;
//		} finally {
//			HibernateUtil.closeSession();
//		}
//		return rs;
//	}
//	
//	public int GetAdminCount(Admin criteria, String business_type) throws Exception {
//		Session session = HibernateUtil.currentSession();
//		Transaction tx = session.beginTransaction();
//		int rs;
//		String sqlString = " SELECT COUNT(*) FROM admin WHERE 1=1 ";
//		
//		if( criteria != null ) {
//			if(criteria.getId() != null && criteria.getId().length() > 0) {
//				sqlString += " and id = :id ";
//			}
//			if(criteria.getAdmin_type() != null && criteria.getAdmin_type().length() > 0) {
//				sqlString += " and admin_Type = :admin_Type ";
//			}
//			if(business_type != null && business_type.length() > 0){
//				sqlString += " and id IN (SELECT aid FROM adminbusinesstype WHERE businessAdmin_type = :businessAdmin_type) ";
//			}
//		}
//		try {
//			Query q = session.createSQLQuery(sqlString);
//			if( criteria != null ) {
//				if(criteria.getId() != null && criteria.getId().length() > 0) {
//					q.setString( "id", criteria.getId() );
//				}
//				if(criteria.getAdmin_type() != null && criteria.getAdmin_type().length() > 0) {
//					q.setString( "admin_Type", criteria.getAdmin_type() );
//				}
//				if(business_type != null && business_type.length() > 0){
//					q.setString( "businessAdmin_type", business_type );
//				}
//			}
//			rs = ((BigInteger)q.uniqueResult()).intValue();
////			rs = 1;
//			tx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			tx.rollback();
//			System.out.println(e.getMessage());
//			throw e;
//		} finally {
//			HibernateUtil.closeSession();
//		}
//		return rs;
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	public List<AdminBusinessType> GetTypes(AdminBusinessType criteria, int page, int rows) throws Exception {
//		Session session = HibernateUtil.currentSession();
//		Transaction tx = session.beginTransaction();
//		List<AdminBusinessType> rs = null;
//		String sqlString = "select * from AdminBusinessType where 1=1 ";
//		if( criteria != null ) {
//		}
//		try {
//			Query q = session.createSQLQuery(sqlString).addEntity(AdminBusinessType.class);
//			if( criteria != null ) {
//			}
//			if( page > 0 && rows >0 ) {
//				q.setFirstResult((page-1) * rows);   
//				q.setMaxResults(rows);   
//			}
//			rs = q.list();
//			tx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			tx.rollback();
//			System.out.println(e.getMessage());
//			throw e;
//		} finally {
//			HibernateUtil.closeSession();
//		}
//		return rs;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<AdminBusinessType> GetAdminBusinessTypeByAid(String aid) throws Exception {
//		Session session = HibernateUtil.currentSession();
//		Transaction tx = session.beginTransaction();
//		List<AdminBusinessType> rs = null;
//		String sqlString = "select * from adminBusinessType where aid = :aid ";
//		try {
//			Query q = session.createSQLQuery(sqlString).addEntity(AdminBusinessType.class);
//			q.setString("aid", aid);
//			rs = q.list();
//			tx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			tx.rollback();
//			System.out.println(e.getMessage());
//			throw e;
//		} finally {
//			HibernateUtil.closeSession();
//		}
//		return rs;
//	}
//	
//	public BusinessType GetBussinessByCode(String code) throws Exception {
//		Session session = HibernateUtil.currentSession();
//		Transaction tx = session.beginTransaction();
//		BusinessType rs = null;
//		String sqlString = "select * from businessType where businessCode = :businessCode ";
//		try {
//			Query q = session.createSQLQuery(sqlString).addEntity(BusinessType.class);
//			q.setString("bussinessCode", code);
//			rs = (BusinessType) q.uniqueResult();
//			tx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			tx.rollback();
//			System.out.println(e.getMessage());
//			throw e;
//		} finally {
//			HibernateUtil.closeSession();
//		}
//		return rs;
//	}
//	
//	public AdminType GetAdminTypeByCode(String code) throws Exception {
//		Session session = HibernateUtil.currentSession();
//		Transaction tx = session.beginTransaction();
//		AdminType rs = null;
//		String sqlString = "select * from AdminType where code = :code ";
//		try {
//			Query q = session.createSQLQuery(sqlString).addEntity(AdminType.class);
//			q.setString("code", code);
//			rs = (AdminType) q.uniqueResult();
//			tx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			tx.rollback();
//			System.out.println(e.getMessage());
//			throw e;
//		} finally {
//			HibernateUtil.closeSession();
//		}
//		return rs;
//	}
//	
//	public void UpdateTypes(String aid, List<String> businessTypes) throws Exception {
//		Session session = HibernateUtil.currentSession();
//		Transaction tx = session.beginTransaction();
//		String sqlString = "delete from AdminBusinessType where aid = :aid ";
//		
//		try {
//			Query q = session.createSQLQuery(sqlString);
//			q.setString("aid", aid);
//			q.executeUpdate();
//			
//			AdminBusinessType adminBusinessType;
//			if(businessTypes != null) {
//				for(int i = 0; i<businessTypes.size(); i++) {
//					adminBusinessType = new AdminBusinessType();
//					adminBusinessType.setAid(aid);
//					adminBusinessType.setBusinessAdmin_type(businessTypes.get(i));
//					session.merge(adminBusinessType);
//				}
//			}
//			tx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			tx.rollback();
//			System.out.println(e.getMessage());
//			throw e;
//		} finally {
//			HibernateUtil.closeSession();
//		}
//		return;
//	}
//	
//	
//	public void AdminMod(Admin admin) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public void AdminDel(Admin admin) throws Exception {
//		Session session = HibernateUtil.currentSession();
//		Transaction tx = session.beginTransaction();
//		String sqlString = "delete from admin where id = :id ";
//		
//		try {
//			Query q = session.createSQLQuery(sqlString);
//			q.setString("id", admin.getId());
//			q.executeUpdate();
//			
//			sqlString = "delete from AdminBusinessType where aid = :aid ";
//			q = session.createSQLQuery(sqlString);
//			q.setString("aid", admin.getId());
//			q.executeUpdate();
//			
//			tx.commit();
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			tx.rollback();
//			System.out.println(e.getMessage());
//			throw e;
//		}
//		finally
//		{
//			HibernateUtil.closeSession();
//		}
//		return;
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<AdminType> GetAllAdminTypes() throws Exception {
//		Session session = HibernateUtil.currentSession();
//		Transaction tx = session.beginTransaction();
//		List<AdminType> rs = null;
//		String sqlString = "select * from adminType ";
//
//		try {
//			Query q = session.createSQLQuery(sqlString).addEntity(AdminType.class);
//			
//			rs = q.list();
//			tx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			tx.rollback();
//			System.out.println(e.getMessage());
//			throw e;
//		} finally {
//			HibernateUtil.closeSession();
//		}
//		return rs;
//	}
//	@SuppressWarnings("unchecked")
//	public List<BusinessType> GetAllBusinessTypes() throws Exception {
//		Session session = HibernateUtil.currentSession();
//		Transaction tx = session.beginTransaction();
//		List<BusinessType> rs = null;
//		String sqlString = "select * from businessType ";
//
//		try {
//			Query q = session.createSQLQuery(sqlString).addEntity(BusinessType.class);
//			
//			rs = q.list();
//			tx.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			tx.rollback();
//			System.out.println(e.getMessage());
//			throw e;
//		} finally {
//			HibernateUtil.closeSession();
//		}
//		return rs;
//	}

}

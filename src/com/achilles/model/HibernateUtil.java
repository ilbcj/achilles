package com.achilles.model;

import org.hibernate.*;
import org.hibernate.cfg.*;

/**
 * @author  yeeku.H.lee kongyeeku@163.com
 * @version  1.0
 * <br>Copyright (C), 2008-2010, yeeku.H.Lee
 * <br>This program is protected by copyright laws.
 * <br>Program Name:
 * <br>Date: 
 */
public class HibernateUtil
{
	public static final SessionFactory sessionFactory;

	static
	{
		try
		{
			//采用默认的hibernate.cfg.xml来启动一个Configuration的实�?
			Configuration configuration=new Configuration().configure();
			//由Configuration的实例来创建�?��SessionFactory实例
			//SchemaExport sch=new SchemaExport(configuration); 
		    //sch.create(true, true); 
			sessionFactory = configuration.buildSessionFactory();
		}
		catch (Throwable ex)
		{
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	//ThreadLocal并不是线程本地化的实�?而是线程�?��变量。也就是说每个使用该变量的线程都必须�?
	//该变量提供一个副�?每个线程改变该变量的值仅仅是改变该副本的�?而不会影响其他线程的该变�?
	//的�?.

	//ThreadLocal是隔离多个线程的数据共享，不存在多个线程之间共享资源,因此不再�?��对线程同�?   
	@SuppressWarnings("rawtypes")
	public static final ThreadLocal session = new ThreadLocal();

	@SuppressWarnings("unchecked")
	public static Session currentSession() throws HibernateException
	{
		Session s = (Session) session.get();
		//如果该线程还没有Session,则创建一个新的Session
		if (s == null)
		{
			s = sessionFactory.openSession();
			//将获得的Session变量存储在ThreadLocal变量session�?
			session.set(s);
		}
		return s;
	}

	@SuppressWarnings("unchecked")
	public static void closeSession() throws HibernateException 
	{
		Session s = (Session) session.get();
		if (s != null)
		s.close();
		session.set(null);
	}
}
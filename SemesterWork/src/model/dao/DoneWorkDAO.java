package model.dao;

import org.hibernate.Session;

import model.Done_work;
import model.resources.HibernateUtil;

public class DoneWorkDAO {
	public static void Add(Done_work d) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(d);
		session.getTransaction().commit();
	}

	public static void Update(Done_work d) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(d);
		session.getTransaction().commit();
	}

	public static void Delete(Done_work d) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(d);
		session.getTransaction().commit();
	}

}

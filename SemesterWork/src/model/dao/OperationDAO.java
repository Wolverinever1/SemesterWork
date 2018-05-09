package model.dao;

import org.hibernate.Session;

import model.Operation;
import model.resources.HibernateUtil;

public class OperationDAO {
	public static void Add(Operation o) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(o);
		session.getTransaction().commit();
	}

	public static void Update(Operation o) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(o);
		session.getTransaction().commit();
	}

	public static void Delete(Operation o) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(o);
		session.getTransaction().commit();
	}

}

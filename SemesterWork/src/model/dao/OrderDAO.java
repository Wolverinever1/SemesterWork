package model.dao;

import org.hibernate.Session;

import model.Order;
import model.resources.HibernateUtil;

public class OrderDAO {
	public static void Add(Order o) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(o);
		session.getTransaction().commit();
	}

	public static void Update(Order o) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(o);
		session.getTransaction().commit();
	}

	public static void Delete(Order o) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(o);
		session.getTransaction().commit();
	}
}

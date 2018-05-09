package model.dao;

import org.hibernate.Session;

import model.Order_product;
import model.resources.HibernateUtil;

public class OrderProductsDAO {
	public static void Add(Order_product o) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(o);
		session.getTransaction().commit();
	}

	public static void Update(Order_product o) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(o);
		session.getTransaction().commit();
	}

	public static void Delete(Order_product o) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(o);
		session.getTransaction().commit();
	}

}

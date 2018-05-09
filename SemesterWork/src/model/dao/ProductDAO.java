package model.dao;

import org.hibernate.Session;

import model.Product;
import model.resources.HibernateUtil;

public class ProductDAO {
	public static void Add(Product p) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(p);
		session.getTransaction().commit();
	}

	public static void Update(Product p) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(p);
		session.getTransaction().commit();
	}

	public static void Delete(Product p) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(p);
		session.getTransaction().commit();
	}
}

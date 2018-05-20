package model.dao;

import java.util.List;

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
	
	@SuppressWarnings("unchecked")
	public static List<Product> selectAll(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Product> products = session.createQuery("from Product").list();
		session.getTransaction().commit();
		return products;
	}
}

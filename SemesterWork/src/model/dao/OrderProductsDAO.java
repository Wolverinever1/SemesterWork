package model.dao;

import java.util.List;

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
	
	public static List<Object[]> SelectOrderProductInfo(int order_id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Object[]> result = session.createNativeQuery("select o_p.model, p.name, o_p.count from order_product o_p inner join product p where "
				+ "p.model = o_p.model and o_p.order_id ="+order_id).list();
		session.getTransaction().commit();
		return result;
	}

}

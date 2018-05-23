package model.dao;

import java.util.List;

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
	
	public static List<Object[]> SelectOrderInfo() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Object[]> result = session.createNativeQuery("select o.order_id, o.order_date, c.customer_name " + 
				"from orders o inner join customer c where c.customer_id = o.customer_id").list();
		session.getTransaction().commit();
		return result;
	}
	
	public static List<Object[]> SelectActiveOrders(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Object[]> result = session.createNativeQuery("select o.order_id, o.order_date, c.customer_name " + 
				"from orders o inner join customer c where c.customer_id = o.customer_id and o.is_done = 0").list();
		session.getTransaction().commit();
		return result;
	}
	
	public static void DeleteWhere(String id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Order o = new Order();
		o.setOrder_id(new Integer(id));
		session.delete(o);
		session.getTransaction().commit();
	}
}

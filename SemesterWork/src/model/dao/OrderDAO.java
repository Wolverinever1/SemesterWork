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
		List<Object[]> result = session.createNativeQuery("select o.order_id, o.order_date, c.customer_name\r\n" + 
				"from `orders` o inner join `customer` c where c.`customer_id`=o.`customer_id`").list();
//		System.out.println(result.get(0)[0]+ "<-->"+result.get(0)[1]+"<-->"+result.get(0)[2]);
		session.getTransaction().commit();
		return result;
	}
	
}

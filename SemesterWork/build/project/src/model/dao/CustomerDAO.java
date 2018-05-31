package model.dao;

import java.util.List;

import org.hibernate.Session;

import model.Customer;
import model.resources.HibernateUtil;

public class CustomerDAO {
	public static void Add(Customer c) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(c);
		session.getTransaction().commit();
	}

	public static void Update(Customer c) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(c);
		session.getTransaction().commit();
	}

	public static void Delete(Customer c) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(c);
		session.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Customer> selectAll(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Customer> customers = session.createQuery("from Customer").list();
		session.getTransaction().commit();
		return customers;
	}
}

package model.dao;

import org.hibernate.Session;

import model.Pr_op_sequence;
import model.resources.HibernateUtil;

public class ProductOperationsDAO {
	public static void Add(Pr_op_sequence p) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(p);
		session.getTransaction().commit();
	}

	public static void Update(Pr_op_sequence p) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(p);
		session.getTransaction().commit();
	}

	public static void Delete(Pr_op_sequence p) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(p);
		session.getTransaction().commit();
	}
}

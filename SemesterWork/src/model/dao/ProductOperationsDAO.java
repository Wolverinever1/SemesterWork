package model.dao;

import java.util.List;

import org.hibernate.Session;

import model.Pr_op_sequence;
import model.Product;
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
	
	public static List<Pr_op_sequence> selectAll(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Pr_op_sequence> workplaces = session.createQuery("from Pr_op_sequence").list();
		session.getTransaction().commit();
		return workplaces;
	}
	
	public static List<Pr_op_sequence> selectOperationSequense(Product p){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Pr_op_sequence> op = session.createQuery("from Pr_op_sequence where primaryKey.model.model = " + p.getModel()+ "order by number").list();
		session.getTransaction().commit();
		return op;
	}
}

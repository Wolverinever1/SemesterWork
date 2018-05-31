package model.dao;

import java.math.BigDecimal;
import java.util.List;

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
	
	public static List<Pr_op_sequence> selectAll(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Pr_op_sequence> workplaces = session.createQuery("from Pr_op_sequence").list();
		session.getTransaction().commit();
		return workplaces;
	}
	
	public static List<Pr_op_sequence> selectOperationSequense(String model){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Pr_op_sequence> op = session.createQuery("from Pr_op_sequence where primaryKey.model.model = " + model+ "order by number").list();
		session.getTransaction().commit();
		return op;
	}
	
	public static BigDecimal ModelTime(String model) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		BigDecimal result =  new BigDecimal(session.createNativeQuery("select SUM(`operation`.`time`) from `pr_op_sequence` inner join operation\r\n" + 
		" on `pr_op_sequence`.`operation_id` = `operation`.`operation_id` where `pr_op_sequence`.model ="+model+";").list().get(0).toString());
		session.getTransaction().commit();
		return result;
		
	}
}

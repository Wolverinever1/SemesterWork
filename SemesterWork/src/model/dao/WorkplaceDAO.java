package model.dao;

import java.util.List;

import org.hibernate.Session;

import model.Worker;
import model.Workplace;
import model.resources.HibernateUtil;

public class WorkplaceDAO {

	public static void Add(Workplace w) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(w);
		session.getTransaction().commit();
	}

	public static void Update(Workplace w) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		System.out.println("must update");
		session.update(w);
		session.getTransaction().commit();
	}

	public static void Delete(Workplace w) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(w);
		session.getTransaction().commit();
	}
	
	public static List<Workplace> selectAll(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Workplace> workplaces = session.createQuery("from Workplace").list();
		session.getTransaction().commit();
		return workplaces;
	}
	
	public static List<Workplace> selectEquipmentId(Worker w){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Workplace> workplaces = session.createQuery("from Workplace where primaryKey.worker_id ="+w.getWorker_id()).list();
		session.getTransaction().commit();
		return workplaces;
	}
}

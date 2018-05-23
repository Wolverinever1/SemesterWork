package model.dao;

import java.util.List;

import org.hibernate.Session;

import model.Worker;
import model.Workplace;
import model.resources.HibernateUtil;

public class WorkerDAO {

	public static void Add(Worker w) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(w);
		session.getTransaction().commit();
	}

	public static void Update(Worker w) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(w);
		session.getTransaction().commit();
	}

	public static void Delete(Worker w) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(w);
		session.getTransaction().commit();
	}
	
	public static List<Workplace> getWorkplaces(Worker w) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Workplace> wp = session.createQuery("from Workplace where primaryKey.worker_id.worker_id="+w.getWorker_id()).list();//from Workplace wp left join wp.worker worker where worker.worker_id 
		session.getTransaction().commit();
		return wp;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Worker> selectAll(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Worker> workers = session.createQuery("from Worker").list();
		session.getTransaction().commit();
		return workers;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Worker> selectAllOrderByGrade(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Worker> workers = session.createQuery("from Worker order by grade").list();
		session.getTransaction().commit();
		return workers;
	}
	
	public static Worker getWorker(int id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Worker w = (Worker) session.createQuery("from Worker where worker_id="+id).list().get(0);
		session.getTransaction().commit();
		return w;
	}
	
}

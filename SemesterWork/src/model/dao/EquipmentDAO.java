package model.dao;

import java.util.List;

import org.hibernate.Session;

import model.Equipment;
import model.Worker;
import model.Workplace;
import model.resources.HibernateUtil;

public class EquipmentDAO {
	public static void Add(Equipment e) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(e);
		session.getTransaction().commit();
	}

	public static void Update(Equipment e) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(e);
		session.getTransaction().commit();
	}

	public static void Delete(Equipment e) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(e);
		session.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Integer> getEquipmentId() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Integer> list = session.createQuery("select e.id from Equipment e").list();
		session.getTransaction().commit();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Workplace> getWorkplaces(Worker w) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
//		@SuppressWarnings("unchecked")
		List<Workplace> wp = session.createQuery("select workplaces from Equipment"/* wp where wp.worker_id.worker_id= "+w.getWorker_id()*/).list();//from Workplace wp left join wp.worker worker where worker.worker_id 
		/*"select p " +
	    "from Publication p " +
	    "join fetch p.book b " +
	    "where " +
	    "   b.isbn = :isbn and " +
	    "   p.currency = :currency",*/
		session.getTransaction().commit();
		return wp;
	}
	
	public static Equipment getEquipment(int id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Equipment e = (Equipment) session.createQuery("from Equipment where id="+id).list().get(0);
		session.getTransaction().commit();
		return e;
	}
	
	public static List<Equipment> selectAll(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Equipment> equipments = session.createQuery("from Equipment").list();
		session.getTransaction().commit();
		return equipments;
	}
}

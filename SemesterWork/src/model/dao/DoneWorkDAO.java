package model.dao;

import java.util.List;

import org.hibernate.Session;

import model.Done_work;
import model.resources.HibernateUtil;

public class DoneWorkDAO {
	public static void Add(Done_work d) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(d);
		session.getTransaction().commit();
	}

	public static void Update(Done_work d) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(d);
		session.getTransaction().commit();
	}

	public static void Delete(Done_work d) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(d);
		session.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public static List<Done_work> selectAll(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Done_work> workers = session.createQuery("from Done_work").list();
		session.getTransaction().commit();
		return workers;
	}
	
	public static List<Object[]> FindActive() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Object[]> done = session.createNativeQuery("SELECT d_w.`order_id`, d_w.`model`, d_w.`operation_id` FROM `done_work` d_w GROUP BY d_w.`operation_id`,`d_w`.`order_id`, `d_w`.`model`\r\n" + 
				"	HAVING SUM(`d_w`.`count_done`)<(SELECT o_p.`count` FROM `order_product` o_p \r\n" + 
				"	WHERE o_p.`order_id`=d_w.`order_id` AND `o_p`.`model`=d_w.`model`);").list();
		session.getTransaction().commit();
		return done;
	}
	
	
	/*
	SELECT * FROM `done_work` d_w GROUP BY d_w.`operation_id`,`d_w`.`order_id`, `d_w`.`model`
	HAVING SUM(`d_w`.`count_done`)<(SELECT o_p.`count` FROM `order_product` o_p 
	WHERE o_p.`order_id`=d_w.`order_id` AND `o_p`.`model`=d_w.`model`);*/
}

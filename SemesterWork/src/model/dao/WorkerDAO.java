package model.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;

import controller.DoneWorkController;
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
		List<Workplace> wp = session.createQuery("from Workplace where worker_id.worker_id=" + w.getWorker_id()).list();
		session.getTransaction().commit();
		return wp;
	}

	@SuppressWarnings("unchecked")
	public static List<Worker> selectAll() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Worker> workers = session.createQuery("from Worker").list();
		session.getTransaction().commit();
		return workers;
	}

	@SuppressWarnings("unchecked")
	public static List<Worker> selectAllOrderByGrade() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Worker> workers = session.createQuery("from Worker order by grade").list();
		session.getTransaction().commit();
		return workers;
	}

	public static Worker getWorker(int id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Worker w = (Worker) session.createQuery("from Worker where worker_id=" + id).list().get(0);
		session.getTransaction().commit();
		return w;
	}

	public static BigDecimal getBusyTime(List<DoneWorkController.OperationWrapper> operations, int worker_id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String select = "select sum(o.`time`) from `operation` o where o.`operation_id` IN\r\n"
				+ "(select d_w.`operation_id` from `done_work` d_w where (";
		select += "(`d_w`.`operation_id` =" + operations.get(0).getOperation_id() + " AND \r\n" + "`d_w`.`model` = "
				+ operations.get(0).getModel() + " AND `d_w`.`order_id` = " + operations.get(0).getOrder_id() + ")";
		for (int i = 1; i < operations.size(); i++) {
			select += "OR (`d_w`.`operation_id` =" + operations.get(i).getOperation_id() + " AND \r\n"
					+ "`d_w`.`model` = " + operations.get(i).getModel() + " AND `d_w`.`order_id` = "
					+ operations.get(i).getOrder_id() + ")";
		}
		select += ") and d_w.`worker_id` = " + worker_id + ");";
		Object o = session.createNativeQuery(select).list().get(0);
		BigDecimal result = new BigDecimal(o == null ? "0.0" : o.toString());
		session.getTransaction().commit();
		return result;
	}

	public static int getWorkerCount() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Integer w = new Integer( session.createQuery("select count(*) from Worker").list().get(0).toString());
		session.getTransaction().commit();
		return w;
	}
	
}

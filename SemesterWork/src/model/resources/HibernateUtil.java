package model.resources;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				sessionFactory = new Configuration().configure("/model/resources/hibernate.cfg.xml")
						.buildSessionFactory();
			} catch (Throwable ex) {
				System.out.println("Session factory creation failed");
				ex.printStackTrace();
			}
		}
		return sessionFactory;
	}

}

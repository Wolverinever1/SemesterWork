package model.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.hibernate.Session;

import model.Account;
import model.resources.HibernateUtil;

public class AccountDAO {
	public static void Add(Account a) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(a);
		session.getTransaction().commit();
	}

	public static void Update(Account a) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(a);
		session.getTransaction().commit();
	}

	public static void Delete(Account a) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(a);
		session.getTransaction().commit();
	}

	public static Object[] isRegister(String login, String password) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Object[] w = (Object[]) session
					.createNativeQuery("select worker_id, is_dressmaker from accounts where login =\'" + login
							+ "\' and password = \'" + password + "\';")
					.list().get(0);
			session.getTransaction().commit();
			return w;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public static boolean checkPassword(String oldPassword, String login) {
		MessageDigest md;
		boolean result = false;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(oldPassword.getBytes());
			byte[] digest = md.digest();
			String passwordEncrypted = new String(digest);
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			result = (boolean) session
					.createQuery("select password = \'" + passwordEncrypted + "\' from Account where login =" + login)
					.list().get(0);
			session.getTransaction().commit();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}

	public static void changePassword(String newPassword, String login) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(newPassword.getBytes());
			byte[] digest = md.digest();
			String passwordEncrypted = new String(digest);
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.createNativeQuery(
					"update accounts set password = \'" + passwordEncrypted + "\' where login =" + login + ";")
					.executeUpdate();
			session.getTransaction().commit();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

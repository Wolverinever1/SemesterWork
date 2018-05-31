package model;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import model.dao.AccountDAO;

public class Email implements Runnable {
	
	// Class fields
	private static String senderLogin = "tehnologtehnolog421@gmail.com";
	private static String senderPassword = "rRe2wvr3@_df2Fv";
	
	private Properties mailServerProperties;
	private Session getMailSession;
	private MimeMessage generateMailMessage;
	
	private String subject;
	private String emailBody;
	private String recipient;
	
	// Constructors
	public Email(Worker worker) {
		String password = StringGenerator.generatePassword();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte[] digest = md.digest();
			String passwordEncrypted = new String(digest, Charset.forName("UTF-8"));
			Account account = new Account(passwordEncrypted, 1, worker);
			AccountDAO.Add(account);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.subject = "Реєстрація";
		this.emailBody = "Ваш тимчасовий пароль ";
		this.emailBody+=password;
		this.emailBody+=". Рекомендуємо відразу змінити пароль. З повагою, Ваш технолог.";
		this.recipient = worker.getEmail();
//		this(worker, "Реєстрація", "<br>Ваш тимчасовий пароль <b>"+password+"</b><br>Рекомендуємо відразу змінити пароль.<br> З повагою, <br>Ваш технолог.");
	}
	
	public Email(Worker worker, String subject, String message) {
		this.subject = subject;
		this.emailBody = message;
		this.recipient = worker.getEmail();
	}
	// functions
	@Override
	public void run() {
		try {
			send();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public void send() throws MessagingException {
		// Initialize 
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		// Filling fields
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		generateMailMessage.setSubject(subject);
		generateMailMessage.setText(emailBody, "UTF-8");
		// Sending mail
		Transport transport = getMailSession.getTransport("smtp");
		transport.connect("smtp.gmail.com", senderLogin, senderPassword);
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}

	public static String getSenderLogin() {
		return senderLogin;
	}

	public static void setSenderLogin(String senderLogin) {
		Email.senderLogin = senderLogin;
	}

	public static String getSenderPassword() {
		return senderPassword;
	}

	public static void setSenderPassword(String senderPassword) {
		Email.senderPassword = senderPassword;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
}

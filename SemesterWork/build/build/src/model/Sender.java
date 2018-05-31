package model;

public class Sender {
		
	public static void sendMesage(Worker worker) {
		Email email = new Email(worker);
		Thread SendingThread = new Thread(email);
		SendingThread.start();
	}
	
	public static void passwordShower() {
		
		for(int i = 0; i < 20; i++)
			System.out.println("Password: "+StringGenerator.generatePassword());
		
	}

}

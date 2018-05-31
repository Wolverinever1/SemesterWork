package model;

public abstract class StringGenerator {
	
	public static String generatePassword() {
		return generatePassword(8);
	}
	
	public static String generatePassword(int size) {
		return generatePassword("QAZWSXEDCRFVTGBYHNUJMIKOLPqazwsxedcrfvtgbyhnujmkiolp1234567890", size);
	}
	
	public static String generatePassword(String chars, int size) {
		StringBuilder builder = new StringBuilder();
		
		while(size>0) {
			builder.append(chars.charAt((int) Math.round(Math.random()*(chars.length()-1))));
			size--;
		}
		
		return builder.toString();		
	}
	
	

}

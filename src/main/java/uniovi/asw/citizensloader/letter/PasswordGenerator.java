package uniovi.asw.citizensloader.letter;

import java.util.Random;

/**
 * Simple class used to generate random passwords.
 * @author Diego
 */
public class PasswordGenerator {
	
	/**
	 * Generates a random password with length 8 containing upper
	 * and lower case letters and numbers in a random order.
	 * @return The resulting password as a String
	 */
	public static String generatePassword() {
		Random random = new Random();
		String result = "";
		char[] characters ={'A','B','C','D','E','F','G','H','I','J','K','L',
				'M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
				'a','b','c','d','e','f','g','h','i','j','k','l','m','n',
				'o','p','q','r','s','t','u','v','w','x','y','z',
				'0','1','2','3','4','5','6','7','8','9',};
		
		for (int i =0; i < 8; i++){
			result +=  characters[random.nextInt(characters.length)];
		}
		return result;
	}

}

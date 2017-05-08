package citloader.reader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uniovi.asw.citizensloader.letter.PasswordGenerator;
import uniovi.asw.persistence.model.User;

public class UserBuilder {

	// Creates a user quickly.
	// Holly shit, that beautiful amount of parameters.
	protected static User newUser(String firstName, String lastName,
			String email, String dateOfBirthStr, String address,
			String nationality, String identification) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dateOfBirth;
		try {
			dateOfBirth = format.parse(dateOfBirthStr);
		} catch (ParseException e) {
			throw new RuntimeException(
					"Bad date string [" + dateOfBirthStr + "]. "
							+ "Contact Pablo García Ledo UO245120 or fix it yourself. "
							+ "I'm sorry :(");
		}
		//String name, String surname, String password, String email, String nationality, String DNI, String address, Date birthDate
		User result = new User(firstName, lastName, PasswordGenerator.generatePassword(), 
				email, nationality, identification, address, dateOfBirth);

		return result;
	}
}

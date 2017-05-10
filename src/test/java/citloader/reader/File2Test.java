package citloader.reader;

import org.junit.Test;

import uniovi.asw.citizensloader.reader.Reader;
import uniovi.asw.citizensloader.reader.ReaderFactory;
import uniovi.asw.persistence.model.User;

public class File2Test {

	// Where the file is located
	
	private static final String PATH = "src/main/resources/test2.xlsx";
	
	// List of users tha will be compared with the ones read from the Excel file
	private static final User[] USERS = {
			UserBuilder.newUser("Pepe", "Garcia", "pepe@hotmail.com",
					"1980-05-17", "A street", "Spanish", "58435551H"),
			UserBuilder.newUser("Julian", "Fernandez",
					"julian@hotmail.com", "1976-04-06", "B street",
					"Spanish", "58462548L"),
			UserBuilder.newUser("Teresa", "Alvarez",
					"teresa@hotmail.com", "1965-07-08", "C street",
					"Spanish", "23185484D"),
			UserBuilder.newUser("Sergio", "Gonzalez",
					"sergio@hotmail.com", "1997-03-17", "D street",
					"Spanish", "35848874F"),
			UserBuilder.newUser("Sergio", "Gonzalez",
					"gonzalez@hotmail.com", "1997-03-17", "D street",
					"Spanish", "54852488R"),
			UserBuilder.newUser("Sergio", "Gonzalez",
					"sergio@hotmail.com", "1997-03-17", "D street",
					"Spanish", "35848874F"),
			UserBuilder.newUser("Juan", "lopez", "juan@hotmail.com",
					"1996-06-26", "G street", "Spanish", "58448544L"),
			UserBuilder.newUser("Pedro", "Garcia",
					"pedro@hotmail.com", "1958-08-15", "H street",
					"Spanish", "56842965K"),
			UserBuilder.newUser("Jorge", "Sanchez",
					"jorge@hotmail.com", "1936-11-16", "I street",
					"Spanish", "79852655O"),
			UserBuilder.newUser("Guillermo", "Martinez",
					"guillermo@hotmail.com", "1986-10-17", "J street",
					"Spanish", "79862155S"),
			UserBuilder.newUser("Javier", "Rodriguez",
					"javier@hotmail.com", "1975-09-18", "K street",
					"Spanish", "59526965A"),
			UserBuilder.newUser("Miguel", "Diaz",
					"miguel@hotmail.com", "1914-08-19", "L street",
					"Spanish", "52258848D"),
			UserBuilder.newUser("Lucia", "Villa", "lucia@hotmail.com",
					"1966-02-20", "M street", "Spanish", "79624535C"),
			UserBuilder.newUser("Laura", "Perez", "laura@hotmail.com",
					"1985-06-21", "N street", "Spanish", "75666543W"),
			UserBuilder.newUser("Ines", "Garcia", "ines@hotmail.com",
					"1958-08-22", "O street", "Spanish", "32519528T"),
			UserBuilder.newUser("Oscar", "Alvarez",
					"oscar@hotmail.com", "1977-07-23", "P street",
					"Spanish", "75582695P"),
			UserBuilder.newUser("Isabel", "Fernandez",
					"isabel@hotmail.com", "1954-03-28", "Q street",
					"Spanish", "76965965V"),
			UserBuilder.newUser("Elena", "Lopez", "elena@hotmail.com",
					"1958-08-25", "R street", "Spanish", "58262648M"),
			UserBuilder.newUser("Pablo", "Perez", "pablo@hotmail.com",
					"1988-12-12", "S street", "Spanish", "35848874F"),
			UserBuilder.newUser("Paula", "Sanchez",
					"paula@hotmail.com", "1993-09-30", "T street",
					"Spanish", "35848874F"),
			UserBuilder.newUser("Diego", "Diaz", "diego@hotmail.com",
					"1989-02-27", "U street", "Spanish", "35848874F"),
			UserBuilder.newUser("Daniel", "Martinez",
					"daniel@hotmail.com", "1945-09-04", "W street",
					"Spanish", "35848874F"),
			UserBuilder.newUser("Roberto", "Gonzalez",
					"roberto@hotmail.com", "1969-12-25", "X street",
					"Spanish", "35848874F"),
			UserBuilder.newUser("Patricia", "Villa",
					"patricia@hotmail.com", "1963-08-24", "Y street",
					"Spanish", "35848874F"),
			UserBuilder.newUser("Ana", "Garcia", "ana@hotmail.com",
					"1978-02-14", "Z street", "Spanish",
					"35848874F"), };

	
	// Compares Users from the list with the ones that will be read form the Excel file
	
	@Test
	public void testData() {
		Reader reader = ReaderFactory.getReader();
		new ReaderOutputTester().testData(reader, USERS, PATH);
	}
}

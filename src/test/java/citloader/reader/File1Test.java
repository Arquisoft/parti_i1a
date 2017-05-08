package citloader.reader;

import org.junit.Test;

import uniovi.asw.citizensloader.reader.Reader;
import uniovi.asw.citizensloader.reader.ReaderFactory;
import uniovi.asw.persistence.model.User;


/**
 * This test
 * 
 * @author Pabloski
 *
 */
public class File1Test {

	private static final String PATH = "src/main/resources/test.xlsx";
	private static final User[] USERS = { UserBuilder.newUser("Juan",
			"Torres Pardo", "juan@example.com", "1985-10-10",
			"C/ Federico García Lorca 2", "Español", "90500084Y"),
			UserBuilder.newUser("Luis", "López Fernando",
					"luis@example.com", "1970-03-02",
					"C/ Real Oviedo 2", "Español", "19160962F"),
			UserBuilder.newUser("Ana", "Torres Pardo",
					"ana@example.com", "1960-01-01",
					"Av. De la Constitución 8", "Español",
					"09940449X") };

	@Test
	public void testData() {
		Reader reader = ReaderFactory.getReader();
		new ReaderOutputTester().testData(reader, USERS, PATH);
	}
}

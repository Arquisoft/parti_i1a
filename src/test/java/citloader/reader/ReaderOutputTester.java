package citloader.reader;

import static org.junit.Assert.assertEquals;

import java.util.List;

import uniovi.asw.citizensloader.reader.Reader;
import uniovi.asw.persistence.model.User;

/**
 * This class eases the check of whether a reader is returning the 
 * User objects with the information it's supposed to.
 * 
 * @author Pabloski
 *
 */
public class ReaderOutputTester {

	public void testData(Reader reader, User[] expected, String path) {
		List<User> results = reader.readFile(path);

		assertEquals(expected.length, results.size());

		for (int i = 0; i < expected.length; i++) {
			User actual = results.get(i);
			User expectedUser = expected[i];
			_assertEquals(expectedUser, actual);
		}
	}

	private static void _assertEquals(User expected, User actual) {
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getSurname(), actual.getSurname());
		assertEquals(expected.getEmail(), actual.getEmail());
		assertEquals(expected.getBirth(),
				actual.getBirth());
		assertEquals(expected.getAddress(), actual.getAddress());
		assertEquals(expected.getNationality(),
				actual.getNationality());
		assertEquals(expected.getDni(),
				actual.getDni());
	}
}

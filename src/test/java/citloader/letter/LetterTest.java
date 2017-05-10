package citloader.letter;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import uniovi.asw.citizensloader.letter.Letter;
import uniovi.asw.citizensloader.letter.PasswordGenerator;
import uniovi.asw.citizensloader.letter.TxtLetter;
import uniovi.asw.persistence.model.User;

public class LetterTest {

	private Letter letter;
	private User user;
	
	@Before
	public void setUp() throws Exception {
		letter = new TxtLetter();
		user = new User("name", "lastName", PasswordGenerator.generatePassword(), "email", "nationality", "1", "address", new Date());
	}

	@Test
	public void testWrite() {
		String path = letter.write(user);
		
		try {
			BufferedReader bf = new BufferedReader(new FileReader(path));
			String line = bf.readLine();
			assertTrue(line.equals("Su registro se ha procesado con éxito. "
					+ "Procederemos a enviarle un correo. Su correo es: " + user.getEmail()
					+ " y su contraseña: " + user.getPassword())
				);
			bf.close();
			
		} catch (FileNotFoundException e) {
			assertTrue("No se encontró el archivo", false);
		} catch (IOException e) {
			assertTrue("Ha habido un problema leyendo del archivo", false);
		}
	}

}

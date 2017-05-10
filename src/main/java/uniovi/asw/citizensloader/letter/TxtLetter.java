package uniovi.asw.citizensloader.letter;

import java.io.*;

import uniovi.asw.persistence.model.User;

/**
 * An implementation of the Letter interface. It writes the content in a simple
 * txt file.
 * 
 * @author Diego
 */
public class TxtLetter implements Letter {

	@Override
	public String write(User u) {
		BufferedWriter file;

		String filename = "generatedFiles\\" + u.getDni() + "_"
				+ System.currentTimeMillis() + ".txt";
		try {
			file = new BufferedWriter(new FileWriter(filename));
			file.write("Su registro se ha procesado con éxito."
					+ " Procederemos a enviarle un correo. Su correo es: "
					+ u.getEmail() + " y su contraseña: "
					+ u.getPassword());
			file.close();
		} catch (IOException e) {
			System.out.println(
					"Could not write registration letter for user: "
							+ "name=" + u.getName() + ", surname="
							+ u.getSurname() + ", dni=" + u.getDni()
							+ "\n Cause: " + e.getMessage());
		}

		return filename;
	}

}

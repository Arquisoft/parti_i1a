package uniovi.asw.citizensloader.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import uniovi.asw.persistence.model.User;


/**
 * Interface for Reader implementations 
 * @author Pabloski
 *
 */
public interface Reader {

	public List<User> readStream(InputStream stream) throws IOException;
	public List<User> readFile(String path);
}

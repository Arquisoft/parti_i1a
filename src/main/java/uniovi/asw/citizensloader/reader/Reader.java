package uniovi.asw.citizensloader.reader;

import java.util.List;

import uniovi.asw.persistence.model.User;


/**
 * Interface for Reader implementations 
 * @author Pabloski
 *
 */
public interface Reader {

	public List<User> readFile(String path);
}

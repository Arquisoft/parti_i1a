package uniovi.asw.citizensloader.letter;

import uniovi.asw.persistence.model.User;

/**
 * Interface that represents the generating letter.
 * @author Diego
 */
public interface Letter {
	
	/**
	 * Writes in a file the welcoming letter to the specified user.
	 * @param u Logged User
	 */
	public void write(User u);
}

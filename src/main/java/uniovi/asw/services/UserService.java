package uniovi.asw.services;

import java.util.List;

import uniovi.asw.persistence.model.User;

public interface UserService {
	
	void save(User user);
	boolean checkExists(Long id);
	List<User> findAll();
}

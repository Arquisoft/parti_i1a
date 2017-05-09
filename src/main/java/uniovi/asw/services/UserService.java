package uniovi.asw.services;

import java.util.List;

import uniovi.asw.persistence.model.User;

public interface UserService {
	
	User save(User user);
	boolean checkExists(Long id);
	List<User> findAll();
	User findByEmail(String email);

    void delete(User user);
    User findById(Long id);

    void clearTable();
    User findUserByEmailAndPassword(String email, String password);
    User findUserByEmail(String email);


}

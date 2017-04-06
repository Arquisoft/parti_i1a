package asw.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asw.persistence.model.User;
import asw.persistence.repositories.UserRepository;
import asw.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository repository;
	
	@Autowired
	public UserServiceImpl(UserRepository repository) {
		setRepository(repository);
	}

	@Override
	public void save(User user) {
		getRepository().save(user);
	}

	@Override
	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		if (getRepository().findAll() != null) {
			Iterator<User> it = getRepository().findAll().iterator();
			while (it.hasNext())
				users.add(it.next());
		}
		return users;
	}

	@Override
	public boolean checkExists(Long id) {
		return getRepository().findOne(id) != null;
	}

	private void setRepository(UserRepository repository){
		this.repository = repository;
	}
	
	private UserRepository getRepository(){
		return this.repository;
	}

}

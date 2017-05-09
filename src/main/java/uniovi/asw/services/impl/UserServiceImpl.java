package uniovi.asw.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniovi.asw.persistence.model.User;
import uniovi.asw.persistence.repositories.UserRepository;
import uniovi.asw.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository repository;
	
	@Autowired
	public UserServiceImpl(UserRepository repository) {
		setRepository(repository);
	}

	@Override
	public User save(User user) {
		return getRepository().save(user);
	}

    @Override
    public User findUserByEmail(String email) {
        return getRepository().findByEmail(email);
    }

    @Override
    public void clearTable() {
        getRepository().deleteAll();
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) {
        return getRepository().findByEmailAndPassword(email, password);
    }

    @Override
    public void delete(User user) {
        getRepository().delete(user);
    }

    @Override
    public User findById(Long id) {
        return getRepository().findOne(id);
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
	public User findByEmail(String email) {
		return getRepository().findByEmail(email);
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

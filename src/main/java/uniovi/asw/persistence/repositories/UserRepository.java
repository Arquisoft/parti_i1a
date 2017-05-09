package uniovi.asw.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uniovi.asw.persistence.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByEmail(String email);
    User findByLoginAndPassword(String login, String password);
    User findByLogin(String login);

}
package asw.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asw.persistence.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    public User findByEmail(String email);

}
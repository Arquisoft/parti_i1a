package uniovi.asw.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uniovi.asw.persistence.model.User;
import uniovi.asw.persistence.model.Votable;
import uniovi.asw.persistence.model.Vote;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

	List<Vote> findByUser(User user);
	List<Vote> findByVotable(Votable v);

    Vote findByUserAndVotable(User loggedinUser, Votable v);

}

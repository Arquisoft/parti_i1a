package uniovi.asw.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.persistence.model.User;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    
    public Proposal findByUser(User user);

}

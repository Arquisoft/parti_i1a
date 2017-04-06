package asw.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asw.persistence.model.Comment;
import asw.persistence.model.Proposal;
import asw.persistence.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	public List<Comment> findByProposal(Proposal proposal);

	public List<Comment> findByUser(User user);

}
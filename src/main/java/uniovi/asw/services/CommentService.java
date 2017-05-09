package uniovi.asw.services;

import java.util.List;

import uniovi.asw.persistence.model.Comment;
import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.persistence.model.User;

public interface CommentService {
	
	Comment save(Comment comment);
	boolean checkExists(Long id);
	List<Comment> findAll();

    void delete(Comment comment);

    Comment findById(Long id);
    List<Comment> findByUser(User user);
    List<Comment> findByProposal(Proposal proposal);
    Comment findByProposalAndId(Long proposalId, Long id);

    void updateComment(Long proposalId, Comment comment);
    void clearTable();
}

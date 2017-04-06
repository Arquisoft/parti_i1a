package asw.services;

import java.util.List;

import asw.persistence.model.Comment;

public interface CommentService {
	
	void save(Comment comment);
	boolean checkExists(Long id);
	List<Comment> findAll();	
}

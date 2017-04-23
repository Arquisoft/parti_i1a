package uniovi.asw.services;

import java.util.List;

import uniovi.asw.persistence.model.Comment;

public interface CommentService {
	
	void save(Comment comment);
	boolean checkExists(Long id);
	List<Comment> findAll();	
}

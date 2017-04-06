package asw.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asw.persistence.model.Comment;
import asw.persistence.repositories.CommentRepository;
import asw.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	private CommentRepository repository;
	
	@Autowired
	public CommentServiceImpl(CommentRepository repository) {
		setRepository(repository);
	}

	@Override
	public void save(Comment comment) {
		getRepository().save(comment);
	}

	@Override
	public List<Comment> findAll() {
		List<Comment> comments = new ArrayList<>();
		if (getRepository().findAll() != null) {
			Iterator<Comment> it = getRepository().findAll().iterator();
			while (it.hasNext())
				comments.add(it.next());
		}
		return comments;
	}	

	@Override
	public boolean checkExists(Long id) {
		return getRepository().findOne(id) != null;
	}

	private void setRepository(CommentRepository repository) {
		this.repository = repository;
	}
	
	private CommentRepository getRepository(){
		return this.repository;
	}

}

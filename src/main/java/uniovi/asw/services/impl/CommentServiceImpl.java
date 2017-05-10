package uniovi.asw.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniovi.asw.persistence.model.Comment;
import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.persistence.model.User;
import uniovi.asw.persistence.repositories.CommentRepository;
import uniovi.asw.persistence.repositories.ProposalRepository;
import uniovi.asw.producers.KfkaProducer;
import uniovi.asw.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	private CommentRepository repository;
	private ProposalRepository proposalRepo;
	private KfkaProducer producer;
	
	@Autowired
	public CommentServiceImpl(CommentRepository repository, KfkaProducer producer) {
		setRepository(repository);
		this.producer = producer;
	}

	@Override
	public Comment save(Comment comment) {
		return getRepository().save(comment);
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

    @Override
    public void delete(Comment comment) {
        getRepository().delete(comment);
    }

    @Override
    public Comment findById(Long id) {
        return getRepository().findOne(id);
    }

    @Override
    public List<Comment> findByUser(User user) {
        return getRepository().findByUser(user);
    }

    @Override
    public List<Comment> findByProposal(Proposal proposal) {
        List<Comment> list = new ArrayList<Comment>();
        for( Comment c: getRepository().findAll()){
            if(c.getProposal().equals(proposal)){
                list.add(c);
            }
        }
        return list;
    }

    @Override
    public Comment findByProposalAndId(Long proposalId, Long id) {
        Proposal p = proposalRepo.findOne(proposalId);
        Comment c = getRepository().findOne(id);
        if(!p.getComments().contains(c))
            throw new IllegalStateException("The proposal does not contain the specified comment");
        return c;
    }

    @Override
    public void updateComment(Long proposalId, Comment comment) {
        Proposal prop = proposalRepo.findOne(proposalId);
        prop.getComments().add(comment);
        proposalRepo.save(prop);
    }

    @Override
    public void clearTable() {
        getRepository().deleteAll();
    }

	@Override
	public Comment makeComment(Comment comment) {
		comment = getRepository().save(comment);
		producer.sendComment(comment);
		return comment;
	}
}

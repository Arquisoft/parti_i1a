package uniovi.asw.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniovi.asw.persistence.model.Comment;
import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.persistence.repositories.CommentRepository;
import uniovi.asw.persistence.repositories.ProposalRepository;
import uniovi.asw.persistence.repositories.VoteRepository;
import uniovi.asw.producers.KfkaProducer;
import uniovi.asw.services.ProposalService;

@Service
public class ProposalServiceImpl implements ProposalService {

	private ProposalRepository proposalRepository;
	private CommentRepository commentRepository;
	private VoteRepository voteRepository;
	private KfkaProducer producer;

	@Autowired
	public ProposalServiceImpl(ProposalRepository proposalRepository,
			CommentRepository commentRepository,
			VoteRepository voteRepository, KfkaProducer producer) {
		setProposalRepository(proposalRepository);
		setCommentRepository(commentRepository);
		setVoteRepository(voteRepository);
		this.producer = producer;
	}

	@Override
	public Proposal save(Proposal proposal) {
		return getProposalRepository().save(proposal);
	}

	@Override
	public List<Proposal> findAll() {
		List<Proposal> proposals = new ArrayList<>();
		if (getProposalRepository().findAll() != null) {
			Iterator<Proposal> it = getProposalRepository().findAll()
					.iterator();
			while (it.hasNext())
				proposals.add(it.next());
		}
		return proposals;
	}

	@Override
	public boolean checkExists(Long id) {
		return getProposalRepository().findOne(id) != null;
	}

	private void setProposalRepository(
			ProposalRepository repository) {
		this.proposalRepository = repository;
	}

	private ProposalRepository getProposalRepository() {
		return this.proposalRepository;
	}

	private CommentRepository getCommentRepository() {
		return commentRepository;
	}

	private void setCommentRepository(
			CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	private VoteRepository getVoteRepository() {
		return voteRepository;
	}

	private void setVoteRepository(VoteRepository voteRepository) {
		this.voteRepository = voteRepository;
	}

	@Override
	public Proposal findById(Long id) {
		return getProposalRepository().findOne(id);
	}

	@Override
	public void clearTable() {
		getProposalRepository().deleteAll();
	}

	@Override
	public void delete(Proposal proposal) {
		for (Comment comment : proposal.getComments()) {
			// This method is as close as possible to 
			// ProposalRepository's delete for simplicity
			// so we don't "undo" the votes through VoteService
			getVoteRepository().delete(comment.getVotes());
			getCommentRepository().delete(comment);
		}
		getProposalRepository().delete(proposal);
	}
	
	@Override
	public void remove(Proposal proposal) {
		Long id = proposal.getId();
		
		// Votes aren't "undone" through VoteService
		// as their only impact beyond Kafka is the vote count
		// of the proposal that is about to be deleted
		delete(proposal);
		
		producer.sendProposalDeleted(id);
	}

	@Override
	public Proposal findProposalByTitle(String tit) {
		return getProposalRepository().findByTitle(tit);
	}

	@Override
	public Proposal makeProposal(Proposal proposal) {
		proposal = getProposalRepository().save(proposal);
		producer.sendProposal(proposal);
		return proposal;
	}
}

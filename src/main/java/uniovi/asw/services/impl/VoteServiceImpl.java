package uniovi.asw.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniovi.asw.persistence.model.Association;
import uniovi.asw.persistence.model.User;
import uniovi.asw.persistence.model.Votable;
import uniovi.asw.persistence.model.Vote;
import uniovi.asw.persistence.model.types.VoteType;
import uniovi.asw.persistence.repositories.VoteRepository;
import uniovi.asw.producers.KfkaProducer;
import uniovi.asw.services.VoteService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {

	private VoteRepository repo;
	private KfkaProducer producer;

	@Autowired
	public VoteServiceImpl(VoteRepository repo,
			KfkaProducer producer) {
		this.repo = repo;
		this.producer = producer;
	}

	public Vote makeVote(Vote v) {
		v = repo.save(v);
		producer.sendVote(v.getVotable().getId(),
				v.getVoteType() == VoteType.POSITIVE);
		return v;
	}

	@Override
	public boolean checkExists(Long id) {
		return repo.exists(id);
	}

	@Override
	public List<Vote> findAll() {
		List<Vote> votes = new ArrayList<Vote>();
		if (repo.findAll() != null) {
			Iterator<Vote> it = repo.findAll().iterator();
			while (it.hasNext())
				votes.add(it.next());
		}
		return votes;
	}

	@Override
	public List<Vote> findVoteByUser(User user) {
		return repo.findByUser(user);
	}

	@Override
	public List<Vote> findVoteByVotable(Votable v) {
		return repo.findByVotable(v);
	}

	@Override
	public Vote findVoteByUserByVotable(User loggedinUser,
			Votable v) {
		return repo.findByUserAndVotable(loggedinUser, v);
	}

	@Override
	public void deleteVote(Vote v) {
		repo.delete(v);
	}

	@Override
	public void clearTable() {
		repo.deleteAll();
	}

	@Override
	public Vote save(Vote v) {
		return repo.save(v);
	}

	@Override
	public void undoVote(Vote v) {
		Long votableId = v.getVotable().getId();
		boolean notificationType = v
				.getVoteType() == VoteType.NEGATIVE;
		
		Association.Votation.unlink(v.getUser(), v, v.getVotable());
		repo.delete(v);
		producer.sendVote(votableId, notificationType);
	}

}

package uniovi.asw.services;

import uniovi.asw.persistence.model.User;
import uniovi.asw.persistence.model.Votable;
import uniovi.asw.persistence.model.Vote;

import java.util.List;

public interface VoteService {

	public Vote save(Vote v);
	public void deleteVote(Vote v);
	public boolean checkExists(Long id);
	
	public List<Vote> findAll();

	public List<Vote> findVoteByUser(User user);
	public List<Vote> findVoteByVotable(Votable v);
	
	public void clearTable();

    Vote findVoteByUserByVotable(User loggedinUser, Votable v);
}

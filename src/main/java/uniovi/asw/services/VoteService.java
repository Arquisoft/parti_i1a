package uniovi.asw.services;

import java.util.List;

import uniovi.asw.persistence.model.User;
import uniovi.asw.persistence.model.Votable;
import uniovi.asw.persistence.model.Vote;

public interface VoteService {

	public Vote save(Vote v);

	/**
	 * Saves a vote and sends a Kafka notification
	 * @param v the vote to save
	 * @return saved vote
	 */
	public Vote makeVote(Vote v);

	public void deleteVote(Vote v);
	
	/**
	 * Unlinks and deletes a vote and sends a Kafka notification
	 * @param v the vote to be deleted and unlinked
	 */
	public void undoVote(Vote v);

	public boolean checkExists(Long id);

	public List<Vote> findAll();

	public List<Vote> findVoteByUser(User user);

	public List<Vote> findVoteByVotable(Votable v);

	public void clearTable();

	Vote findVoteByUserByVotable(User loggedinUser, Votable v);
}

package uniovi.asw.persistence.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniovi.asw.persistence.model.types.VoteType;
import uniovi.asw.producers.KfkaProducer;
import uniovi.asw.services.VoteService;

@Service
public class VoteMaker {

	@Autowired
	private KfkaProducer producer;

	@Autowired
	private VoteService vs;

	public Vote makeVote(User user, Votable votable,
			VoteType voteType) {
		Vote vote = new Vote(user, votable, voteType);
		vs.save(vote);
		notifyNewVote(votable, voteType);
		return vote;
	}

	public void deleteVote(Vote vote) {
		Votable votable = vote.getVotable();
		VoteType notificationType; 
		if (vote.getVoteType() == VoteType.POSITIVE) {
			notificationType = VoteType.NEGATIVE;
		} else {
			notificationType = VoteType.POSITIVE;
		}
		Association.Votation.unlink(vote.getUser(), vote, votable);
		vs.deleteVote(vote);
		notifyNewVote(votable, notificationType);
	}

	private void notifyNewVote(Votable votable, VoteType voteType) {
		long votableId = votable.getId();
		char sign = voteType == VoteType.POSITIVE ? '+' : '-';
		String message = String.format("%d;%s", votableId, sign);
		producer.send("newVote", message);
	}
}

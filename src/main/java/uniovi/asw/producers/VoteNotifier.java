package uniovi.asw.producers;

import uniovi.asw.persistence.model.Vote;

public interface VoteNotifier {
	public void notifyNewVote(Vote vote);
}

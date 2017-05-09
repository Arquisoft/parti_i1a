package uniovi.asw.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uniovi.asw.persistence.model.Vote;
import uniovi.asw.persistence.model.types.VoteType;


@Service
public class VoteNotifierImpl implements VoteNotifier {
	
	@Autowired
	private KfkaProducer producer;
	
	public void notifyNewVote(Vote vote) {
		long votableId = vote.getVotable().getId();
		char sign = vote.getVoteType() == VoteType.POSITIVE? '+' : '-';
		String message = String.format("%d;%s", votableId, sign);
		producer.sendVoteDePablo("newVote", message);
	}
}

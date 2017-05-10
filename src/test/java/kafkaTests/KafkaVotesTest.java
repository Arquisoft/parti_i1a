package kafkaTests;

import org.junit.Test;
import org.springframework.kafka.annotation.KafkaListener;

import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.persistence.model.User;
import uniovi.asw.persistence.model.Vote;
import uniovi.asw.persistence.model.types.VoteType;

public class KafkaVotesTest extends KafkaTest {

	@Test
	public void testMessages() throws Exception {
		
		Proposal prop = pS.findAll().get(0);
		User user0 = uS.findAll().get(0);
		User user1 = uS.findAll().get(1);

		expectedMessages.add(String.format("%d;+", prop.getId()));
		expectedMessages.add(String.format("%d;-", prop.getId()));

		vS.makeVote(new Vote(user0, prop, VoteType.POSITIVE));
		vS.makeVote(new Vote(user1, prop, VoteType.NEGATIVE));
		
		assertReceived();
	}

	@KafkaListener(topics = "newVote")
	public void listenNewVote(String message) {
		listen(message);
	}
}

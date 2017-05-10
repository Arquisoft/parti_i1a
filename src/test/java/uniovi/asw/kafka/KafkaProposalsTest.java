package uniovi.asw.kafka;

import org.junit.Test;
import org.springframework.kafka.annotation.KafkaListener;

import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.persistence.model.User;

public class KafkaProposalsTest extends KafkaTest {

	@Test
	public void testMessages() throws Exception {
		
		User u1 = uS.findAll().get(0);
		
		Proposal p1 = new Proposal(u1, "Proposal 1", "None pizza", "POLITICS");
		expectedMessages.add(p1.toString());
		pS.makeProposal(p1);
		
		Proposal p2 = new Proposal(u1, "Proposal 2", "None pineapple", "SPORTS");
		expectedMessages.add(p2.toString());
		pS.makeProposal(p2);
		
		expectedMessages.add("" + p1.getId());
		pS.remove(p1);
		
		expectedMessages.add("" + p2.getId());
		pS.remove(p2);
	}

	@KafkaListener(topics = "newProposal")
	public void listen(Proposal proposal) {
		listen(proposal.toString());
	}
	
	@KafkaListener(topics = "deletedProposal")
	public void listen(String message) {
		listen(message);
	}
}

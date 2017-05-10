package uniovi.asw.kafka;

import org.junit.Test;
import org.springframework.kafka.annotation.KafkaListener;

import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.persistence.model.User;
import uniovi.asw.persistence.model.types.Topic;

public class KafkaProposalsTest extends KafkaTest {

	@Test
	public void testMessages() throws Exception {

		User u1 = uS.findAll().get(0);

		Proposal p1 = new Proposal(u1, "Proposal 1", "None pizza", Topic.POLITICS);
		expectedMessages.add(getRelevantInfo(p1));
		pS.makeProposal(p1);

		Proposal p2 = new Proposal(u1, "Proposal 2", "None pineapple", Topic.SPORTS);
		expectedMessages.add(getRelevantInfo(p2));
		pS.makeProposal(p2);

		expectedMessages.add("" + p1.getId());
		pS.remove(p1);

		expectedMessages.add("" + p2.getId());
		pS.remove(p2);

		assertReceived();
	}

	@KafkaListener(topics = "newProposal", containerFactory = "kafkaProposalListenerContainerFactory")
	public void listenNewProposal(Proposal proposal) {
		listen(getRelevantInfo(proposal));
	}

	@KafkaListener(topics = "deletedProposal")
	public void listenDeletedProposal(String message) {
		listen(message);
	}

	private String getRelevantInfo(Proposal proposal) {
		return "Proposal [title=" + proposal.getTitle()
				+ ", description=" + proposal.getDescription()
				+ ", topic=" + proposal.getTopic() + "]";
	}
}

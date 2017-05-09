package uniovi.asw.kafka;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.test.context.junit4.SpringRunner;

import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.persistence.model.User;
import uniovi.asw.persistence.model.Vote;
import uniovi.asw.persistence.model.types.Topic;
import uniovi.asw.persistence.model.types.VoteType;
import uniovi.asw.producers.KfkaProducer;
import uniovi.asw.services.FillDatabase;
import uniovi.asw.services.ProposalService;
import uniovi.asw.services.UserService;
import uniovi.asw.services.VoteService;

public class KafkaProposalsTest extends KafkaTest {

	@Test
	public void testMessages() throws Exception {
		
		User u1 = uS.findAll().get(0);
		Proposal p1 = new Proposal(u1, "Proposal 1", "None pizza", "POLITICS");
		
		// TODO: do the test
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

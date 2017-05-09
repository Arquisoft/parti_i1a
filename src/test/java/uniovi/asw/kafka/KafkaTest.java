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
import uniovi.asw.persistence.model.VoteMaker;
import uniovi.asw.persistence.model.types.VoteType;
import uniovi.asw.producers.KfkaProducer;
import uniovi.asw.services.FillDatabase;
import uniovi.asw.services.ProposalService;
import uniovi.asw.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class KafkaTest {

	@Autowired
	private UserService uS;
	@Autowired
	private ProposalService pS;
	@Autowired
	private KfkaProducer producer;
	@Autowired
	private FillDatabase fillDatabase;
	@Autowired
	private VoteMaker vm;

	private Set<String> expectedMessages;
	private Set<String> unexpectedMessages;

	public KafkaTest() {
		resetMessages();
	}

	@Before
	public void setUp() throws Exception {
		Thread.sleep(2000);
		resetMessages();
	}

	private void resetMessages() {
		expectedMessages = Collections
				.synchronizedSet(new HashSet<>());
		unexpectedMessages = Collections
				.synchronizedSet(new HashSet<>());
	}

	@After
	public void cleanUp() {
		fillDatabase.fill();
	}

	@Test
	public void testMessages() throws Exception {
		
		// Test messaging
		expectedMessages.add("TestVote1");
		expectedMessages.add("TestVote2");
		for (String message : expectedMessages) {
			producer.send("newVote", message);
		}

		// Test actual votes
		Proposal prop = pS.findAll().get(0);
		User user0 = uS.findAll().get(0);
		User user1 = uS.findAll().get(1);

		expectedMessages.add(String.format("%d;+", prop.getId()));
		expectedMessages.add(String.format("%d;-", prop.getId()));

		vm.makeVote(user0, prop, VoteType.POSITIVE);
		vm.makeVote(user1, prop, VoteType.NEGATIVE);
		
		assertReceived();
	}
	
	private void assertReceived() {
		long startMillis = System.currentTimeMillis();
		long timeOut = 15000;
		while (expectedMessages.size() != 0
				&& System.currentTimeMillis()
						- startMillis < timeOut) {
		}
		String errorMessage = "Expected messages not received: "
				+ expectedMessages
				+ "\nUnexpected messages received: "
				+ unexpectedMessages;

		Assert.assertEquals(errorMessage, 0, expectedMessages.size());
		Assert.assertEquals(errorMessage, 0,
				unexpectedMessages.size());
	}

	@KafkaListener(topics = "newVote")
	public void listen(String message) {
		if (!expectedMessages.remove(message)) {
			unexpectedMessages.add(message);
		}
	}
}

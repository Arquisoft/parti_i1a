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
import uniovi.asw.persistence.model.types.VoteType;
import uniovi.asw.producers.KfkaProducer;
import uniovi.asw.services.FillDatabase;
import uniovi.asw.services.ProposalService;
import uniovi.asw.services.UserService;
import uniovi.asw.services.VoteService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public abstract class KafkaTest {

	@Autowired
	protected UserService uS;
	@Autowired
	protected ProposalService pS;
	@Autowired
	protected KfkaProducer producer;
	@Autowired
	private FillDatabase fillDatabase;
	@Autowired
	protected VoteService vS;

	protected Set<String> expectedMessages;
	protected Set<String> unexpectedMessages;

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
	
	protected void assertReceived() {
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
	
	protected void listen(String message) {
		if (!expectedMessages.remove(message)) {
			unexpectedMessages.add(message);
		}
	}
}

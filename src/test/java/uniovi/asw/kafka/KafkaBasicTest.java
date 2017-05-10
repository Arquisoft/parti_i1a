package uniovi.asw.kafka;

import org.junit.Test;
import org.springframework.kafka.annotation.KafkaListener;

public class KafkaBasicTest extends KafkaTest {

	@Test
	public void testMessages() throws Exception {
		
		expectedMessages.add("TestVote1");
		expectedMessages.add("TestVote2");
		for (String message : expectedMessages) {
			producer.send("basicTest", message);
		}
		
		assertReceived();
	}

	@KafkaListener(topics = "basicTest")
	public void listenMessage(String message) {
		listen(message);
	}
}

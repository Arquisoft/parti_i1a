package uniovi.asw.kafka;

import org.junit.Test;
import org.springframework.kafka.annotation.KafkaListener;

import uniovi.asw.persistence.model.Comment;
import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.persistence.model.User;
import uniovi.asw.serializers.CommentMessage;

public class KafkaCommentsTest extends KafkaTest {

	@Test
	public void testMessages() throws Exception {

		User u1 = uS.findAll().get(0);
		Proposal p1 = pS.findAll().get(0);

		Comment c1 = new Comment("Comment 1 content", u1, p1);
		expectedMessages.add(new CommentMessage(c1).toString());
		cS.makeComment(c1);

		Comment c2 = new Comment("Comment 2 content", u1, p1);
		expectedMessages.add(new CommentMessage(c2).toString());
		cS.makeComment(c2);

		assertReceived();
	}

	@KafkaListener(topics = "newComment", containerFactory = "kafkaCommentMessageListenerContainerFactory")
	public void listen(CommentMessage comment) {
		listen(comment.toString());
	}

}

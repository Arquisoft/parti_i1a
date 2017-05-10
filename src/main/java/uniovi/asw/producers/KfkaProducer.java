package uniovi.asw.producers;

import javax.annotation.ManagedBean;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.persistence.model.Comment;

@ManagedBean
public class KfkaProducer {

	private static final Logger logger = Logger.getLogger(KafkaProducer.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private KafkaTemplate<String, Proposal> kafkaProposalTemplate;
	@Autowired
	private KafkaTemplate<String, Comment> kafkaCommentTemplate;
//	@Autowired
//	private MockGenerator generator;

//	@Scheduled(cron = "*/1 * * * * *")
	public void sendVote(long votableId, boolean positive) {

		String vote = votableId + ";" + (positive? "+" : "-");

		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("newVote", vote);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.info("Success on sending vote \"" + vote + "\"");
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("Error on sending vote \"" + vote + "\", stacktrace " + ex.getMessage());
			}
		});
	}

//	@Scheduled(cron = "*/30 * * * * *")
	public void sendProposal(Proposal proposal) {

		ListenableFuture<SendResult<String, Proposal>> future = kafkaProposalTemplate.send("newProposal", proposal);
		future.addCallback(new ListenableFutureCallback<SendResult<String, Proposal>>() {
			@Override
			public void onSuccess(SendResult<String, Proposal> result) {
				logger.info("Success on sending proposal: \"" + proposal + "\"");
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("Error on sending proposal: \"" + proposal + "\", stacktrace " + ex.getMessage());
			}
		});
	}
	
	public void sendComment(Comment comment) {

		ListenableFuture<SendResult<String, Comment>> future = kafkaCommentTemplate.send("newComment", comment);
		future.addCallback(new ListenableFutureCallback<SendResult<String, Comment>>() {
			@Override
			public void onSuccess(SendResult<String, Comment> result) {
				logger.info("Success on sending comment: \"" + comment + "\"");
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("Error on sending comment: \"" + comment + "\", stacktrace " + ex.getMessage());
			}
		});
	}

	public void sendProposalDeleted(long id) {
		send("deletedProposal", ""+id);
	}
	
    public void send(String topic, String data) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Success on sending message \"" + data + "\" to topic " + topic);
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Error on sending message \"" + data + "\", stacktrace " + ex.getMessage());
            }
        });
    }

}

package asw.producers;

import javax.annotation.ManagedBean;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import asw.persistence.model.Proposal;

@ManagedBean
public class KfkaProducer {

	private static final Logger logger = Logger.getLogger(KafkaProducer.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private KafkaTemplate<String, Proposal> kafkaProposalTemplate;
	@Autowired
	private MockGenerator generator;

	@Scheduled(cron = "*/1 * * * * *")
	public void sendVote() {

		String vote = generator.generateVote();

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

	@Scheduled(cron = "*/30 * * * * *")
	public void sendProposal() {

		Proposal proposal = generator.generateProposal();

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

}

package uniovi.asw.hello;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import uniovi.asw.persistence.model.Comment;
import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.persistence.model.User;
import uniovi.asw.serializers.CommentMessage;
import uniovi.asw.services.ProposalService;

@Component
@Scope("singleton")
public class ProposalsLiveHandler {

	private static final Logger logger = Logger
			.getLogger(MainController.class);

	/**
	 * The proposals for the live window, mapped by id in order to be much
	 * faster
	 */
	private Map<Long, Proposal> proposals;// = generateProposals();

	@Autowired
	private ProposalService pService;

	/**
	 * When new vote arrives, the data is updated here, NOT updated on the
	 * database, participation team will handle that, we need to focus on
	 * updating the date shown to the user.
	 * 
	 * @param data
	 */
	@KafkaListener(topics = "newVote")
	public void listen(String data) {
		String[] contents = data.split(";");

		if (contents.length != 2)
			return;

		Proposal p;
		int newVote;

		if (proposals.containsKey(Long.parseLong(contents[0]))) {
			p = proposals.get(Long.parseLong(contents[0]));

			if (contents[1].equals("+"))
				newVote = +1;
			else if (contents[1].equals("-"))
				newVote = -1;
			else
				newVote = 0;

			p.setNumberOfVotes(p.getNumberOfVotes() + newVote);
		}

		logger.info("New vote received: \"" + data + "\"");
	}

	@KafkaListener(topics = "newProposal", containerFactory = "kafkaProposalListenerContainerFactory")
	public void listenProposals(Proposal proposal) {
		proposals.put(proposal.getId(), proposal);

		logger.info("New proposal received: \"" + proposal + "\"");
	}

	@KafkaListener(topics = "newComment", containerFactory = "kafkaCommentMessageListenerContainerFactory")
	public void listenComments(CommentMessage receivedComment) {
		// Retrieve the referenced proposal (the received one is empty)
		Proposal proposal = proposals
				.get(receivedComment.getProposalId());

		if (proposal == null) {
			throw new RuntimeException(
					"New Comment message received for unexisting proposal "
							+ receivedComment.toString());
		}

		// Build a User with the provided information
		User user = new User();
		user.setId(receivedComment.getUserId());
		user.setName(receivedComment.getUserName());

		// Build a Comment
		// This comment will be stored in the proposal comments collection
		new Comment(receivedComment.getContent(), user, proposal);

		logger.info(
				"New comment received: \"" + receivedComment + "\"");
	}

	@KafkaListener(topics = "deletedProposal")
	public void listenDeletedProposals(String idStr) {
		long id = Long.parseLong(idStr);
		proposals.remove(id);
	}

	/**
	 * Do the initial search on the database
	 */
	@PostConstruct
	private void updateProposalsFromDatabase() {

		Map<Long, Proposal> proposalsMap = new HashMap<Long, Proposal>();

		List<Proposal> proposalsList = pService.findAll();

		for (Proposal p : proposalsList)
			proposalsMap.put(p.getId(), p);

		this.proposals = proposalsMap;
		logger.info("Loading proposals from the database");
	}

	public Map<Long, Proposal> getMap() {
		return proposals;
	}

}

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

import uniovi.asw.persistence.model.Association;
import uniovi.asw.persistence.model.Comment;
import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.persistence.model.Topic;
import uniovi.asw.persistence.model.User;
import uniovi.asw.services.CommentService;
import uniovi.asw.services.ProposalService;
import uniovi.asw.services.UserService;

@Component
@Scope("singleton")
public class ProposalsLiveHandler {

	private static final Logger logger = Logger.getLogger(MainController.class);

	/**
	 * The proposals for the live window, mapped by id in order to be much
	 * faster
	 */
	private Map<Long, Proposal> proposals;// = generateProposals();

	@Autowired
	private ProposalService pService;

	@Autowired
	private CommentService cService;

	@Autowired
	private UserService uService;

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

	/**
	 * Do the initial search on the database
	 */
	@PostConstruct
	private void updateProposalsFromDatabase() {

		Proposal p1 = new Proposal();

		p1.setTitle("Liberate snakes through the city");
		p1.setContent("We all hate rats, we should set" + " some snakes free to eat them, once"
				+ " the rats are extinct we can throw the snakes in Gijón");
		p1.setMinVotes(100);
		p1.setTopic(Topic.HEALTHCARE);
		p1.setNumberOfVotes(890);

		pService.save(p1);

		User u1 = new User();
		u1.setName("David");
		u1.setEmail("asuka98XD@gmail.com");
		u1.setDNI("123");
		u1.setPassword("1234");

		User u2 = new User();
		u2.setName("Francisco");
		u2.setEmail("marhuenda@elmundo.com");
		u2.setDNI("456");
		u2.setPassword("1234");

		uService.save(u1);
		uService.save(u2);

		Comment c1 = new Comment();
		Association.MakeComment.link(u1, c1, p1);
		c1.setContent("pole");

		Comment c2 = new Comment();
		c2.setContent("No te lo perdonare Carmena");
		Association.MakeComment.link(u2, c2, p1);

		cService.save(c1);
		cService.save(c2);

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

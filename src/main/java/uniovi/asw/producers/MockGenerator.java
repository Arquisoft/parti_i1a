package uniovi.asw.producers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uniovi.asw.persistence.model.Comment;
import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.persistence.model.Topic;
import uniovi.asw.services.ProposalService;

/**
 * Simulates user interaction, generating votes and proposals. Useful for Kafka
 * testing.
 */
@Component
public class MockGenerator {

    public static final String MOCK_CONTENT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer ultrices auctor venenatis. Duis tempus mi nec elit condimentum, eu tincidunt urna";
    private static final Random RANDOM = new Random();

    private List<String> titles;

    public MockGenerator() {
	titles = new ArrayList<String>();
	titles.add("Free coffee on Mondays");
	titles.add("3-day weekends");
	titles.add("Why don't we just die already");
	titles.add("Send SDI to hell");
	titles.add("Make la vida less dura");
	titles.add("MAKE ASW GREAT AGAIN!");
    }

    @Autowired
    private ProposalService proposalService;

    /**
     * Simulates the creation of a random vote of the form idProposal;symbol
     * where symbol is either "+" or "-".
     * 
     * The id of the proposal as well as the symbol are chosen randomly.
     * 
     * @return A String representing a vote.
     */
    public String generateVote() {

	int randomPos = RANDOM.nextInt(proposalService.findAll().size());
	String vote = String.valueOf(proposalService.findAll().get(randomPos).getId());

	vote += ";";
	vote += System.currentTimeMillis() % 2 == 0 ? "+" : "-";

	return vote;
    }

    /**
     * Simulates the generation of a new proposal.
     * 
     * @return The id of the new proposal.
     */
    public Proposal generateProposal() {
	Proposal p = new Proposal();

	p.setTitle(titles.get(RANDOM.nextInt(titles.size())));
	p.setContent(MOCK_CONTENT);
	p.setMinVotes(RANDOM.nextInt(100000));
	p.setTopic(Topic.randomTopic());
	p.setNumberOfVotes(RANDOM.nextInt(100000));

	proposalService.save(p);
	return p;
    }
    
    public Comment generateComment() {
	Comment c = new Comment();
	
	c.setContent(MOCK_CONTENT);
	c.setNumberOfVotes(RANDOM.nextInt(15));
	
//	TODO: llamar a Association como dios manda
//	int randomPos = RANDOM.nextInt(proposalRepository.findAll().size());
	
	return c;
    }

    public List<String> getTitles() {
        return titles;
    }
    
    
}

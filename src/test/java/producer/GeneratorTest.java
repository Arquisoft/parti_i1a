package producer;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import uniovi.asw.hello.Application;
import uniovi.asw.persistence.model.Comment;
import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.producers.MockGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=0" })
@SuppressWarnings({ "deprecation" })
public class GeneratorTest {

    @Autowired
    private MockGenerator mg;

    @Test
    public void constructorTest() {
	assertEquals(6, mg.getTitles().size());
    }

    @Test
    public void generateVoteTest() {
	String vote = mg.generateVote();
	assertTrue(vote.length() > 0);
	String[] contents = vote.split(";");
	Long.parseLong(contents[0]);
	assertEquals(2, contents.length);
	assertTrue(contents[1].equals("+") || contents[1].equals("-"));
    }
    
    @Test
    public void generateProposalTest() {
	Proposal p = mg.generateProposal();
	assertTrue(p.getTitle().length()>0);
	assertTrue(p.getDescription().length()>0);
	assertTrue(p.getTopic() != null);
    }
    
    @Test
    public void generateCommentTest() {
	Comment c = mg.generateComment();
	assertTrue(c.getContent().length()>0);
    }

}

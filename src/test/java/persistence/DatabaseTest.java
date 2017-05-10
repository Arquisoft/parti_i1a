package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import uniovi.asw.hello.Application;
import uniovi.asw.persistence.model.Comment;
import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.persistence.model.User;
import uniovi.asw.persistence.model.Vote;
import uniovi.asw.persistence.model.types.VoteType;
import uniovi.asw.services.CommentService;
import uniovi.asw.services.FillDatabase;
import uniovi.asw.services.ProposalService;
import uniovi.asw.services.UserService;
import uniovi.asw.services.VoteService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Component
public class DatabaseTest {

	private User u1, u2, u3, u4;
	private Proposal p1, p2, p3, p4;
	private Comment c1, c2, c3, c4;
	@Autowired
	private UserService uS;
	@Autowired
	private CommentService cS;
	@Autowired
	private ProposalService pS;
	@Autowired
	private VoteService vS;
	@Autowired
	private FillDatabase fD;
	
	@Before
	public void setUp() {
		clearData();
		initializeData();
		try {
			addUsers();
			addProposals();
			addComments();
		} catch (DataIntegrityViolationException e) {
			System.out.println("Reinserting data");
		}
	}

	@After
	public void restore() {
		fD.fill();
	}
	
	@Test
	public void testServices() {
		assertEquals(4, uS.findAll().size());
		assertTrue(uS.checkExists(u1.getId()));
		assertTrue(uS.checkExists(u2.getId()));
		assertTrue(uS.checkExists(u3.getId()));
		assertTrue(uS.checkExists(u4.getId()));
		
		// User Data 
		assertEquals("Gonzalo",u1.getName());
		assertEquals("Menéndez Borge",u1.getSurname());
		assertEquals("contraseña1",u1.getPassword());
		assertEquals("mail1",u1.getEmail());
		assertEquals("Spain",u1.getNationality());
		assertEquals("1111",u1.getDni());
		assertEquals("Address1",u1.getAddress());
		assertEquals(new Date(2500000),u1.getBirth());
		u1.setId(new Long(5));
		assertEquals(new Long(5), u1.getId());
		
		// Comments Data
		assertEquals("Gonzalo you can't leave us yet, we still have another ASW deliverable"
				,c1.getContent());
		assertEquals(u2,c1.getUser());
		assertEquals(p1,c1.getProposal());

		// Proposals

		assertEquals(4, pS.findAll().size());
		assertTrue(pS.checkExists(p1.getId()));
		assertTrue(pS.checkExists(p2.getId()));
		assertTrue(pS.checkExists(p3.getId()));
		assertTrue(pS.checkExists(p4.getId()));

		// Comments

		assertEquals(4, cS.findAll().size());
		assertTrue(cS.checkExists(c1.getId()));
		assertTrue(cS.checkExists(c2.getId()));
		assertTrue(cS.checkExists(c3.getId()));
		assertTrue(cS.checkExists(c4.getId()));

		// Making Proposals
		makeProposals();
		assertEquals(3, u1.getProposals().size());
		assertEquals(1, u2.getProposals().size());
		p1.addVote();
		assertEquals(p1.getVotes().size()+1, p1.getNumberOfVotes());

		// Commenting
		comments();
		assertEquals(3, u1.getComments().size());
		assertEquals(2, p1.getComments().size());
		assertEquals(2, p2.getComments().size());
		assertEquals(1, p3.getComments().size());
		assertEquals(2, u2.getComments().size());
		assertEquals(2, u3.getComments().size());

		// Voting
		vote();
		assertEquals(3, u1.getVotes().size());
		assertEquals(2, p1.getVotes().size());		
		assertEquals(1, p2.getVotes().size());		
		
		// Removing the comment/proposal
		u1.deleteComment(p1, c1);		
		u1.deleteProposal(p1);		
		assertEquals(2, u1.getComments().size());
		assertEquals(2, u1.getProposals().size());	
		
		// Equals assertions
		assertEquals(p1,p1);
		assertEquals(u1,u1);
		assertEquals(c1,c1);		
	}

	private void initializeData() {
		initializeUsers();
		initializeProposals();
		initializeComments();
	}

	private void clearData() {
		vS.clearTable();
		cS.clearTable();
		pS.clearTable();
		uS.clearTable();
	}
	
	// Create Users
	
	private void initializeUsers() {
		u1 = new User("Gonzalo", "Menéndez Borge", "contraseña1", "mail1", "Spain", "1111", "Address1", new Date(2500000));
		u2 = new User("Jorge", "López Alonso", "contraseña2", "mail2", "Spain", "2222", "Address2", new Date());
		u3 = new User("Julián", "García Murias", "contraseña3", "mail3", "Spain", "3333", "Address3", new Date());
		u4 = new User("Sergio", "Mosquera Dopico", "contraseña4", "mail4", "Spain", "4444", "Address4", new Date());
	}

	// Create Proposals
	
	private void initializeProposals() {
		p1 = new Proposal(u1, "Let me return to Ireland", "I was happy there and my friend Ortin is still there");
		p2 = new Proposal(u2, "Take SDI easier", "We think that this subject is so hard");
		p3 = new Proposal(u3, "Fire Fernando Hierro",
				"If he keeps training Real Oviedo we'll never move into Liga Santander");
		p4 = new Proposal(u4, "University bus to Piedrasblancas each half an hour",
				"Is horrible when you have to wait an hour for the bus because the ASW presentations took too long");
	}

	// Create Comments
	
	private void initializeComments() {
		c1 = new Comment("Gonzalo you can't leave us yet, we still have another ASW deliverable", u2, p1);
		c2 = new Comment("Jorge leave that and let's go eating a slice of spanish tortilla on San Fernando", u3, p2);
		c3 = new Comment("The coach is not important while Jon Erice keeps playing", u4, p3);
		c4 = new Comment("You Piedrasblancas people always complaining...", u1, p4);
	}

	// Save Users
	
	private void addUsers() {
		uS.save(u1);
		uS.save(u2);
		uS.save(u3);
		uS.save(u4);
	}

	// Save Proposals
	
	private void addProposals() {
		pS.save(p1);
		pS.save(p2);
		pS.save(p3);
		pS.save(p4);
	}

	// Save Comments
	
	private void addComments() {
		cS.save(c1);
		cS.save(c2);
		cS.save(c3);
		cS.save(c4);
	}

	// Asigning each proposal to a user
	
	private void makeProposals() {
		u1.makeProposal(p1);
		u2.makeProposal(p2);
		u1.makeProposal(p3);
		u1.makeProposal(p4);
	}

	// Asigning a comment to a user and a proposal
	
	private void comments() {
		u1.comment(p1, c1);
		u1.comment(p2, c2);
		u2.comment(p3, c3);
		u3.comment(p1, c3);
		u3.comment(p2, c3);
		u3.comment(p3, c3);
	}

	// Voting proposals
	
	@SuppressWarnings("unused")
	private void vote() {
		Vote v1=new Vote(u1, p1, VoteType.POSITIVE);		
		Vote v2=new Vote(u1, p1, VoteType.POSITIVE);
		Vote v3=new Vote(u1, p2, VoteType.POSITIVE);
		Vote v4=new Vote(u1, p1, VoteType.POSITIVE);		
		u1.vote(v4, p1);
		u1.deleteVote(v4, p1);
	}

}

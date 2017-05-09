package uniovi.asw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import uniovi.asw.persistence.model.*;
import uniovi.asw.persistence.model.types.VoteType;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Service
public class FillDatabase {

	private User u1, u2, u3, u4, uadmin;
	private Proposal p1, p2, p3, p4, p5;
	private Comment c1, c2, c3, c4, c5, c6;
	private Vote v1, v2, v3;
	
	@Autowired
	private UserService uS;
	@Autowired
	private CommentService cS;
	@Autowired
	private ProposalService pS;
	@Autowired
	private VoteService vS;
	
	@PostConstruct
	public void fill() {
		vS.clearTable();
		cS.clearTable();
		pS.clearTable();
		uS.clearTable();
		
		try {
			initializeData();
		} catch (DataIntegrityViolationException e) {
			System.out.println("Some errors occur during the initialization of the database (;-;) ");
		}

		check();
	}

    private void check() {
	    List<User> t = uS.findAll();
	    List<Comment> te = cS.findAll();
	    List<Proposal> tes = pS.findAll();
	    List<Vote> test = vS.findAll();

//	    System.out.println("Oh boy");
    }

    private void initializeData() {
		initializeUsers();
		initializeProposals();
		initializeComments();
		initializeVotes();
	}

    private void initializeUsers() {
		u1 = new User("Daniel","Fernández Feito", "password", "dani@gmail.com","Spain","1111","address1", new Date());
		u2 = new User("Diego Roger","Freijó Álvarez", "password", "diego@gmail.com","Spain","2222","address2", new Date());
		u3 = new User("Sergio","García Álvarez","password","sergio@gmail.com", "Australia","3333", "address3", new Date());
        u4 = new User("Pablo","García Ledo","password", "pablo@gmail.com","Kuala lumpur","4444","address4", new Date());
		uadmin = new User("Admin","Istrador","password","admin", "Admin Country","5555","address5", new Date());
		uadmin.setAdmin(true);
		addUsers();
	}

    private void addUsers() {
        u1 = uS.save(u1);
        u2 = uS.save(u2);
        u3 = uS.save(u3);
        u4 = uS.save(u4);
        uadmin = uS.save(uadmin);
    }

	private void initializeProposals() {
		p1 = new Proposal(u1, "Title1", "Description1", "HEALTHCARE");
		p2 = new Proposal(u2, "Title2", "Description2", "ENVIROMENT");
		p3 = new Proposal(u3, "Title3", "Description3", "POLITICS");
		p4 = new Proposal(u4, "Title4", "Description4", "SECURITY");
		p5 = new Proposal(u4, "Title5", "Description5", "SPORTS");
		addProposals();
	}

    private void addProposals() {
        p1 = pS.save(p1);
        p2 = pS.save(p2);
        p3 = pS.save(p3);
        p4 = pS.save(p4);
        p5 = pS.save(p5);
    }

	private void initializeComments() {
		c1 = new Comment("Comment body 1", u1, p1);
		c2 = new Comment("Comment body 2", u2, p2);
		c3 = new Comment("Comment body 3", u3, p3);
		c4 = new Comment("Comment body 4", u4, p4);
		c5 = new Comment("Comment body 5", u4, p5);
		c6 = new Comment("Comment body 6", u3, p5);
		addComments();
	}

    private void addComments() {
        c1 = cS.save(c1);
        c2 = cS.save(c2);
        c3 = cS.save(c3);
        c4 = cS.save(c4);
        c5 = cS.save(c5);
        c6 = cS.save(c6);
    }

    private void initializeVotes() {
        v1 = new Vote(u4, p1, VoteType.POSITIVE);
        v2 = new Vote(u3, p1, VoteType.POSITIVE);
        v3 = new Vote(u2, p1, VoteType.NEGATIVE);
        addVotes();
    }

    private void addVotes() {
        v1 = vS.save(v1);
        v2 = vS.save(v2);
        v3 = vS.save(v3);
	}

}
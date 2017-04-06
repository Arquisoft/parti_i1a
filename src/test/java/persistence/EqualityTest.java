package persistence;

import java.util.Date;

import org.junit.experimental.theories.DataPoint;

import asw.persistence.model.Comment;
import asw.persistence.model.Proposal;
import asw.persistence.model.User;
import asw.persistence.model.Vote;
import asw.persistence.model.VoteType;

public class EqualityTest extends ObjectTheories{
	
		@DataPoint
		public static final User u1 = new User("Gonzalo", "Menéndez Borge", "contraseña1", "mail1", "Spain", "1111", "Address1", new Date(2500000));
		@DataPoint
		public static final Proposal p1 = new Proposal(u1, "Let me return to Ireland", "I was happy there and my friend Ortin is still there");
		@DataPoint
		public static final Comment c1 = new Comment("Gonzalo you can't leave us yet, we still have another ASW deliverable", u1, p1);
		@DataPoint
		public static final Vote v1 = new Vote(u1, p1, VoteType.POSITIVE);
	}

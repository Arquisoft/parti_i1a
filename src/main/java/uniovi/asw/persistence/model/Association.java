package uniovi.asw.persistence.model;

import uniovi.asw.persistence.model.types.VoteType;

public class Association {

	public static class Votation {

		public static void link(User User, Vote vote, Votable votable) {
			vote._setVotable(votable);
			vote._setUser(User);
			
			if(vote.getVoteType().equals(VoteType.POSITIVE))
                votable.incrementUpvotes();
			else if(vote.getVoteType().equals(VoteType.NEGATIVE))
                votable.incrementDownvotes();

			votable._getVotes().add(vote);
			User._getVotes().add(vote);
		}

		public static void unlink(User User, Vote vote, Votable votable) {
			votable._getVotes().remove(vote);
			User._getVotes().remove(vote);

			vote._setVotable(null);
			vote._setUser(null);

			if(vote.getVoteType().equals(VoteType.POSITIVE))
                votable.decrementUpvotes();
			else if(vote.getVoteType().equals(VoteType.NEGATIVE))
                votable.decrementDownvotes();
			
		}
	}

	public static class MakeProposal {

		public static void link(User user, Proposal proposal) {
			proposal.setUser(user);
			user._getProposals().add(proposal);
		}

		public static void unlink(User user, Proposal proposal) {
			user._getProposals().remove(proposal);
			proposal.setUser(null);
		}
	}

	public static class MakeComment {

		public static void link(User user, Comment comment, Proposal proposal) {
			comment._setProposal(proposal);
			comment._setUser(user);
			proposal._getComments().add(comment);
			user._getComments().add(comment);
		}

		public static void unlink(User user, Comment comment, Proposal proposal) {
			proposal._getComments().remove(comment);
			user._getComments().remove(comment);

			comment._setProposal(null);
			comment._setUser(null);
		}
	}
}
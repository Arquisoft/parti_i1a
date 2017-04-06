package asw.persistence.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(KeyVote.class)
public class Vote implements Serializable {

	private static final long serialVersionUID = -8143484614238441355L;

	@Id @ManyToOne
	private User user;
	
	@Id @ManyToOne
	private Votable votable;
	
	private VoteType voteType;	

	Vote() {}

	public Vote(User user, Votable votable, VoteType voteType) {
		setVoteType(voteType);
		Association.Votation.link(user, this, votable);
	}	
	
	public void _setUser(User user) {
		this.user = user;
	}

	void _setVotable(Votable votable) {
		this.votable = votable;
	}

	public VoteType getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}	
}

package uniovi.asw.persistence.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.beans.factory.annotation.Configurable;

import uniovi.asw.persistence.model.types.VoteType;

@Entity
@Configurable
//@IdClass(KeyVote.class)
public class Vote implements Serializable {

	private static final long serialVersionUID = -8143484614238441355L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ManyToOne
	private User user;
	
	@ManyToOne
	private Votable votable;

	@Enumerated(EnumType.STRING)
	private VoteType voteType;

	Vote(){}

	public Vote(User user, Votable votable, VoteType voteType) {
		setVoteType(voteType);
		Association.Votation.link(user, this, votable);
	}	
	
	public User getUser() {
		return user;
	}
	
	public void _setUser(User user) {
		this.user = user;
	}

	void _setVotable(Votable votable) {
		this.votable = votable;
	}

	public Votable getVotable(){ return votable; }

	public VoteType getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

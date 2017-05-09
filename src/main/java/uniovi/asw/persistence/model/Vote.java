package uniovi.asw.persistence.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import uniovi.asw.persistence.model.types.VoteType;
import uniovi.asw.producers.VoteNotifier;

import java.io.Serializable;

import javax.persistence.*;

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

    @Transient
    @Autowired
    private VoteNotifier notifier;

	Vote(){}

	public Vote(User user, Votable votable, VoteType voteType) {
		setVoteType(voteType);
		Association.Votation.link(user, this, votable);
        if (notifier != null) {
            notifier.notifyNewVote(this);
        }
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

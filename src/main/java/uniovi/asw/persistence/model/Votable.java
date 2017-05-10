package uniovi.asw.persistence.model;

import uniovi.asw.persistence.model.types.VoteType;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Votable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@OneToMany(mappedBy = "votable", cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
	private Set<Vote> votes = new HashSet<>();

    private int upvotes;
    private int downvotes;

    Votable() {}

    public Long getId() {
        return id;
    }
    
    public void setId(long id) {
    	this.id = id;
    }

    Set<Vote> _getVotes() {
        return votes;
    }

    public Set<Vote> getVotes(){
        return new HashSet<Vote>(votes);
    }

    public int getUpvotes() {
        process();
        return upvotes;
    }

    public int getDownvotes() {
        process();
        return downvotes;
    }

    public void incrementUpvotes() {
        this.upvotes++;
    }

    public void incrementDownvotes() {
        this.downvotes++;
    }

    public void decrementUpvotes() {
        this.upvotes--;
    }

    public void decrementDownvotes() {
        this.downvotes--;
    }

    private void process(){
        upvotes = 0; downvotes = 0;
        for(Vote v: votes){
            if( v.getVoteType() == VoteType.POSITIVE)
                upvotes++;
            if ( v.getVoteType() == VoteType.NEGATIVE)
                downvotes++;
        }
    }

    public int calculateScore() {
        process();
        int number = upvotes - downvotes;
        return number;
    }



    private int numberOfVotes;
    public int getNumberOfVotes(){return numberOfVotes;}
    public void setNumberOfVotes(int numberOfVotes){this.numberOfVotes = numberOfVotes;}
    public void addVote(){this.numberOfVotes++;}

}

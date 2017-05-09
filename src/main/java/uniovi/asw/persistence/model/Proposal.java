package uniovi.asw.persistence.model;

import uniovi.asw.persistence.model.types.Topic;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TProposals")
public class Proposal extends Votable{
	
	private String title;
	private String content;	
	private int minVotes;
	private Topic topic;
	
	@OneToMany(mappedBy = "proposal")
	private Set<Comment> comments = new HashSet<Comment>();
	
	@ManyToOne
	private User user;	
	
	public Proposal(){}	
	
	public Proposal(User user, String title, String content){
		Association.MakeProposal.link(user, this);
		setTitle(title);
		setContent(content);
	}

	public String getTitle() {
		return title;
	}
	
	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getMinVotes() {
		return minVotes;
	}
	public void setMinVotes(int minVotes) {
		this.minVotes = minVotes;
	}
		
	public Set<Comment> getComments(){
		return new HashSet<Comment>(comments);
	}
	
	Set<Comment> _getComments(){
		return this.comments;
	}

	public void setUser(User user) {
		this.user=user;		
	}

	@Override
	public String toString() {
	    return "Proposal [title=" + title + ", content=" + content + ", minVotes=" + minVotes + ", topic=" + topic
		    + ", comments=" + comments + ", user=" + user + "]";
	}	
}

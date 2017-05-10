package uniovi.asw.persistence.model;

import uniovi.asw.persistence.model.types.MinSupport;
import uniovi.asw.persistence.model.types.NotAllowedWords;
import uniovi.asw.persistence.model.types.Topic;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "TProposals")
public class Proposal extends Votable {

	private String title;
	private String description;
	private int minVotes;
	private Date created;
	@Enumerated(EnumType.STRING)
	private Topic topic;

	@ElementCollection
	private Set<String> notAllowedWords = NotAllowedWords.getInstance().getSet();

	@OneToMany(mappedBy = "proposal", fetch = FetchType.EAGER)
	private Set<Comment> comments = new HashSet<Comment>();

	@ManyToOne
	private User user;

	public Proposal() {
	}

	public Proposal(User user, String tit) {
		this.title = tit;
		this.comments = new HashSet<Comment>();
		this.notAllowedWords = NotAllowedWords.getInstance().getSet();
		this.minVotes = MinSupport.getInstance().getSupport();
		Association.MakeProposal.link(user, this);
	}

	public Proposal(User user, String tit, String description) {
		this.title = tit;
		this.description = description;
		this.comments = new HashSet<Comment>();
		this.notAllowedWords = NotAllowedWords.getInstance().getSet();
		this.minVotes = MinSupport.getInstance().getSupport();
		Association.MakeProposal.link(user, this);
	}

	public Proposal(User user, String tit, String desc, Topic topic) {
		this.title = tit;
		this.description = desc;
		this.topic = topic;
		this.minVotes = MinSupport.getInstance().getSupport();
		this.setCreated(new Date());
		this.comments = new HashSet<Comment>();
		this.notAllowedWords = NotAllowedWords.getInstance().getSet();
		Association.MakeProposal.link(user, this);
	}

	public Proposal(User user, String tit, String desc, Topic topic, int minSupport, Set<String> l) {
		this.title = tit;
		this.description = desc;
		this.topic = topic;
		this.setCreated(new Date());
		this.comments = new HashSet<Comment>();
		this.minVotes = minSupport;
		this.notAllowedWords = l;
		Association.MakeProposal.link(user, this);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMinVotes() {
		return minVotes;
	}

	public void setMinVotes(int minVotes) {
		this.minVotes = minVotes;
	}

	public Set<Comment> getComments() {
		return new HashSet<Comment>(comments);
	}

	Set<Comment> _getComments() {
		return this.comments;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean checkNotAllowedWords() {
		for (String s : notAllowedWords) {
			if (description.contains(s)) {
				System.out.println("Not allowed Word: " + s);
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "Proposal [title=" + title + ", description=" + description + ", minVotes=" + minVotes + ", topic="
				+ topic.name() + ", comments=" + comments + ", user=" + user + "]";
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}

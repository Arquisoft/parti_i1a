package asw.persistence.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TComments")
public class Comment extends Votable{

	private String content;	

	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Proposal proposal;

	public Comment() {
	}	
	
	public Comment(String content, User user, Proposal proposal) {
		super();
		setContent(content);
		Association.MakeComment.link(user, this, proposal);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public User getUser() {
		return user;
	}

	void _setUser(User user) {
		this.user = user;
	}

	public Proposal getProposal() {
		return proposal;
	}	

	void _setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((proposal == null) ? 0 : proposal.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}
	
}

package uniovi.asw.serializers;

import uniovi.asw.persistence.model.Comment;

/**
 * Simplifies the serialization and deserialization of newComment messages.
 * 
 * Created as response to serialization loops.
 * 
 * @author Pabloski
 */
public class CommentMessage {
	private long proposalId;
	private long userId;
	private String userName;
	private String content;

	public CommentMessage() {}
	
	public CommentMessage(long proposalId, long userId, String userName,
			String content) {
		super();
		this.proposalId = proposalId;
		this.userId = userId;
		this.userName = userName;
		this.content = content;
	}

	public CommentMessage(Comment comment) {
		this(comment.getProposal().getId(), comment.getUser().getId(),
				comment.getUser().getName(), comment.getContent());
	}
	
	public long getProposalId() {
		return proposalId;
	}

	public void setProposalId(long proposalId) {
		this.proposalId = proposalId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CommentMessage [proposalId=" + proposalId
				+ ", userId=" + userId + ", userName=" + userName
				+ ", content=" + content + "]";
	}
	
}

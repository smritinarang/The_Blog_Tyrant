package thedailyscribbles.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


/**
 * Class representing a Comment.
 * 
 * @author The Daily Scribbles Team
 *
 */
@Entity
@Table(name="comment_details")
public class Comment {
	
	/**
	 * unique identifier of the comment object.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer commentId;
	
	/**
	 * comment description
	 */
	private String commentDescription;
	
	/**
	 * no of votes the comment object has received
	 */
	private Integer votes;
	
	
	/**
	 * {@link Blogger} who made this comment
	 */
	@ManyToOne
	@NotNull
	private Blogger blogger;
	
	
	/**
	 * {@link Post} to which this comment belongs.
	 */
	@ManyToOne
	@JsonIgnore
	private Post post;
	
	/**
	 * flag variable to identify whether the comment owner has voted up his own comment
	 */
	private boolean voteUp;
	
	private LocalDateTime createdDateTime;
	
	
	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	//Constructors
	public Comment() {
		super();
	}
	
	public Comment(String commentDescription,Blogger blogger, Post post) {
		this.commentDescription = commentDescription;
		this.blogger = blogger;
		this.post = post;
	}

	
	//Getters and Setters
	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getCommentDescription() {
		return commentDescription;
	}

	public void setCommentDescription(String commentDescription) {
		this.commentDescription = commentDescription;
	}

	public Integer getVotes() {
		return votes;
	}

	public void setVotes(Integer votes) {
		this.votes = votes;
	}

	public Blogger getBlogger() {
		return blogger;
	}

	public void setBlogger(Blogger blogger) {
		this.blogger = blogger;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public boolean isVoteUp() {
		return voteUp;
	}

	public void setVoteUp(boolean voteUp) {
		this.voteUp = voteUp;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", commentDescription=" + commentDescription + ", votes=" + votes
				+ ", voteUp=" + voteUp + "]";
	}
	
	
}

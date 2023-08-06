package thedailyscribbles.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


/**
 * Class representing a Community.
 * 
 * @author The Daily Scribbles Team
 *
 */
@Entity
@Table(name="post_details")
public class Post {
	
	/**
	 * unique identifier of the post.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer postId;
	
	/**
	 * title of the post.
	 */
	private String title;
	
	/**
	 * post content
	 */
	@Column(columnDefinition = "TEXT")
	private String data;
	
	/**
	 * date and time on which the post was created.
	 */
	private LocalDateTime createdDateTime;
	
	
	/**
	 * blogger who created this post.
	 */
	@ManyToOne
	//@JoinColumn(name="blogger_id",referencedColumnName="uid",nullable=false)
	@JsonIgnore
	private Blogger blogOwner;
	
	
	/**
	 * list of bloggers who have upvoted this post.
	 */
	@ManyToMany(mappedBy="upVoted")
	private List<Blogger> blogUpVoters;
	
	
	/**
	 * list of bloggers who have downvoted this post.
	 */
	@ManyToMany(mappedBy="downVoted")
	@JsonIgnore
	private List<Blogger> blogDownVoters;
	
	
	/**
	 * list of comments in this post.
	 */
	@OneToMany(cascade=CascadeType.ALL,mappedBy="post")
	@JsonIgnore
	private List<Comment> comments;
	
	/**
	 * total amount of votes in this post.
	 */
	private Integer votes;
	
	/**
	 * flag variable to identify whether the blogOwner has upvoted his own post.
	 */
	private boolean voteUp;
	
	/**
	 * flag variable to indicate NSFW content.
	 */
	private boolean notSafeForWork;
	
	/**
	 * flag variable to indicate spoiler.
	 */
	private boolean spoiler;
	
	/**
	 * flag variable to signify original content.
	 */
	private boolean originalContent;
	
	//flair
	private String flair;
	
	
	/**
	 * flag variable to identify whether the moderator has shadowed this post.
	 */
	private boolean shadow;
	
	@Column(columnDefinition = "TEXT")
	private String postImage;
	
	
	//Constructors
	public Post() {
		super();
		this.votes = 0;
		this.shadow = false;
	}
	
	public Post(String title,String data) {
		this.title = title;
		this.data = data;
	}

	
	//Getters and Setters
	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Integer getVotes() {
		return votes;
	}

	public void setVotes(Integer votes) {
		this.votes = votes;
	}

	public boolean isVoteUp() {
		return voteUp;
	}

	public void setVoteUp(boolean voteUp) {
		this.voteUp = voteUp;
	}

	public boolean isNotSafeForWork() {
		return notSafeForWork;
	}

	public void setNotSafeForWork(boolean notSafeForWork) {
		this.notSafeForWork = notSafeForWork;
	}

	public boolean isSpoiler() {
		return spoiler;
	}

	public void setSpoiler(boolean spoiler) {
		this.spoiler = spoiler;
	}

	public boolean isOriginalContent() {
		return originalContent;
	}

	public void setOriginalContent(boolean originalContent) {
		this.originalContent = originalContent;
	}

	public String getFlair() {
		return flair;
	}

	public void setFlair(String flair) {
		this.flair = flair;
	}

	public Blogger getBlogOwner() {
		return blogOwner;
	}

	public void setBlogOwner(Blogger blogOwner) {
		this.blogOwner = blogOwner;
	}

	public List<Blogger> getBlogUpVoters() {
		return blogUpVoters;
	}

	public void setBlogUpVoters(List<Blogger> blogUpVoters) {
		this.blogUpVoters = blogUpVoters;
	}

	public List<Blogger> getBlogDownVoters() {
		return blogDownVoters;
	}

	public void setBlogDownVoters(List<Blogger> blogDownVoters) {
		this.blogDownVoters = blogDownVoters;
	}
	
	

	public boolean isShadow() {
		return shadow;
	}

	public void setShadow(boolean shadow) {
		this.shadow = shadow;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", title=" + title + ", data=" + data + ", createdDateTime=" + createdDateTime
				+ ", votes=" + votes + ", voteUp=" + voteUp + ", notSafeForWork="
				+ notSafeForWork + ", spoiler=" + spoiler + ", originalContent=" + originalContent + ", flair=" + flair
				+ "]";
	}

	public String getPostImage() {
		return postImage;
	}

	public void setPostImage(String postImage) {
		this.postImage = postImage;
	}
	
	
	
}

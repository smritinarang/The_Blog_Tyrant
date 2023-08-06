package thedailyscribbles.model;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * Class representing a Blogger.
 * 
 * @author The Daily Scribbles Team
 *
 */
@Entity
@Table(name="blogger_details")
public class Blogger extends User{
	
	
	/**
	 * Unique username of the blogger
	 */
	@Column(name="username",nullable=false,unique=true)
	@Length(min=MIN_USERNAME_LENGTH,message="Username must be at least "
			+MIN_USERNAME_LENGTH+" characters long!")
	@NotBlank(message= "Username field cannot be empty!")
	private String bloggerName;
	
	
	/**
	 * List of posts created by the blogger
	 */
	@OneToMany(cascade=CascadeType.ALL,mappedBy="blogOwner")
	@Column(name="posts")
	@JsonIgnore
	private List<Post> post;
	
	
	/**
	 * List of comments made by the blogger
	 */
	@OneToMany(cascade=CascadeType.ALL,mappedBy="blogger")
	@JsonIgnore
	private List<Comment> comments;
	
	
	/**
	 * List of posts upvoted by the blogger
	 */
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="post_upvoted",joinColumns = {@JoinColumn(name="bloggerId")},
	inverseJoinColumns = {@JoinColumn(name="productId")})
	@Column(name="upvoted")
	@JsonIgnore
	private List<Post> upVoted;
	
	
	/**
	 * List of posts downvoted by the blogger
	 */
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="post_downvoted",joinColumns = {@JoinColumn(name="bloggerId")},
	inverseJoinColumns = {@JoinColumn(name="productId")})
	@Column(name="downvoted")
	@JsonIgnore
	private List<Post> downVoted;
	
	
	/**
	 * List of communities the blogger is a part of
	 */
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="blogger_community",joinColumns = {@JoinColumn(name="bloggerId")},
	inverseJoinColumns= {@JoinColumn(name="community_id")})
	@JsonIgnore
	private List<Community> communities;
	
	
	/**
	 * The karma score of the blogger
	 */
	private Integer karma;
	
	@Column(columnDefinition = "TEXT")
	private String profilePicture;
	
	
	/**
	 * The role of the user
	 */
	private static final String USER_ROLE = "blogger";
	
	
	//Constructors
	public Blogger() {
		super();
		this.role = USER_ROLE;
		this.karma = 0;
	}
	
	public Blogger(String password,String bloggerName,Integer karma) {
		super(password,USER_ROLE);
		this.bloggerName = bloggerName;
		this.karma = karma;
	}

	
	//Getters and Setters
	public String getBloggerName() {
		return bloggerName;
	}

	public void setBloggerName(String bloggerName) {
		this.bloggerName = bloggerName;
	}
	

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public List<Post> getPost() {
		return post;
	}

	public void setPost(List<Post> post) {
		this.post = post;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Post> getUpVoted() {
		return upVoted;
	}

	public void setUpVoted(List<Post> upVoted) {
		this.upVoted = upVoted;
	}

	public List<Post> getDownVoted() {
		return downVoted;
	}

	public void setDownVoted(List<Post> downVoted) {
		this.downVoted = downVoted;
	}

	public List<Community> getCommunities() {
		return communities;
	}

	public void setCommunities(List<Community> communities) {
		this.communities = communities;
	}

	public Integer getKarma() {
		return karma;
	}

	public void setKarma(Integer karma) {
		this.karma = karma;
	}

	@Override
	public String toString() {
		return "Blogger [bloggerName=" + bloggerName + ", karma=" + karma + "]";
	}
	
}

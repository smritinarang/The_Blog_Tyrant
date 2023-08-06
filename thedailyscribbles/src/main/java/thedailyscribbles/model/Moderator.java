package thedailyscribbles.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;


/**
 * Class representing a Moderator.
 * 
 * @author The Daily Scribbles Team
 *
 */
@Component
@Entity
@Table(name="moderator_details")
public class Moderator extends User{
	
	/**
	 * Unique username of the moderator.
	 */
	@Column(name="username",nullable=false,unique=true)
	@Length(min=MIN_USERNAME_LENGTH,message="Username must be at least "+MIN_USERNAME_LENGTH+" characters long!")
	@NotBlank(message= "Username field cannot be empty!")
	private String moderatorName;
	
	
	/**
	 * Blogger Id of the moderator
	 */
	@Column(name="bloggerid",nullable=false,unique=true)
	private Integer bloggerId;
	
	
	/**
	 * role of the moderator.
	 */
	private static final String USER_ROLE = "mod";
	
	
	//Constructors
	public Moderator() {
		super();
		this.role=USER_ROLE;
	}
	
	public Moderator(String password, String moderatorName) {
		super(password,USER_ROLE);
		this.moderatorName = moderatorName;
	}

	
	//Getters and Setters
	public String getModeratorName() {
		return moderatorName;
	}

	public void setModeratorName(String moderatorName) {
		this.moderatorName = moderatorName;
	}
	

	public Integer getBloggerId() {
		return bloggerId;
	}

	public void setBloggerId(Integer bloggerId) {
		this.bloggerId = bloggerId;
	}

	@Override
	public String toString() {
		return "Moderator [moderatorName=" + moderatorName + "]";
	}
	
	
}


package thedailyscribbles.model;

import org.hibernate.validator.constraints.Length;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;


/**
 * The {@link User} class represents the basic user entity for the system.
 * It defines the attributes and methods for a user.
 * 
 * @author The Daily Scribbles Team
 */
@Entity
@Table(name="user_details")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class User {
	
	/**
     * The minimum length of a user name.
     */
	protected static final int MIN_USERNAME_LENGTH = 3;
	
	
	/**
     * The minimum length of a user's password.
     */
	private static final int MIN_PASSWORD_LENGTH = 8;
	
	
	/**
     * The unique identifier of the user.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="uid")
	private Integer userId;
	
	
	/**
     * The password for the user.
     */
	@Column(nullable=false)
	@Length(min=MIN_PASSWORD_LENGTH,message="Password must be at least "+MIN_PASSWORD_LENGTH+" characters long!")
	@NotBlank(message="Password field cannot be empty!")
	protected String password;
	
	
	/**
     * The role of the user. Can be admin,blogger or moderator
     */
	@NotBlank(message="Please assign a role")
	@Column(nullable=false)
	protected String role;
	
	
	//Constructors
	public User() {
		super();
	}
	
	public User(Integer userId,String password,String role) {
		this.userId = userId;
		this.password = password;
		this.role = role;
	}
	
	public User(String password,String role) {
		this.password = password;
		this.role = role;
	}

	
	//getters and setters
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", role=" + role + "]";
	}
}

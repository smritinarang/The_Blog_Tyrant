package thedailyscribbles.model;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;


/**
 * Class representing an Administrator.
 * 
 * @author The Daily Scribbles Team
 *
 */
@Entity
@Table(name="admin_details")
public class Admin extends User{
	
	
	/**
	 * unique username of the admin.
	 */
	@Column(name="username",nullable=false,unique=true)
	@Length(min=MIN_USERNAME_LENGTH,message="Username must be at least "+MIN_USERNAME_LENGTH+" characters long!")
	@NotBlank(message = "Username field cannot be empty!")
	private String adminName;
	
	
	/**
	 * contact information for the administrator.
	 */
	@Column(name="contact")
	private String adminContact;
	
	
	/**
	 * role of the admin.
	 */
	private static final String USER_ROLE = "admin";
	
	
	//Constructors
	public Admin() {
		super();
		this.role = USER_ROLE;
	}
	
	public Admin(String password,String adminName,String adminContact) {
		super(password,USER_ROLE);
		this.adminName = adminName;
		this.adminContact = adminContact;
	}

	
	//Getters and Setters
	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminContact() {
		return adminContact;
	}

	public void setAdminContact(String adminContact) {
		this.adminContact = adminContact;
	}

	@Override
	public String toString() {
		return "Admin [adminName=" + adminName + ", adminContact=" + adminContact + "]";
	}
	
}

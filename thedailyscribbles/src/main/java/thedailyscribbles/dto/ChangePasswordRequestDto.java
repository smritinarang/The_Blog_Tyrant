package thedailyscribbles.dto;

/**
 * A data transfer object class that is used to store information about a change password request.
 * 
 * @author Puthin Kumar
 */

public class ChangePasswordRequestDto {
	
	private String bloggerName;
	private String oldPassword;
	private String newPassword;
	
	public String getBloggerName() {
		return bloggerName;
	}
	public void setBloggerName(String bloggerName) {
		this.bloggerName = bloggerName;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}

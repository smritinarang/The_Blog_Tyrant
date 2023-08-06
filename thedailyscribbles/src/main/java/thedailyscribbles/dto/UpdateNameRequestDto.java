package thedailyscribbles.dto;


/**
 * A data transfer object class that is used to store information about a name change
 * request for a {@code User} profile.
 * 
 * @author Puthin Kumar
 */

public class UpdateNameRequestDto {
	
	private String oldName;
	private String newName;
	
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	
}

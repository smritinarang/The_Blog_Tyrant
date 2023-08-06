package thedailyscribbles.exception;

/**
 * This class extends the Exception class and is used to represent an error 
 * condition where the requested Moderator is not found.
 * 
 */

public class ModeratorNotFoundException extends Exception {
	private static final long serialVersionUID = 6L;

	public ModeratorNotFoundException(String msg) {
		super(msg);
	}
}

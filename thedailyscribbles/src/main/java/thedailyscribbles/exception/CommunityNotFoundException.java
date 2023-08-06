package thedailyscribbles.exception;

/**
 * This class extends the Exception class and is used to represent an error 
 * condition where the requested Community is not found.
 * 
 */

public class CommunityNotFoundException extends Exception{
	private static final long serialVersionUID = 4L;

	public CommunityNotFoundException(String msg) {
		super(msg);
	}
}

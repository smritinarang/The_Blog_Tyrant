package thedailyscribbles.exception;

/**
 * This class extends the Exception class and is used to represent an error 
 * condition where the requested Post is not found.
 * 
 */

public class PostNotFoundException extends Exception {
	private static final long serialVersionUID = 7L;

	public PostNotFoundException(String msg) {
		super(msg);
	}
}

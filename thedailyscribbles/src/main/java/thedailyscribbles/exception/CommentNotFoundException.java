package thedailyscribbles.exception;

/**
 * This class extends the Exception class and is used to represent an error 
 * condition where the requested Comment is not found.
 * 
 */

public class CommentNotFoundException extends Exception {
	private static final long serialVersionUID = 3L;

	public CommentNotFoundException(String msg) {
		super(msg);
	}
}

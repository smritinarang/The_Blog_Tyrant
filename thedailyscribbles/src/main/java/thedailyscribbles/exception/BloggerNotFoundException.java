package thedailyscribbles.exception;

/**
 * This class extends the Exception class and is used to represent an error 
 * condition where the requested Blogger is not found.
 * 
 */

public class BloggerNotFoundException extends Exception {
	private static final long serialVersionUID = 2L;

	public BloggerNotFoundException(String msg){
		super(msg);
	}
}

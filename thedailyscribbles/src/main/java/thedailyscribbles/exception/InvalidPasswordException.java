package thedailyscribbles.exception;

/**
 * This class extends the Exception class and is used to represent an error 
 * condition where the password provided is invalid.
 * 
 */

public class InvalidPasswordException extends Exception {

	private static final long serialVersionUID = 5L;

	public InvalidPasswordException(String msg){
		super(msg);
}
}

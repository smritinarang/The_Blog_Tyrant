package thedailyscribbles.exception;

public class BloggerAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 2L;

	public BloggerAlreadyExistsException(String msg){
		super(msg);
	}
}
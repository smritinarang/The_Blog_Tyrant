package thedailyscribbles.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class is used to handle exceptions for RESTful APIs in a centralized manner.
 * 
 * 
 */

@RestControllerAdvice
public class RestfulExceptionHandler {

	/**
	 * Handles exceptions of type {@link BloggerNotFoundException}.
	 * 
	 * @param e the {@link BloggerNotFoundException} to be handled
	 */
	@ExceptionHandler(BloggerNotFoundException.class)
	public ResponseEntity<String> handleBloggerNotFoundException(BloggerNotFoundException e) {
		String error = e.getMessage();
	    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	/**
	 * Handles exceptions of type {@link InvalidPasswordException}.
	 * 
	 * @param e the {@link InvalidPasswordException} to be handled
	 */
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException e) {
		String error = e.getMessage();
	    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	/**
	 * Handles exceptions of type {@link AdminNotFoundException}.
	 * 
	 * @param e the {@link AdminNotFoundException} to be handled
	 */
	@ExceptionHandler(AdminNotFoundException.class)
	public ResponseEntity<String> handleAdminNotFoundException(AdminNotFoundException e) {
		String error = e.getMessage();
	    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	/**
	 * Handles exceptions of type {@link ModeratorNotFoundException}.
	 * 
	 * @param e the {@link ModeratorNotFoundException} to be handled
	 */
	@ExceptionHandler(ModeratorNotFoundException.class)
	public ResponseEntity<String> handleModeratorNotFoundException(ModeratorNotFoundException e) {
		String error = e.getMessage();
	    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	/**
	 * Handles exceptions of type {@link PostNotFoundException}.
	 * 
	 * @param e the {@link PostNotFoundException} to be handled
	 */
	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<String> handlePostNotFoundException(PostNotFoundException e) {
		String error = e.getMessage();
	    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	/**
	 * Handles exceptions of type {@link CommunityNotFoundException}.
	 * 
	 * @param e the {@link CommunityNotFoundException} to be handled
	 */
	@ExceptionHandler(CommunityNotFoundException.class)
	public ResponseEntity<String> handleCommunityNotFoundException(CommunityNotFoundException e) {
		String error = e.getMessage();
	    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BloggerAlreadyExistsException.class)
	public ResponseEntity<String> handleBloggerAlreadyExistsException(BloggerAlreadyExistsException e) {
		String error = e.getMessage();
	    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}

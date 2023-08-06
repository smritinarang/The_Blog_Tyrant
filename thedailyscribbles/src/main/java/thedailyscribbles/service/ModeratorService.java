package thedailyscribbles.service;

import java.util.List;

import thedailyscribbles.exception.BloggerNotFoundException;
import thedailyscribbles.exception.InvalidPasswordException;
import thedailyscribbles.exception.ModeratorNotFoundException;
import thedailyscribbles.exception.PostNotFoundException;
import thedailyscribbles.model.Comment;
import thedailyscribbles.model.Moderator;
import thedailyscribbles.model.Post;


/**
*The {@link ModeratorService} interface provides the services related to the {@link Moderator} entity.
*/
public interface ModeratorService {
	
	/**
	 * Creates a new Moderator and saves it to the database.
	 *
	 * @param moderator : The Moderator object to be saved.
	 * @return The Moderator object that was saved to the database.
	 */
	public Moderator createModerator(Moderator moderator);
	
	
	/**
	 * Logs in a Moderator by verifying their username and password.
	 *
	 * @param username : The Moderator's username.
	 * @param password : The Moderator's password.
	 * @return The Moderator object if login was successful.
	 * @throws ModeratorNotFoundException If the Moderator with the given username was not found.
	 * @throws InvalidPasswordException If the password provided was incorrect.
	 */
	public Moderator login(String username, String password) 
			throws ModeratorNotFoundException,InvalidPasswordException;
	
	
	/**
	 * Finds a Moderator by their username.
	 *
	 * @param moderatorName : The username of the Moderator.
	 * @return The Moderator object if found.
	 * @throws ModeratorNotFoundException If the Moderator with the given username was not found.
	 */
	public Moderator findByName(String moderatorName) throws ModeratorNotFoundException;
	
	
	/**
	 * Finds a Moderator by their ID.
	 *
	 * @param moderatorId : The ID of the Moderator.
	 * @return The Moderator object if found.
	 * @throws ModeratorNotFoundException If the Moderator with the given ID was not found.
	 */
	public Moderator findById(Integer moderatorId) throws ModeratorNotFoundException;
	
	
	/**
	 * Shadows a Post so that it is not visible to bloggers.
	 *
	 * @param postId : The ID of the Post to be shadowed.
	 * @throws PostNotFoundException If the Post with the given ID was not found.
	 */
	public void shadowPost(Integer postId) throws PostNotFoundException;
	
//	public Post createPost(Post post);
//	
//	public Comment createComment(Post post,Comment comment);
//	
//	public Post upvotePost(Post post);
//	
//	public Post downvotePost(Post post);
	
	
	/**
	 * Returns a list of posts with a specified title.
	 *
	 * @param title : The title of the post to be searched.
	 * @return List of posts with the given title
	 */
	public List<Post> searchPost(String title);
	
	
	/**
	 * Returns a list of posts by a specific blogger.
	 *
	 * @param bloggerName : The username of the blogger.
	 * @return List of posts belonging to the specific blogger.
	 * @throws BloggerNotFoundException If the blogger with the given username was not found.
	 */
	public List<Post> searchPostByBlogger(String bloggerName) 
			throws BloggerNotFoundException;
}

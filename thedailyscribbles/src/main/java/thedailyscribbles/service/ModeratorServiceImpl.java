package thedailyscribbles.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thedailyscribbles.exception.BloggerNotFoundException;
import thedailyscribbles.exception.InvalidPasswordException;
import thedailyscribbles.exception.ModeratorNotFoundException;
import thedailyscribbles.exception.PostNotFoundException;
import thedailyscribbles.model.Blogger;
import thedailyscribbles.model.Comment;
import thedailyscribbles.model.Moderator;
import thedailyscribbles.model.Post;
import thedailyscribbles.repository.ModeratorRepository;

/**
*Service class that implements {@link ModeratorService}.
*/
@Service
public class ModeratorServiceImpl implements ModeratorService {
	
	private static final Logger logger = LogManager.getLogger(ModeratorServiceImpl.class);
	
	@Autowired
	ModeratorRepository modRepo;
	
	@Autowired
	PostService postService;
	
	@Autowired
	BloggerService bloggerService;
	
	
	/**
	 * Creates a new Moderator and saves it to the database.
	 *
	 * @param moderator : The Moderator object to be saved.
	 * @return The Moderator object that was saved to the database.
	 */
	@Override
	public Moderator createModerator(Moderator moderator) {
		logger.info("Creating moderator : {}",moderator);
		Moderator moderatorTemp =  modRepo.save(moderator);
		logger.info("Created moderator : {}",moderatorTemp);
		return moderatorTemp;
	}
	
	
	/**
	 * Logs in a Moderator by verifying their username and password.
	 *
	 * @param username : The Moderator's username.
	 * @param password : The Moderator's password.
	 * @return The Moderator object if login was successful.
	 * @throws ModeratorNotFoundException If the Moderator with the given username was not found.
	 * @throws InvalidPasswordException If the password provided was incorrect.
	 */
	@Override
	public Moderator login(String username, String password) 
			throws ModeratorNotFoundException,InvalidPasswordException {
		logger.info("Logging in moderator with username : {}",username);
		Moderator moderatorTemp = this.findByName(username);
		if(moderatorTemp.getPassword().equals(password)) {
			logger.info("Login successful for username : {}",username);
			return moderatorTemp;
		}
		else {
			logger.error("Login failed : Invalid Password for username {}",username);
			throw new InvalidPasswordException("Invalid Password");
		}
	}
	
	
	/**
	 * Finds a Moderator by their username.
	 *
	 * @param moderatorName : The username of the Moderator.
	 * @return The Moderator object if found.
	 * @throws ModeratorNotFoundException If the Moderator with the given username was not found.
	 */
	@Override
	public Moderator findByName(String moderatorName) throws ModeratorNotFoundException {
		logger.info("Finding moderator with name : {}",moderatorName);
		Optional<Moderator> moderatorTemp = modRepo.findByModeratorName(moderatorName);
		if(moderatorTemp.isPresent()) {
			logger.info("Moderator found with name : {}",moderatorName);
			return moderatorTemp.get();
		}
		else {
			logger.error("Moderator not found with name : {}",moderatorName);
			throw new ModeratorNotFoundException("No User found for username:"+moderatorName);
		}
	}
	
	
	/**
	 * Finds a Moderator by their ID.
	 *
	 * @param moderatorId : The ID of the Moderator.
	 * @return The Moderator object if found.
	 * @throws ModeratorNotFoundException If the Moderator with the given ID was not found.
	 */
	@Override
	public Moderator findById(Integer moderatorId) throws ModeratorNotFoundException {
		logger.info("Finding moderator with ID : {}",moderatorId);
		Optional<Moderator> moderatorTemp = modRepo.findById(moderatorId);
		if(moderatorTemp.isPresent()) {
			logger.info("Moderator found with ID : {}",moderatorId);
			return moderatorTemp.get();
		}
		else {
			logger.error("Moderator not found with ID : {}",moderatorId);
			throw new ModeratorNotFoundException("No moderator found");
		}
	}
	
	/**
	 * Shadows a Post so that it is not visible to bloggers.
	 *
	 * @param postId : The ID of the Post to be shadowed.
	 * @throws PostNotFoundException If the Post with the given ID was not found.
	 */
	@Override
	public void shadowPost(Integer postId) throws PostNotFoundException {
		logger.info("Shadowing post with id : {}",postId);
		Post postTemp = postService.findById(postId);
		postTemp.setShadow(!postTemp.isShadow());
		postService.updatePost(postTemp);
		logger.info("Post with id {} successfully shadowed",postId);
	}

	
//	@Override
//	public Post createPost(Post post) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Comment createComment(Post post, Comment comment) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Post upvotePost(Post post) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Post downvotePost(Post post) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
	/**
	 * Returns a list of posts with a specified title.
	 *
	 * @param title : The title of the post to be searched.
	 * @return List of posts with the given title
	 */
	@Override
	public List<Post> searchPost(String title) {
		return postService.searchPost(title);
	}

	/**
	 * Returns a list of posts by a specific blogger.
	 *
	 * @param bloggerName : The username of the blogger.
	 * @return List of posts belonging to the specific blogger.
	 * @throws BloggerNotFoundException If the blogger with the given username was not found.
	 */
	@Override
	public List<Post> searchPostByBlogger(String bloggerName) throws BloggerNotFoundException {
		logger.info("Searching posts by blogger name : {}",bloggerName);
		Blogger bloggerTemp = bloggerService.viewProfile(bloggerName);
		List<Post> postTemp =  bloggerTemp.getPost();
		logger.info("Found {} posts for blogger name {}",postTemp.size(),bloggerName);
		return postTemp;
	}

}

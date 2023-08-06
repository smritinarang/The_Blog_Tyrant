package thedailyscribbles.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thedailyscribbles.exception.BloggerAlreadyExistsException;
import thedailyscribbles.exception.BloggerNotFoundException;
import thedailyscribbles.exception.CommentNotFoundException;
import thedailyscribbles.exception.InvalidPasswordException;
import thedailyscribbles.exception.PostNotFoundException;
import thedailyscribbles.model.Blogger;
import thedailyscribbles.model.Comment;
import thedailyscribbles.model.Post;
import thedailyscribbles.repository.BloggerRepository;
import thedailyscribbles.utils.Constants;


/**
*Service class that implements {@link BloggerService}.
*@author Puthin Kumar, Xavier David
*/
@Service
public class BloggerServiceImpl implements BloggerService{
	
	private static final Logger logger = LogManager.getLogger(BloggerServiceImpl.class);
	
	@Autowired
	BloggerRepository bloggerRepo;
	
	@Autowired
	PostService postService;
	
	@Autowired
	CommentService commentService;
	
	
	
	/**
	 * Used to create a new Blogger account.
	 * It takes a Blogger object as an argument and saves it to the database.
	 * 
	 * @param blogger : The Blogger object that needs to be created
	 * @return The Blogger object that was created successfully
	 */
	@Override
	public Blogger createAccount(Blogger blogger) throws BloggerAlreadyExistsException {
		logger.info("Creating Blogger : {}",blogger);
		try {
			Blogger bloggerTemp = this.viewProfile(blogger.getBloggerName());
			throw new BloggerAlreadyExistsException("username already exists");
		}catch(BloggerNotFoundException e) {
			Blogger bloggerTemp = bloggerRepo.save(blogger);
			logger.info("Created Blogger : {}",bloggerTemp);
			return bloggerTemp;
		}

	}
	
	
	/**
	 * Used to update an existing Blogger account.
	 * It takes a Blogger object as an argument and saves it to the database.
	 * 
	 * @param blogger : The Blogger object that needs to be updated
	 * @return The Blogger object that was updated successfully
	 * @throws BloggerNotFoundException 
	 */
	public Blogger updateAccount(Blogger blogger) throws BloggerNotFoundException {
		logger.info("Updating Blogger : {}",blogger);
		Blogger bloggerUpdate = this.findById(blogger.getUserId());
		bloggerUpdate.setBloggerName(blogger.getBloggerName());
		bloggerUpdate.setProfilePicture(blogger.getProfilePicture());
		Blogger bloggerTemp = bloggerRepo.save(bloggerUpdate);
		logger.info("Updated Blogger : {}",bloggerTemp);
		return bloggerTemp;
	}

	
	/**
	 * Used to log in an existing Blogger.
	 * It takes the username and password as arguments and returns the 
	 * Blogger object if the username and password are correct.
	 * 
	 * @param bloggerName : The username of the Blogger
	 * @param password : The password of the Blogger
	 * @return The Blogger object if the login is successful
	 * @throws BloggerNotFoundException If the Blogger with the specified username is not found
	 * @throws InvalidPasswordException If the password provided is incorrect
	 */
	@Override
	public Blogger login(String bloggerName, String password) 
			throws BloggerNotFoundException,InvalidPasswordException{
		logger.info("Attempting to login Blogger with user name : {}",bloggerName);
		Blogger bloggerTemp = this.viewProfile(bloggerName);
		if(bloggerTemp.getPassword().equals(password)) {
			logger.info("Login Successful for Blogger : {}",bloggerTemp);
			return bloggerTemp;
		}
		else {
			logger.error("Login Failed : Invalid Password");
			throw new InvalidPasswordException(Constants.INVALID_PASSWORD);
		}
	}

	
	
	/**
	 * Used to change the name of an existing account
	 * 
	 * @param oldName : current name of the blogger
	 * @param newName : the new name to be set
	 * @return the updated Blogger object
	 * @throws BloggerNotFoundException if the blogger with the given name is not found
	 */
	@Override
	public Blogger updateName(String oldName,String newName) 
			throws BloggerNotFoundException {
		logger.info("Updating name for blogger : {}",oldName);
		Blogger bloggerTemp = this.viewProfile(oldName);
		bloggerTemp.setBloggerName(newName);
		logger.info("Name Updated. New name : {}",newName);
		return bloggerRepo.save(bloggerTemp);
	}
	
	
	/**
	 * Used to change the password of an existing account
	 * 
	 * @param bloggerName : name of the blogger whose password is to be changed
	 * @param oldPassword : current password of the blogger
	 * @param newPassword : the new password to be set
	 * @return the updated Blogger object
	 * @throws BloggerNotFoundException if the blogger with the given name is not found
	 * @throws InvalidPasswordException if the old password entered is incorrect
	 */
	public Blogger changePassword(String bloggerName,String oldPassword, String newPassword) 
			throws BloggerNotFoundException,InvalidPasswordException{
		logger.info("Attempting to change password for blogger : {}",bloggerName);
		Blogger bloggerTemp = this.viewProfile(bloggerName);
		if(bloggerTemp.getPassword().equals(oldPassword)) {
			bloggerTemp.setPassword(newPassword);
			logger.info("Password changed successfully");
			return bloggerTemp;
		}
		else {
			logger.error("Action Failed : Invalid Password");
			throw new InvalidPasswordException(Constants.INVALID_PASSWORD);
		}
	}

	
	
	
	/**
	*Deletes a profile of a blogger with the given blogger name and password.
	*@param bloggerName : the name of the blogger whose profile is to be deleted
	*@param password : the password of the blogger to verify the identity
	*@throws BloggerNotFoundException if no blogger is found with the given name
	*@throws InvalidPasswordException if the password provided does not match the blogger's password
	*/
	@Override
	public void deleteProfile(String bloggerName,String password) 
			throws BloggerNotFoundException,InvalidPasswordException {
		logger.info("Attempting to delete profile : {}",bloggerName);
		Blogger bloggerTemp = this.viewProfile(bloggerName);
		if(bloggerTemp.getPassword().equals(password)) {
			bloggerRepo.delete(bloggerTemp);
			logger.info("Successfully deleted profile : {}",bloggerName);
		}
		else {
			logger.error("Action Failed : Invalid Password");
			throw new InvalidPasswordException(Constants.INVALID_PASSWORD);
		}
	}

	
	/**
	*Views the profile of a blogger with the given username.
	*@param username : the name of the blogger whose profile is to be viewed
	*@return the Blogger object that represents the profile of the blogger
	*@throws BloggerNotFoundException if no blogger is found with the given name
	*/
	@Override
	public Blogger viewProfile(String username) throws BloggerNotFoundException {
		logger.info("Fetching blogger with name : {}",username);
		Optional<Blogger> bloggerTemp = bloggerRepo.findByBloggerName(username);
		if(bloggerTemp.isPresent()) {
			logger.info("Blogger found : {}",bloggerTemp.get());
			return bloggerTemp.get();
		}
		else {
			logger.error("Blogger not found with name : {}",username);
			throw new BloggerNotFoundException("No User found for username:"+username);
		}
	}
	
	
	/**
	*Finds a blogger by their ID.
	*@param bloggerId : The ID of the blogger to retrieve.
	*@return The Blogger object with the given ID.
	*@throws BloggerNotFoundException If no blogger is found with the given ID.
	*/
	@Override
	public Blogger findById(Integer bloggerId) throws BloggerNotFoundException{
		logger.info("Fetching blogger with ID : {}",bloggerId);
		Optional<Blogger> bloggerTemp = bloggerRepo.findById(bloggerId);
		if(bloggerTemp.isPresent()) {
			logger.info("Blogger found : {}",bloggerTemp.get());
			return bloggerTemp.get();
		}
		else {
			logger.error("Blogger not found with ID : {}",bloggerId);
			throw new BloggerNotFoundException("Blogger Not Found");
		}
	}

	
	/**
	*Creates a new post for a blogger.
	*@param bloggerName : The username of the blogger who will create the post.
	*@param post : The post object to be created.
	*@return The newly created post object.
	*@throws BloggerNotFoundException If the given bloggerName does not match any existing bloggers.
	*/
	@Override
	public Post createPost(String bloggerName,Post post) throws BloggerNotFoundException {
		logger.info("Creating Post for blogger : {}",bloggerName);
		Blogger bloggerTemp = this.viewProfile(bloggerName);
		List<Post> bloggerPosts = bloggerTemp.getPost();
		post.setBlogOwner(bloggerTemp);
		Post postTemp = postService.createPost(post);
		
		bloggerPosts.add(postTemp);
		bloggerTemp.setPost(bloggerPosts);
		this.updateAccount(bloggerTemp);
		logger.info("Created Post for blogger : {}",bloggerName);
		return postTemp;
	}

	
	/**
	*Adds a new comment to a post and updates the comment lists of the blogger and post.
	*@param bloggerId : The ID of the blogger who is making the comment.
	*@param postId : The ID of the post to which the comment will be added.
	*@param comment : The comment object to be added.
	*@return The newly added comment object.
	*@throws BloggerNotFoundException If no blogger is found with the given bloggerId.
	*@throws PostNotFoundException If no post is found with the given postId.
	*/
	@Override
	public Comment addComment(Integer bloggerId,Integer postId, Comment comment) 
			throws BloggerNotFoundException,PostNotFoundException {
		Post postTemp = postService.findById(postId);
		Blogger bloggerTemp = this.findById(bloggerId);
		logger.info("Creating Comment for blogger : {}",bloggerTemp);
		comment.setBlogger(bloggerTemp);
		comment.setPost(postTemp);
		comment.setCreatedDateTime(LocalDateTime.now());
		commentService.addComment(comment);
		
		List<Comment> postCommentList = postTemp.getComments();
		postCommentList.add(comment);
		postTemp.setComments(postCommentList);
		postService.updatePost(postTemp);
		
		List<Comment> bloggerCommentList = bloggerTemp.getComments();
		bloggerCommentList.add(comment);
		bloggerTemp.setComments(bloggerCommentList);
		this.updateAccount(bloggerTemp);
		
		logger.info("Created Comment for blogger : {}",bloggerTemp);
		return comment;
	}
	

	
	/**
	*Searches for posts with a given title.
	*@param title : The title to search for.
	*@return A list of posts that match the given title.
	*/
	@Override
	public List<Post> searchPost(String title) {
		logger.info("Searching for posts with title : {}",title);
		List<Post> postTemp = postService.searchPost(title);
		logger.info("Found {} posts",postTemp.size());
		return postTemp;
	}

	
	/**
	*Retrieves a list of posts by a given blogger.
	*@param bloggerName : The username of the blogger whose posts will be retrieved.
	*@return A list of posts by the given blogger.
	*@throws BloggerNotFoundException If the given bloggerName does not match any existing bloggers.
	*/
	@Override
	public List<Post> searchPostByBlogger(String bloggerName) throws BloggerNotFoundException {
		logger.info("Returning posts by blogger : {}",bloggerName);
		Blogger bloggerTemp = this.viewProfile(bloggerName);
		logger.info("Action Success");
		return bloggerTemp.getPost();
	}

	
	/**
	*This method allows a blogger to upvote a post.
	*@param bloggerId : The id of the blogger who is upvoting the post
	*@param postId : The id of the post that is being upvoted
	*@throws BloggerNotFoundException If the blogger with the given id is not found
	*@throws PostNotFoundException If the post with the given id is not found
	*/
	@Override
	public void upVotePost(Integer bloggerId,Integer postId) 
			throws BloggerNotFoundException,PostNotFoundException {
		logger.info("Upvoting Post with ID : {}",postId);
		Post postTemp = postService.findById(postId);
		Blogger bloggerTemp = this.findById(bloggerId);
		List<Post> upVoted = bloggerTemp.getUpVoted();
		upVoted.add(postTemp);
		bloggerTemp.setUpVoted(upVoted);
		postTemp.setVotes(postTemp.getVotes()+1);
		postService.updatePost(postTemp);
		this.updateAccount(bloggerTemp);
		logger.info("Upvoted Post with ID : {}",postId);
	}

	
	/**
	*This method allows a blogger to downvote a post.
	*@param bloggerId : The id of the blogger who is downvoting the post
	*@param postId : The id of the post that is being downvoted
	*@throws BloggerNotFoundException If the blogger with the given id is not found
	*@throws PostNotFoundException If the post with the given id is not found
	*/
	@Override
	public void downVotePost(Integer bloggerId,Integer postId) 
			throws BloggerNotFoundException,PostNotFoundException {
		logger.info("Downvoting Post with ID : {}",postId);
		Post postTemp = postService.findById(postId);
		Blogger bloggerTemp = this.findById(bloggerId);
		List<Post> downVoted = bloggerTemp.getDownVoted();
		downVoted.add(postTemp);
		bloggerTemp.setDownVoted(downVoted);
		postTemp.setVotes(postTemp.getVotes()-1);
		postService.updatePost(postTemp);
		this.updateAccount(bloggerTemp);
		logger.info("Downvoted Post with ID : {}",postId);
	}

	
	/**
	*This method allows a blogger to delete a comment.
	*@param bloggerId : The id of the blogger who is deleting the comment
	*@param commentId : The id of the comment that is being deleted
	*@throws BloggerNotFoundException If the blogger with the given id is not found
	*@throws CommentNotFoundException If the comment with the given id is not found 
	*		 or the blogger does not own the comment
	*/
	@Override
	public void deleteComment(Integer bloggerId, Integer commentId)
			throws BloggerNotFoundException, CommentNotFoundException {
		logger.info("Deleting Comment with Id : {}",commentId);
		Blogger bloggerTemp = this.findById(bloggerId);
		Comment commentTemp = commentService.findById(commentId);
		if(commentTemp.getBlogger().getUserId().equals(bloggerTemp.getUserId())) {
			commentService.deleteComment(commentTemp);
			logger.info("Deleted Comment with Id : {}",commentId);
		}
		else {
			logger.error("Action Failed : Blogger does not own this comment");
			throw new CommentNotFoundException("Blogger does not own the comment");
		}
	}

	
	/**
	*This method allows a blogger to edit a comment.
	*@param bloggerId : The id of the blogger who is editing the comment
	*@param comment : The comment object with the updated comment description
	*@return The updated comment object
	*@throws BloggerNotFoundException If the blogger with the given id is not found
	*@throws CommentNotFoundException If the comment with the given id is not found 
	*		 or the blogger does not own the comment
	*/
	@Override
	public Comment editComment(Integer bloggerId, Comment comment)
			throws BloggerNotFoundException, CommentNotFoundException {
		logger.info("Editing Comment : {}",comment);
		Blogger bloggerTemp = this.findById(bloggerId);
		Comment commentTemp = commentService.findById(comment.getCommentId());
		commentTemp.setCommentDescription(comment.getCommentDescription());
		if(commentTemp.getBlogger().getUserId().equals(bloggerTemp.getUserId())) {
			logger.info("Comment edited : {}",commentTemp);
			return commentService.updateComment(commentTemp);
		}
		else {
			logger.error("Action Failed : Blogger does not own this comment");
			throw new CommentNotFoundException("Blogger does not own the comment");
		}
	}

	
	/**
	*Deletes a post based on the given post id and the blogger id who is trying to delete the post.
	*@param bloggerId : The id of the blogger who is trying to delete the post
	*@param postId : The id of the post that needs to be deleted
	*@throws BloggerNotFoundException If the blogger with the given id is not found
	*@throws PostNotFoundException If the post with the given id is not found 
	*		 or the blogger does not own the post
	*/
	@Override
	public void deletePost(Integer bloggerId, Integer postId) 
			throws BloggerNotFoundException, PostNotFoundException {
		logger.info("Deleting Post with ID : {}",postId);
		Blogger bloggerTemp = this.findById(bloggerId);
		Post postTemp = postService.findById(postId);
		if(postTemp.getBlogOwner().getUserId().equals(bloggerTemp.getUserId())) {
			postService.deletePost(postTemp);
			logger.info("Deleted Post with ID : {}",postId);
		}
		else {
			logger.error("Action Failed : Blogger does not own this post");
			throw new PostNotFoundException("Blogger does not own the Post");
		}
	}

	
	/**
	*Edits an existing post based on the given post and the blogger id 
	*who is trying to edit the post.
	*@param bloggerId : The id of the blogger who is trying to edit the post
	*@param post : The post object that contains the new data for the post
	*@return The updated post object
	*@throws BloggerNotFoundException If the blogger with the given id is not found
	*@throws PostNotFoundException If the post with the given id is not found 
	*		 or the blogger does not own the post
	*/
	@Override
	public Post editPost(Integer bloggerId, Post post) 
			throws BloggerNotFoundException, PostNotFoundException {
		logger.info("Editing Post : {}",post);
		Blogger bloggerTemp = this.findById(bloggerId);
		Post postTemp = postService.findById(post.getPostId());
		postTemp.setTitle(post.getTitle());
		postTemp.setData(post.getData());
		postTemp.setCreatedDateTime(post.getCreatedDateTime());
		postTemp.setPostImage(post.getPostImage());
		if(postTemp.getBlogOwner().getUserId().equals(bloggerTemp.getUserId())) {
			logger.info("Edited Post : {}",post);
			return postService.updatePost(postTemp);
		}
		else {
			logger.error("Action Failed : Blogger does not own this post");
			throw new PostNotFoundException("Blogger does not own the Post");
		}
	}

	//Custom Query Example
	@Override
	public List<Blogger> findAllSortedByName() {
		return bloggerRepo.findAllSortedByName();
	}


	@Override
	public Post createPost(Integer bloggerId, Post post) throws BloggerNotFoundException {
		logger.info("Creating Post for blogger : {}",bloggerId);
		Blogger bloggerTemp = this.findById(bloggerId);
		List<Post> bloggerPosts = bloggerTemp.getPost();
		post.setBlogOwner(bloggerTemp);
		post.setCreatedDateTime(LocalDateTime.now());
		Post postTemp = postService.createPost(post);
		
		bloggerPosts.add(postTemp);
		bloggerTemp.setPost(bloggerPosts);
		this.updateAccount(bloggerTemp);
		logger.info("Created Post for blogger : {}",bloggerId);
		return postTemp;
	}


	@Override
	public List<Post> searchPostByBloggerId(Integer bloggerId) throws BloggerNotFoundException, PostNotFoundException {
		logger.info("Returning posts by blogger : {}",bloggerId);
		Blogger bloggerTemp = this.findById(bloggerId);
		logger.info("Action Success");
		return bloggerTemp.getPost();
	}
	
	@Override
	public void deletePost(Integer postId) 
			throws PostNotFoundException, BloggerNotFoundException {
		logger.info("Deleting Post with ID : {}",postId);
		Post postTemp = postService.findById(postId);
		postService.deletePost(postTemp);
		logger.info("Deleted Post with ID : {}",postId);
	}

}

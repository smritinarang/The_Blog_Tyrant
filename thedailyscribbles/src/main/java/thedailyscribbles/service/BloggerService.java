package thedailyscribbles.service;

import java.util.List;

import thedailyscribbles.exception.BloggerAlreadyExistsException;
import thedailyscribbles.exception.BloggerNotFoundException;
import thedailyscribbles.exception.CommentNotFoundException;
import thedailyscribbles.exception.InvalidPasswordException;
import thedailyscribbles.exception.PostNotFoundException;
import thedailyscribbles.model.Blogger;
import thedailyscribbles.model.Comment;
import thedailyscribbles.model.Post;


/**
*The {@link BloggerService} interface provides the services related to the {@link Blogger} entity.
*It declares methods for creating, updating, deleting and finding bloggers.
*@author Puthin Kumar, Xavier David
*/
public interface BloggerService {
	
	/**
	 * Used to create a new Blogger account.
	 * It takes a Blogger object as an argument and saves it to the database.
	 * 
	 * @param blogger : The Blogger object that needs to be created
	 * @return The Blogger object that was created successfully
	 */
	public Blogger createAccount(Blogger blogger) throws BloggerAlreadyExistsException;
	
	
	/**
	 * Used to update an existing Blogger account.
	 * It takes a Blogger object as an argument and saves it to the database.
	 * 
	 * @param blogger : The Blogger object that needs to be updated
	 * @return The Blogger object that was updated successfully
	 */
	public Blogger updateAccount(Blogger blogger) throws BloggerNotFoundException;
	
	
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
	public Blogger login(String bloggerName, String password) 
			throws BloggerNotFoundException,InvalidPasswordException;
	
	
	/**
	 * Used to change the name of an existing account
	 * 
	 * @param oldName : current name of the blogger
	 * @param newName : the new name to be set
	 * @return the updated Blogger object
	 * @throws BloggerNotFoundException if the blogger with the given name is not found
	 */
	public Blogger updateName(String oldName, String newName) throws BloggerNotFoundException;
	
	
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
	public Blogger changePassword(String bloggerName,String oldPassword, String newPassword) throws BloggerNotFoundException,InvalidPasswordException;
	
	
	/**
	*Deletes a profile of a blogger with the given blogger name and password.
	*@param bloggerName : the name of the blogger whose profile is to be deleted
	*@param password : the password of the blogger to verify the identity
	*@throws BloggerNotFoundException if no blogger is found with the given name
	*@throws InvalidPasswordException if the password provided does not match the blogger's password
	*/
	public void deleteProfile(String bloggerName,String password) throws BloggerNotFoundException,InvalidPasswordException;
	
	
	/**
	*Views the profile of a blogger with the given username.
	*@param username : the name of the blogger whose profile is to be viewed
	*@return the Blogger object that represents the profile of the blogger
	*@throws BloggerNotFoundException if no blogger is found with the given name
	*/
	public Blogger viewProfile(String username) throws BloggerNotFoundException;
	
	
	/**
	*Finds a blogger by their ID.
	*@param bloggerId : The ID of the blogger to retrieve.
	*@return The Blogger object with the given ID.
	*@throws BloggerNotFoundException If no blogger is found with the given ID.
	*/
	public Blogger findById(Integer bloggerId) throws BloggerNotFoundException;
	
	
	/**
	*Creates a new post for a blogger.
	*@param bloggerName : The username of the blogger who will create the post.
	*@param post : The post object to be created.
	*@return The newly created post object.
	*@throws BloggerNotFoundException If the given bloggerName does not match any existing bloggers.
	*/
	public Post createPost(String bloggerName,Post post) throws BloggerNotFoundException;
	
	public Post createPost(Integer bloggerId,Post post) throws BloggerNotFoundException;

	
	
	/**
	*Adds a new comment to a post and updates the comment lists of the blogger and post.
	*@param bloggerId : The ID of the blogger who is making the comment.
	*@param postId : The ID of the post to which the comment will be added.
	*@param comment : The comment object to be added.
	*@return The newly added comment object.
	*@throws BloggerNotFoundException If no blogger is found with the given bloggerId.
	*@throws PostNotFoundException If no post is found with the given postId.
	*/
	public Comment addComment(Integer blooggerId,Integer postId, Comment comment) throws BloggerNotFoundException,PostNotFoundException;
	
	
	/**
	*Searches for posts with a given title.
	*@param title : The title to search for.
	*@return A list of posts that match the given title.
	*/
	public List<Post> searchPost(String title);
	
	
	/**
	*Retrieves a list of posts by a given blogger.
	*@param bloggerName : The username of the blogger whose posts will be retrieved.
	*@return A list of posts by the given blogger.
	*@throws BloggerNotFoundException If the given bloggerName does not match any existing bloggers.
	*/
	public List<Post> searchPostByBlogger(String bloggerName) throws BloggerNotFoundException,PostNotFoundException;
	
	public List<Post> searchPostByBloggerId(Integer bloggerId) throws BloggerNotFoundException,PostNotFoundException;
	
	
	/**
	*This method allows a blogger to upvote a post.
	*@param bloggerId : The id of the blogger who is upvoting the post
	*@param postId : The id of the post that is being upvoted
	*@throws BloggerNotFoundException If the blogger with the given id is not found
	*@throws PostNotFoundException If the post with the given id is not found
	*/
	public void upVotePost(Integer bloggerId,Integer postId) throws BloggerNotFoundException,PostNotFoundException;
	
	
	/**
	*This method allows a blogger to downvote a post.
	*@param bloggerId : The id of the blogger who is downvoting the post
	*@param postId : The id of the post that is being downvoted
	*@throws BloggerNotFoundException If the blogger with the given id is not found
	*@throws PostNotFoundException If the post with the given id is not found
	*/
	public void downVotePost(Integer bloggerId,Integer postId) throws BloggerNotFoundException,PostNotFoundException;
	
	
	/**
	*This method allows a blogger to delete a comment.
	*@param bloggerId : The id of the blogger who is deleting the comment
	*@param commentId : The id of the comment that is being deleted
	*@throws BloggerNotFoundException If the blogger with the given id is not found
	*@throws CommentNotFoundException If the comment with the given id is not found 
	*		 or the blogger does not own the comment
	*/
	public void deleteComment(Integer bloggerId, Integer commentId) throws BloggerNotFoundException,CommentNotFoundException;
	
	
	/**
	*This method allows a blogger to edit a comment.
	*@param bloggerId : The id of the blogger who is editing the comment
	*@param comment : The comment object with the updated comment description
	*@return The updated comment object
	*@throws BloggerNotFoundException If the blogger with the given id is not found
	*@throws CommentNotFoundException If the comment with the given id is not found 
	*		 or the blogger does not own the comment
	*/
	public Comment editComment(Integer bloggerId,Comment comment) throws BloggerNotFoundException,CommentNotFoundException;
	
	
	/**
	*Deletes a post based on the given post id and the blogger id who is trying to delete the post.
	*@param bloggerId : The id of the blogger who is trying to delete the post
	*@param postId : The id of the post that needs to be deleted
	*@throws BloggerNotFoundException If the blogger with the given id is not found
	*@throws PostNotFoundException If the post with the given id is not found 
	*		 or the blogger does not own the post
	*/
	public void deletePost(Integer bloggerId,Integer postId) throws BloggerNotFoundException,PostNotFoundException;
	public void deletePost(Integer postId) throws BloggerNotFoundException, PostNotFoundException;
	
	
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
	public Post editPost(Integer bloggerId,Post post)  throws BloggerNotFoundException,PostNotFoundException;

	//Custom Query Example
	public List<Blogger> findAllSortedByName();
	
}

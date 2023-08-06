package thedailyscribbles.service;

import thedailyscribbles.exception.CommentNotFoundException;
import thedailyscribbles.model.Comment;

/**
*The {@link CommentService} interface provides the services related to the {@link Comment} entity.
*It declares methods for creating, updating, deleting and finding comments.
*/
public interface CommentService {
	
	/**
	*Adds a new comment to the database.
	*@param comment : the comment to be added
	*@return the comment added to the database
	*/
	public Comment addComment(Comment comment) ;
	
	
	/**
	*Updates the details of an existing comment in the database.
	*@param comment : the comment with updated details
	*@return the updated comment in the database
	*/
	public Comment updateComment(Comment comment);
	
	
	/**
	*Retrieves a comment from the database using its id.
	*@param commentId : the id of the comment to be retrieved
	*@return the comment with the specified id
	*@throws CommentNotFoundException if the comment with the specified id is not found
	*/
	public Comment findById(Integer commentId) throws CommentNotFoundException;
	
	
	/**
	*Deletes a comment from the database.
	*@param comment : the comment to be deleted
	*/
	public void deleteComment(Comment comment);
}

package thedailyscribbles.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thedailyscribbles.exception.CommentNotFoundException;
import thedailyscribbles.model.Comment;
import thedailyscribbles.repository.CommentRepository;


/**
*Service class that implements {@link CommentService}.
*/
@Service
public class CommentServiceImpl implements CommentService{

	private static final Logger logger = LogManager.getLogger(CommentServiceImpl.class);
	
	@Autowired
	CommentRepository commentRepo;
	
	
	/**
	*Adds a new comment to the database.
	*@param comment : the comment to be added
	*@return the comment added to the database
	*/
	@Override
	public Comment addComment(Comment comment) {
		logger.info("Adding Comment : {}",comment);
		Comment commentTemp = commentRepo.save(comment);
		logger.info("Added Comment : {}",commentTemp);
		return commentTemp;
	}
	
	
	/**
	*Updates the details of an existing comment in the database.
	*@param comment : the comment with updated details
	*@return the updated comment in the database
	*/
	@Override
	public Comment updateComment(Comment comment) {
		logger.info("Updating Comment : {}",comment);
		Comment commentTemp = commentRepo.save(comment);
		logger.info("Updated Comment : {}",commentTemp);
		return commentTemp;
	}
	
	
	/**
	*Deletes a comment from the database.
	*@param comment : the comment to be deleted
	*/
	@Override
	public void deleteComment(Comment comment) {
		logger.info("Deleting Comment : {}",comment);
		commentRepo.delete(comment);
		logger.info("Deleted Comment : {}",comment);	
	}
	
	
	/**
	*Retrieves a comment from the database using its id.
	*@param commentId : the id of the comment to be retrieved
	*@return the comment with the specified id
	*@throws CommentNotFoundException if the comment with the specified id is not found
	*/
	@Override
	public Comment findById(Integer commentId) throws CommentNotFoundException {
		logger.info("Finding comment with id : {}",commentId);
		Optional<Comment> commentTemp = commentRepo.findById(commentId);
		if(commentTemp.isPresent()) {
			logger.info("Found comment : {}",commentTemp.get());
			return commentTemp.get();
		}
		else {
			logger.error("Comment not found for id : {}",commentId);
			throw new CommentNotFoundException("Comment not Found");
		}
	}
	

}

package thedailyscribbles.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import thedailyscribbles.exception.PostNotFoundException;
import thedailyscribbles.model.Post;
import thedailyscribbles.repository.PostRepository;

/**
*Service class that implements {@link PostService}.
*@author Puthin Kumar, Xavier David
*/
@Service
public class PostServiceImpl implements PostService {

	private static final Logger logger = LogManager.getLogger(PostServiceImpl.class);
	
	@Autowired
	PostRepository postRepo;
	
	
	/**
	*Creates a new {@link Post}.
	*@param post : The post to be created.
	*@return The created post.
	 * @throws PostNotFoundException 
	*/
	@Override
	public Post createPost(Post post) {
		logger.info("Creating post : {}",post);
		Post postTemp = postRepo.save(post);
		logger.info("Created post : {}",postTemp);
		return postTemp;
	}
	
	/**
	*Updates an existing {@link Post}.
	*@param post : The post to be updated.
	*@return The updated post.
	*/
	@Override
	public Post updatePost(Post post) {
		logger.info("Updating post : {}",post);
		post.setCreatedDateTime(LocalDateTime.now());
		Post postTemp = postRepo.save(post);
		logger.info("Updated post : {}",postTemp);
		return postTemp;
	}

	/**
	*Deletes an existing {@link Post}.
	*@param post : The post to be deleted.
	 * @throws PostNotFoundException 
	*/
	@Override
	@Transactional
	public void deletePost(Post post) throws PostNotFoundException {
		logger.info("Deleting post : {}",post);
		postRepo.deleteAllUpvotes(post.getPostId());
		postRepo.delete(post);
		logger.info("Deleted post : {}",post);
	}
	
	/**
	*Searches for posts by their title.
	*@param title : The title of the post.
	*@return A list of posts that match the title.
	*/
	@Override
	public List<Post> searchPost(String title) {
		logger.info("Searching posts with title : {}",title);
		List<Post> postTemp = postRepo.findAllByTitle(title);
		logger.info("Found posts : {}",postTemp);
		return postTemp;
	}
	
	/**
	*Finds a post by its ID.
	*@param postId : The ID of the post.
	*@return The post with the specified ID.
	*@throws PostNotFoundException if the post with the specified ID is not found.
	*/
	@Override
	public Post findById(Integer postId) throws PostNotFoundException {
		logger.info("Finding post with id : {}",postId);
		Optional<Post> postTemp = postRepo.findById(postId);
		if(postTemp.isPresent()) {
			logger.info("Found post : {}",postTemp.get());
			return postTemp.get();
		}
		else {
			logger.error("Post not found for id : {}",postId);
			throw new PostNotFoundException("Post not found");
		}
	}

	@Override
	public List<Post> fetchAllPosts() {
		return postRepo.findAll();
	}

	@Override
	public List<Post> fetchPostsByType(String sortType) {
		if(sortType.compareTo("latest")==0) {
			return postRepo.fetchByDate();
		}
		else if(sortType.compareTo("hot")==0) {
			return postRepo.fetchByVotesAndDate();
		}
		else if(sortType.compareTo("best")==0) {
			return postRepo.fetchByVotes();
		}
		else {
			logger.error("Unable to determine sortType");
			return postRepo.findAll();
		}
	}
	
}

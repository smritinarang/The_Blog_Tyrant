package thedailyscribbles.service;

import java.util.List;

import thedailyscribbles.exception.PostNotFoundException;
import thedailyscribbles.model.Post;

/**
*The {@link PostService} interface provides the services related to the {@link Post} entity.
*It declares methods for creating, updating, deleting and finding posts.
*@author Puthin Kumar, Xavier David
*/
public interface PostService {
	
	/**
	*Creates a new {@link Post}.
	*@param post : The post to be created.
	*@return The created post.
	*/
	public Post createPost(Post post);
	
	
	/**
	*Updates an existing {@link Post}.
	*@param post : The post to be updated.
	*@return The updated post.
	*/
	public Post updatePost(Post post);
	
	
	/**
	*Deletes an existing {@link Post}.
	*@param post : The post to be deleted.
	*/
	public void deletePost(Post post) throws PostNotFoundException;
	
	
	/**
	*Searches for posts by their title.
	*@param title : The title of the post.
	*@return A list of posts that match the title.
	*/
	public List<Post> searchPost(String title);
	
	
	/**
	*Finds a post by its ID.
	*@param postId : The ID of the post.
	*@return The post with the specified ID.
	*@throws PostNotFoundException if the post with the specified ID is not found.
	*/
	public Post findById(Integer postId) throws PostNotFoundException;
	
	public List<Post> fetchAllPosts();
	
	public List<Post> fetchPostsByType(String sortType);
}

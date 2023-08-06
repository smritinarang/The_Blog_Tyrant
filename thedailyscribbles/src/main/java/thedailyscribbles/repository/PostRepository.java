package thedailyscribbles.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import thedailyscribbles.model.Post;

/**
*Repository interface for {@link Post} entity.
*/
public interface PostRepository extends JpaRepository<Post,Integer>{
	public List<Post> findAllByTitle(String title);
	
	@Modifying
	@Query(value= "DELETE FROM post_upvoted  WHERE product_id = :postId",  nativeQuery = true)
	public void deleteAllUpvotes(@Param("postId") Integer postId);
	
	@Query(value="SELECT e FROM Post e ORDER BY createdDateTime DESC")
	public List<Post> fetchByDate();
	
	@Query(value="SELECT e FROM Post e ORDER BY votes DESC")
	public List<Post> fetchByVotes();
	
	@Query(value="SELECT e FROM Post e ORDER BY votes DESC, createdDateTime DESC")
	public List<Post> fetchByVotesAndDate();
}

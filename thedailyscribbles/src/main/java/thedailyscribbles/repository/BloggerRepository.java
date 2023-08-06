package thedailyscribbles.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import thedailyscribbles.model.Blogger;

/**
*Repository interface for {@link Blogger} entity.
*/

public interface BloggerRepository extends JpaRepository<Blogger,Integer>{
	
	/**
	*Finds an {@link Blogger} by their username.
	*@param username The username of the blogger.
	*@return An {@link Optional} element of the {@link Blogger}.
	*/
	public Optional<Blogger> findByBloggerName(String username);

	
	// Custom Query Example
	@Query(value = "SELECT e FROM Blogger e ORDER BY bloggerName")
	public List<Blogger> findAllSortedByName();
}

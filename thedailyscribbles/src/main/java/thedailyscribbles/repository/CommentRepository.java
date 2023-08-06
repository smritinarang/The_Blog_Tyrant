package thedailyscribbles.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import thedailyscribbles.model.Comment;

/**
*Repository interface for {@link Comment} entity.
*/
public interface CommentRepository extends JpaRepository<Comment,Integer> {

}

package thedailyscribbles.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import thedailyscribbles.model.Community;

/**
*Repository interface for {@link Community} entity.
*/
public interface CommunityRepository extends JpaRepository<Community,Integer>{
	public Optional<Community> findByCommunityName(String communityName);
}

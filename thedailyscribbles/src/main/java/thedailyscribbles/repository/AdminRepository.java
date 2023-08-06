package thedailyscribbles.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import thedailyscribbles.model.Admin;

/**
*Repository interface for {@link Admin} entity.
*/

public interface AdminRepository extends JpaRepository<Admin,Integer>{
	
	/**
	*Finds an {@link Admin} by their username.
	*@param username The username of the admin.
	*@return An {@link Optional} element of the {@link Admin}.
	*/
	
	public Optional<Admin> findByAdminName(String username);
}

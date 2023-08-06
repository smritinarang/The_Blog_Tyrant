package thedailyscribbles.service;

import java.util.List;

import thedailyscribbles.exception.CommunityNotFoundException;
import thedailyscribbles.model.Community;


/**
*The {@link CommunityService} interface provides the services related to the {@link Community} entity.
*It declares methods for creating, updating, deleting and finding communities.
*/
public interface CommunityService {
	
	/**
	 * Creates a new community in the database
	 * 
	 * @param community : The community object to be created
	 * @return The created community object
	 */
	public Community createCommunity(Community community);
	
	
	/**
	 * Updates an existing community in the database
	 * 
	 * @param community : The community object to be updated
	 * @return The updated community object
	 */
	public Community updateCommunity(Community community);
	
	
	/**
	 * Deletes a community from the database
	 * 
	 * @param community : The community object to be deleted
	 */
	public void deleteCommunity(Community community);
	
	
	/**
	 * Returns a community by searching for its name
	 * 
	 * @param communityName : The name of the community
	 * @return The community object with the specified name
	 * @throws CommunityNotFoundException : When the community with the specified name is not found
	 */
	public Community viewCommunity(String communityName) throws CommunityNotFoundException;
	
	
	/**
	 * Returns a community by its ID
	 * 
	 * @param communityId : The ID of the community
	 * @return The community object with the specified ID
	 * @throws CommunityNotFoundException : When the community with the specified ID is not found
	 */
	public Community findById(Integer communityId) throws CommunityNotFoundException;
	
	
	/**
	 * Returns a list of all the communities from the database
	 * 
	 * @return - List of communities
	 */
	public List<Community> getAllCommunities();
}

package thedailyscribbles.service;

import java.util.List;

import thedailyscribbles.exception.AdminNotFoundException;
import thedailyscribbles.exception.BloggerNotFoundException;
import thedailyscribbles.exception.CommunityNotFoundException;
import thedailyscribbles.exception.InvalidPasswordException;
import thedailyscribbles.model.Admin;
import thedailyscribbles.model.Community;
import thedailyscribbles.model.Moderator;


/**
*The {@link AdminService} interface provides the services related to the {@link Admin} entity.
*/
public interface AdminService {
	
	
	/**
	*Method to log in the Admin user.
	*The method takes the username and password as inputs and checks if they are correct.
	*@param adminName : the username of the Admin.
	*@param password : the password entered for the Admin.
	*@return the profile of the Admin if the login is successful.
	*@throws AdminNotFoundException if the Admin with the given username is not found.
	*@throws InvalidPasswordException if the password entered is incorrect.
	*/
	public Admin login(String adminName, String password) 
			throws AdminNotFoundException,InvalidPasswordException;
	
	
	/**
	*Method to view the profile of an Admin.
	*@param adminName : the username of the Admin.
	*@return the profile of the Admin.
	*@throws AdminNotFoundException if the Admin with the given username is not found.
	*/
	public Admin viewProfile(String adminName) throws AdminNotFoundException;
	
	
	/**
	 * Create a new community.
	 * @param community : Community object to be created
	 * @return Community object that was created
	 */
	public Community createCommunity(Community community);
	
	
	/**
	 * Update an existing community.
	 * @param community : Community object to be updated
	 * @return Community object that was updated
	 */
	public Community updateCommunity(Community community);
	
	
	/**
	 * Delete a community. 
	 * @param communityId : ID of the community to be deleted
	 * @throws CommunityNotFoundException if the community with the specified ID cannot be found
	 */
	public void deleteCommunity(Integer communityId) throws CommunityNotFoundException;
	
	
	/**
	 * View a community.
	 * @param communityName : name of the community to be viewed
	 * @return Community object with the specified name
	 * @throws CommunityNotFoundException if the community with the specified name cannot be found
	 */
	public Community viewCommunity(String communityName) throws CommunityNotFoundException;
	
	
	/**
	*Retrieves a list of all communities.
	*@return a list of all communities
	*/
	public List<Community> getAllCommunities();
	
	
	/**
	*Creates a moderator by promoting a blogger with a specified ID.
	*@param bloggerId : the ID of the blogger to be promoted as a moderator
	*@return the newly created moderator account
	*@throws BloggerNotFoundException if the specified blogger is not found
	*/
	public Moderator createModerator(Integer bloggerId) throws BloggerNotFoundException;
	
	
	/**
	*Adds a list of bloggers to a community with a specified ID.
	*@param communityId : the ID of the community to add bloggers to
	*@param bloggerIdList : a list of IDs of the bloggers to be added
	*@return a message indicating that the operation was successful
	*@throws CommunityNotFoundException if the specified community is not found
	*@throws BloggerNotFoundException if one of the specified bloggers is not found
	*/
	public String addBloggersToCommunity(Integer communityId, List<Integer> bloggerIdList)
			throws BloggerNotFoundException,CommunityNotFoundException;
	
	
	/**
	*Removes a list of bloggers from a community with a specified ID.
	*@param communityId : the ID of the community to remove bloggers from
	*@param bloggerIdList : a list of IDs of the bloggers to be removed
	*@return a message indicating that the operation was successful
	*@throws BloggerNotFoundException if one of the specified bloggers is not found
	*@throws CommunityNotFoundException if the specified community is not found
	*/
	public String removeBloggersFromCommunity(Integer communityId,List<Integer> bloggerIdList) 
			throws BloggerNotFoundException,CommunityNotFoundException;
}

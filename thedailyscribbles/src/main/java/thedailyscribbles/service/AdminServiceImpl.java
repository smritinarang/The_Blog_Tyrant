package thedailyscribbles.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thedailyscribbles.exception.AdminNotFoundException;
import thedailyscribbles.exception.BloggerNotFoundException;
import thedailyscribbles.exception.CommunityNotFoundException;
import thedailyscribbles.exception.InvalidPasswordException;
import thedailyscribbles.model.Admin;
import thedailyscribbles.model.Blogger;
import thedailyscribbles.model.Community;
import thedailyscribbles.model.Moderator;
import thedailyscribbles.repository.AdminRepository;


/**
*Service class that implements {@link AdminService}.
*/
@Service
public class AdminServiceImpl implements AdminService {
	
	private static final Logger logger = LogManager.getLogger(AdminServiceImpl.class);
	
	@Autowired
	AdminRepository adminRepo;
	
	@Autowired
	CommunityService communityService;
	
	@Autowired
	ModeratorService modService;
	
	@Autowired
	BloggerService bloggerService;
	
	
	
	/**
	*Method to log in the Admin user.
	*The method takes the username and password as inputs and checks if they are correct.
	*@param adminName : the username of the Admin.
	*@param password : the password entered for the Admin.
	*@return the profile of the Admin if the login is successful.
	*@throws AdminNotFoundException if the Admin with the given username is not found.
	*@throws InvalidPasswordException if the password entered is incorrect.
	*/
	@Override
	public Admin login(String adminName, String password) throws AdminNotFoundException,InvalidPasswordException {
		logger.info("Attempting to log in admin");
		Admin adminTemp = this.viewProfile(adminName);
		if(adminTemp.getPassword().equals(password)) {
			logger.info("Login Successful");
			return adminTemp;
		}
		else {
			logger.error("Login Failed");
			throw new InvalidPasswordException("Invalid Password");
		}
	}
	

	
	/**
	*Method to view the profile of an Admin.
	*@param adminName : the username of the Admin.
	*@return the profile of the Admin.
	*@throws AdminNotFoundException if the Admin with the given username is not found.
	*/
	@Override
	public Admin viewProfile(String adminName) throws AdminNotFoundException {
		logger.info("Viewing Admin Profile");
		Optional<Admin> adminTemp = adminRepo.findByAdminName(adminName);
		if(adminTemp.isPresent()) {
			logger.info("Action Successful");
			return adminTemp.get();
		}
		else {
			logger.error("Action Failed");
			throw new AdminNotFoundException("No User found for username:"+adminName);
		}
	}


	/**
	 * Create a new community.
	 * @param community : Community object to be created
	 * @return Community object that was created
	 */
	@Override
	public Community createCommunity(Community community) {
		logger.info("Admin Creating Community : {}",community);
		return communityService.createCommunity(community);
	}

	
	/**
	 * Update an existing community.
	 * @param community : Community object to be updated
	 * @return Community object that was updated
	 */
	@Override
	public Community updateCommunity(Community community) {
		logger.info("Admin Updating Community : {}",community);
		return communityService.updateCommunity(community);
	}

	
	/**
	 * Delete a community. 
	 * @param communityId : ID of the community to be deleted
	 * @throws CommunityNotFoundException if the community with the specified ID cannot be found
	 */
	@Override
	public void deleteCommunity(Integer communityId) throws CommunityNotFoundException {
		logger.info("Admin Creating Community with ID : {}",communityId);
		Community communityTemp = communityService.findById(communityId);
		communityService.deleteCommunity(communityTemp);
	}

	
	/**
	 * View a community.
	 * @param communityName : name of the community to be viewed
	 * @return Community object with the specified name
	 * @throws CommunityNotFoundException if the community with the specified name cannot be found
	 */
	@Override
	public Community viewCommunity(String communityName) throws CommunityNotFoundException {
		logger.info("Admin viewing Community with name : {}",communityName);
		return communityService.viewCommunity(communityName);
	}

	
	/**
	*Retrieves a list of all communities.
	*@return a list of all communities
	*/
	@Override
	public List<Community> getAllCommunities() {
		logger.info("Admin fetching all Communities");
		return communityService.getAllCommunities();
	}


	/**
	*Creates a moderator by promoting a blogger with a specified ID.
	*@param bloggerId : the ID of the blogger to be promoted as a moderator
	*@return the newly created moderator account
	*@throws BloggerNotFoundException if the specified blogger is not found
	*/
	@Override
	public Moderator createModerator(Integer bloggerId) throws BloggerNotFoundException {
		logger.info("Admin promoting Blogger with ID : {}",bloggerId);
		Blogger bloggerTemp = bloggerService.findById(bloggerId);
		Moderator modTemp = new Moderator();
		modTemp.setModeratorName(bloggerTemp.getBloggerName());
		modTemp.setPassword(bloggerTemp.getPassword());
		modTemp.setBloggerId(bloggerId);
		return modService.createModerator(modTemp);
	}


	/**
	*Adds a list of bloggers to a community with a specified ID.
	*@param communityId : the ID of the community to add bloggers to
	*@param bloggerIdList : a list of IDs of the bloggers to be added
	*@return a message indicating that the operation was successful
	*@throws CommunityNotFoundException if the specified community is not found
	*@throws BloggerNotFoundException if one of the specified bloggers is not found
	*/
	@Override
	public String addBloggersToCommunity(Integer communityId, List<Integer> bloggerIdList) 
			throws CommunityNotFoundException,BloggerNotFoundException {
		logger.info("Admin adding bloggers to Community with ID: {}",communityId);
		Community communityTemp = communityService.findById(communityId);
		List<Blogger> bloggerList = communityTemp.getMembers();
		for(Integer id : bloggerIdList) {
			Blogger bloggerTemp = bloggerService.findById(id);
			List<Community> communityList = bloggerTemp.getCommunities();
			communityList.add(communityTemp);
			bloggerTemp.setCommunities(communityList);
			bloggerService.updateAccount(bloggerTemp);
			bloggerList.add(bloggerTemp);
		}
		communityTemp.setMembers(bloggerList);
		communityService.updateCommunity(communityTemp);
		logger.info("Successful");
		return "Added successfully";
	}


	/**
	*Removes a list of bloggers from a community with a specified ID.
	*@param communityId : the ID of the community to remove bloggers from
	*@param bloggerIdList : a list of IDs of the bloggers to be removed
	*@return a message indicating that the operation was successful
	*@throws BloggerNotFoundException if one of the specified bloggers is not found
	*@throws CommunityNotFoundException if the specified community is not found
	*/
	@Override
	public String removeBloggersFromCommunity(Integer communityId, List<Integer> bloggerIdList)
			throws BloggerNotFoundException, CommunityNotFoundException {
		logger.info("Admin removing bloggers from Community with ID: {}",communityId);
		Community communityTemp = communityService.findById(communityId);
		List<Blogger> bloggerList = communityTemp.getMembers();
		for(Integer id : bloggerIdList) {
			Blogger bloggerTemp = bloggerService.findById(id);
			List<Community> communityList = bloggerTemp.getCommunities();
			if(communityList.contains(communityTemp)) {
				communityList.remove(communityTemp);
			}
			else {
				throw new CommunityNotFoundException("Blogger is not Part of community");
			}
			bloggerTemp.setCommunities(communityList);
			bloggerService.updateAccount(bloggerTemp);
			if(bloggerList.contains(bloggerTemp)) {
				bloggerList.remove(bloggerTemp);
			}
		}
		communityTemp.setMembers(bloggerList);
		communityService.updateCommunity(communityTemp);
		logger.info("Successful");
		return "Removed successfully";
	}
	
}

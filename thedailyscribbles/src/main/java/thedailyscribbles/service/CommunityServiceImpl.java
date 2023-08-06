package thedailyscribbles.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thedailyscribbles.exception.CommunityNotFoundException;
import thedailyscribbles.model.Community;
import thedailyscribbles.repository.CommunityRepository;


/**
*Service class that implements {@link CommunityService}.
*/
@Service
public class CommunityServiceImpl implements CommunityService{

	private static final Logger logger = LogManager.getLogger(CommunityServiceImpl.class);
	
	@Autowired
	CommunityRepository communityRepo;
	
	
	/**
	 * Creates a new community in the database
	 * 
	 * @param community : The community object to be created
	 * @return The created community object
	 */
	@Override
	public Community createCommunity(Community community) {
		logger.info("Creating Community : {}",community);
		Community communityTemp = communityRepo.save(community);
		logger.info("Created Community : {}",communityTemp);
		return communityTemp;
	}

	
	/**
	 * Updates an existing community in the database
	 * 
	 * @param community : The community object to be updated
	 * @return The updated community object
	 */
	@Override
	public Community updateCommunity(Community community) {
		logger.info("Updating Community : {}",community);
		Optional<Community> communityUpdate = communityRepo.findById(community.getCommunityId());
		Community communityTemp = communityUpdate.get();
		communityTemp.setCommunityName(community.getCommunityName());
		communityTemp.setCommunityDescription(community.getCommunityDescription());
		communityTemp = communityRepo.save(communityTemp);
		logger.info("Updated Community : {}",communityTemp);
		return communityTemp;
	}
	
	
	/**
	 * Deletes a community from the database
	 * 
	 * @param community : The community object to be deleted
	 */
	@Override
	public void deleteCommunity(Community community) {
		logger.info("Deleting Community : {}",community);
		communityRepo.delete(community);
		logger.info("Deleted Community : {}",community);
	}

	
	/**
	 * Returns a community by searching for its name
	 * 
	 * @param communityName : The name of the community
	 * @return The community object with the specified name
	 * @throws CommunityNotFoundException : When the community with the specified name is not found
	 */
	@Override
	public Community viewCommunity(String communityName) throws CommunityNotFoundException {
		logger.info("Finding Community with name : {}",communityName);
		Optional<Community> communityTemp = communityRepo.findByCommunityName(communityName);
		if(communityTemp.isPresent()) {
			logger.info("Found Community : {}",communityTemp.get());
			return communityTemp.get();
		}
		else {
			logger.error("No community found for name : {}",communityName);
			throw new CommunityNotFoundException("Community Not Found");
		}
	}
	
	
	/**
	 * Returns a community by its ID
	 * 
	 * @param communityId : The ID of the community
	 * @return The community object with the specified ID
	 * @throws CommunityNotFoundException : When the community with the specified ID is not found
	 */
	@Override
	public Community findById(Integer communityId) throws CommunityNotFoundException {
		logger.info("Finding Community with ID : {}",communityId);
		Optional<Community> communityTemp = communityRepo.findById(communityId);
		if(communityTemp.isPresent()) {
			logger.info("Found Community : {}",communityTemp.get());
			return communityTemp.get();
		}
		else {
			logger.error("No community found for ID : {}",communityId);
			throw new CommunityNotFoundException("Community Not Found");
		}
	}

	
	/**
	 * Returns a list of all the communities from the database
	 * 
	 * @return - List of communities
	 */
	@Override
	public List<Community> getAllCommunities() {
		logger.info("Fetching all the communities");
		List<Community> communityTemp = communityRepo.findAll();
		logger.info("Found {} communities",communityTemp.size());
		return communityTemp;
	}

}

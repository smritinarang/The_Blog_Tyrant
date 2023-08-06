package thedailyscribbles.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import thedailyscribbles.exception.CommunityNotFoundException;
import thedailyscribbles.model.Community;
import thedailyscribbles.repository.CommunityRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CommunityServiceTest {

    @Mock
    private CommunityRepository communityRepo;

    @InjectMocks
    private CommunityServiceImpl communityService;

    @Test
    void testCreateCommunity() {
        // Create a sample community object
        Community community = new Community();
        community.setCommunityName("Test Community");
        community.setCommunityDescription("This is a test community");

        // Configure the mock repository to return the community object when save() is called
        when(communityRepo.save(community)).thenReturn(community);

        // Call the service method
        Community createdCommunity = communityService.createCommunity(community);

        // Verify that the save() method was called once with the community object as argument
        verify(communityRepo, times(1)).save(community);

        // Verify that the returned community object matches the original community object
        assertEquals(community, createdCommunity);
    }

    @Test
    void testUpdateCommunity() {
        // Create a sample community object
        Community community = new Community();
        community.setCommunityId(1);
        community.setCommunityName("Test Community");
        community.setCommunityDescription("This is a test community");

        // Configure the mock repository to return the community object when save() is called
        when(communityRepo.save(community)).thenReturn(community);

        // Call the service method
        Community updatedCommunity = communityService.updateCommunity(community);

        // Verify that the save() method was called once with the community object as argument
        verify(communityRepo, times(1)).save(community);

        // Verify that the returned community object matches the original community object
        assertEquals(community, updatedCommunity);
    }
    
    @Test
    void testDeleteCommunity() {
        // Create a sample community object
        Community community = new Community();
        community.setCommunityId(1);
        community.setCommunityName("Test Community");
        community.setCommunityDescription("This is a test community");

        // Call the service method
        communityService.deleteCommunity(community);

        // Verify that the delete() method was called once with the community object as argument
        verify(communityRepo, times(1)).delete(community);
    }

    @Test
    void testViewCommunity() throws CommunityNotFoundException {
        // Create a sample community object
        Community community = new Community();
        community.setCommunityId(1);
        community.setCommunityName("Test Community");
        community.setCommunityDescription("This is a test community");

        // Configure the mock repository to return the community object when findByCommunityName() is called
        when(communityRepo.findByCommunityName("Test Community")).thenReturn(Optional.of(community));

        // Call the service method
        Community viewedCommunity = communityService.viewCommunity("Test Community");

        // Verify that the findByCommunityName() method was called once with the correct argument
        verify(communityRepo, times(1)).findByCommunityName("Test Community");

        // Verify that the returned community object matches the original community object
        assertEquals(community, viewedCommunity);
    }

    @Test
    void testViewCommunityNotFound() {
        // Configure the mock repository to return an empty Optional when findByCommunityName() is called
        when(communityRepo.findByCommunityName("Test Community")).thenReturn(Optional.empty());

        // Call the service method and verify that a CommunityNotFoundException is thrown
        assertThrows(CommunityNotFoundException.class, () -> {
            communityService.viewCommunity("Test Community");
        });

        // Verify that the findByCommunityName() method was called once with the correct argument
        verify(communityRepo, times(1)).findByCommunityName("Test Community");
    }
    
    @Test
    void testFindById() throws CommunityNotFoundException {
        // Create a sample community object
        Community community = new Community();
        community.setCommunityId(1);
        community.setCommunityName("Test Community");
        community.setCommunityDescription("This is a test community");

        // Configure the mock repository to return the community object when findById() is called
        when(communityRepo.findById(1)).thenReturn(Optional.of(community));

        // Call the service method
        Community foundCommunity = communityService.findById(1);

        // Verify that the findById() method was called once with the correct argument
        verify(communityRepo, times(1)).findById(1);

        // Verify that the returned community object matches the original community object
        assertEquals(community, foundCommunity);
    }

    @Test
    void testFindByIdNotFound() {
        // Configure the mock repository to return an empty Optional when findById() is called
        when(communityRepo.findById(1)).thenReturn(Optional.empty());

        // Call the service method and verify that a CommunityNotFoundException is thrown
        assertThrows(CommunityNotFoundException.class, () -> {
            communityService.findById(1);
        });

        // Verify that the findById() method was called once with the correct argument
        verify(communityRepo, times(1)).findById(1);
    }

    @Test
    void testGetAllCommunities() {
        // Create two sample community objects
        Community community1 = new Community();
        community1.setCommunityId(1);
        community1.setCommunityName("Test Community 1");
        community1.setCommunityDescription("This is a test community 1");

        Community community2 = new Community();
        community2.setCommunityId(2);
        community2.setCommunityName("Test Community 2");
        community2.setCommunityDescription("This is test community 2");

        List<Community> communities = new ArrayList<>();
        communities.add(community1);
        communities.add(community2);

        // Configure the mock repository to return the list of communities when findAll() is called
        when(communityRepo.findAll()).thenReturn(communities);

        // Call the service method
        List<Community> foundCommunities = communityService.getAllCommunities();

        // Verify that the findAll() method was called once
        verify(communityRepo, times(1)).findAll();

        // Verify that the returned list of communities contains the original community objects
        assertEquals(communities, foundCommunities);
    }
}


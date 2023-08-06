package thedailyscribbles.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import thedailyscribbles.exception.InvalidPasswordException;
import thedailyscribbles.exception.ModeratorNotFoundException;
import thedailyscribbles.exception.PostNotFoundException;
import thedailyscribbles.model.Moderator;
import thedailyscribbles.model.Post;
import thedailyscribbles.repository.ModeratorRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ModeratorServiceTest {

	@Mock
	private ModeratorRepository modRepo;
	
	@Mock
	private PostService postService;
	
	@InjectMocks
	private ModeratorServiceImpl moderatorService;
	
	private Moderator moderator;
	
	@BeforeEach
	void setUp() {
		moderator = new Moderator("testpassword", "testmoderator");
		moderator.setUserId(1);
	}

	@Test
	void testCreateModerator() {
		// Arrange
		Moderator moderator = new Moderator();
		moderator.setModeratorName("Test Moderator");
		moderator.setPassword("password");
		when(modRepo.save(moderator)).thenReturn(moderator);
		
		// Act
		Moderator result = moderatorService.createModerator(moderator);
		
		// Assert
		assertEquals(moderator, result);
		verify(modRepo).save(moderator);
		verifyNoMoreInteractions(modRepo);
	}
	
	@Test
	void testLogin_WithValidCredentials_ShouldReturnModerator() throws ModeratorNotFoundException, InvalidPasswordException {
		// Arrange
		String username = "Test Moderator";
		String password = "password";
		Moderator moderator = new Moderator();
		moderator.setModeratorName(username);
		moderator.setPassword(password);
		when(modRepo.findByModeratorName(username)).thenReturn(Optional.of(moderator));
		
		// Act
		Moderator result = moderatorService.login(username, password);
		
		// Assert
		assertEquals(moderator, result);
		verify(modRepo).findByModeratorName(username);
		verifyNoMoreInteractions(modRepo);
	}
	
	@Test
	void testLogin_WithInvalidCredentials_ShouldThrowInvalidPasswordException() throws ModeratorNotFoundException {
		// Arrange
		String username = "Test Moderator";
		String password = "password";
		Moderator moderator = new Moderator();
		moderator.setModeratorName(username);
		moderator.setPassword("wrongpassword");
		when(modRepo.findByModeratorName(username)).thenReturn(Optional.of(moderator));
		
		// Act and Assert
		InvalidPasswordException exception = assertThrows(InvalidPasswordException.class, () -> moderatorService.login(username, password));
		assertEquals("Invalid Password", exception.getMessage());
		verify(modRepo).findByModeratorName(username);
		verifyNoMoreInteractions(modRepo);
	}
	
	@Test
	void testFindByName_WithValidName_ShouldReturnModerator() throws ModeratorNotFoundException {
		// Arrange
		String username = "Test Moderator";
		Moderator moderator = new Moderator();
		moderator.setModeratorName(username);
		when(modRepo.findByModeratorName(username)).thenReturn(Optional.of(moderator));
		
		// Act
		Moderator result = moderatorService.findByName(username);
		
		// Assert
		assertEquals(moderator, result);
		verify(modRepo).findByModeratorName(username);
		verifyNoMoreInteractions(modRepo);
	}
	
	@Test
	void testFindByName_WithInvalidName_ShouldThrowModeratorNotFoundException() {
		// Arrange
		String username = "Test Moderator";
		when(modRepo.findByModeratorName(username)).thenReturn(Optional.empty());
		
		// Act and Assert
		ModeratorNotFoundException exception = assertThrows(ModeratorNotFoundException.class, () -> moderatorService.findByName(username));
		assertEquals("No User found for username:"+username, exception.getMessage());
		verify(modRepo).findByModeratorName(username);
		verifyNoMoreInteractions(modRepo);
	}
	
	@Test
	void testFindById() throws ModeratorNotFoundException {
		when(modRepo.findById(moderator.getUserId())).thenReturn(Optional.of(moderator));
		Moderator foundModerator = moderatorService.findById(moderator.getUserId());
		assertEquals(moderator, foundModerator);
		verify(modRepo).findById(moderator.getUserId());
	}

	@Test
	void testFindById_WithInvalidId_ShouldThrowModeratorNotFoundException() {
		when(modRepo.findById(moderator.getUserId())).thenReturn(Optional.empty());
		assertThrows(ModeratorNotFoundException.class, () -> {
			moderatorService.findById(moderator.getUserId());
		});
		verify(modRepo).findById(moderator.getUserId());
	    }
	
	@Test
	void testShadowPost() throws PostNotFoundException {
		Post post = new Post();
		post.setPostId(1);
		when(postService.findById(1)).thenReturn(post);
		
		moderatorService.shadowPost(1);
		
		assertTrue(post.isShadow());
		verify(postService, times(1)).updatePost(post);
	}
	
	@Test
	void testShadowPostThrowsPostNotFoundException() throws PostNotFoundException {
		when(postService.findById(1)).thenThrow(new PostNotFoundException("Post not found"));
		
		Exception exception = assertThrows(PostNotFoundException.class, () -> moderatorService.shadowPost(1));
		
		assertEquals("Post not found", exception.getMessage());
	}
	
	
}
	


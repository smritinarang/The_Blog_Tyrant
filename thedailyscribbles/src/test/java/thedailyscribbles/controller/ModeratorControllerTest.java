package thedailyscribbles.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import thedailyscribbles.dto.LoginRequestDto;
import thedailyscribbles.model.Moderator;
import thedailyscribbles.model.Post;
import thedailyscribbles.service.ModeratorService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ModeratorControllerTest {
	@InjectMocks
	private ModeratorController controller;
	
	@Mock
	private ModeratorService moderatorService;

	private MockMvc mockMvc;
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	void testLogin() throws Exception {
		LoginRequestDto login = new LoginRequestDto();
		login.setUserName("username");
		login.setPassword("password");
		Moderator expectedModerator = new Moderator();
		expectedModerator.setModeratorName("username");
		expectedModerator.setPassword("password");
		
		when(moderatorService.login("username", "password")).thenReturn(expectedModerator);
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		mockMvc.perform(post("/mod/login")
				.content(mapper.writeValueAsString(login))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.moderatorName").value(expectedModerator.getModeratorName()))
				.andExpect(jsonPath("$.password").value(expectedModerator.getPassword()));
	}
	
	@Test
	void testShadowPost() throws Exception {
		Integer postId = 15;
		Post expectedPost = new Post();
		expectedPost.setTitle("post title");
		expectedPost.setShadow(false);
		
		doNothing().when(moderatorService).shadowPost(postId);

	    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

	    mockMvc.perform(get("/mod/shadowpost")
	        .param("postid", postId.toString()))
	        .andExpect(status().isOk());
	}
	
}

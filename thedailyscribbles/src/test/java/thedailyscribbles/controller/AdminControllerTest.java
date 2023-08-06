package thedailyscribbles.controller;

import static org.mockito.Mockito.when;
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
import thedailyscribbles.model.Admin;
import thedailyscribbles.service.AdminService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
	
	@InjectMocks
	private AdminController controller;
	
	@Mock
	private AdminService adminService;

	private MockMvc mockMvc;
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	void testLogin() throws Exception {
		LoginRequestDto login = new LoginRequestDto();
		login.setUserName("username");
		login.setPassword("password");
		Admin expectedAdmin = new Admin();
		expectedAdmin.setAdminName("username");
		expectedAdmin.setPassword("password");
		
		when(adminService.login("username", "password")).thenReturn(expectedAdmin);
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		mockMvc.perform(post("/admin/login")
				.content(mapper.writeValueAsString(login))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.adminName").value(expectedAdmin.getAdminName()))
				.andExpect(jsonPath("$.password").value(expectedAdmin.getPassword()));
	}
}

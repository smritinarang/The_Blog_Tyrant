package thedailyscribbles.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import thedailyscribbles.dto.LoginRequestDto;
import thedailyscribbles.exception.InvalidPasswordException;
import thedailyscribbles.exception.ModeratorNotFoundException;
import thedailyscribbles.exception.PostNotFoundException;
import thedailyscribbles.model.Moderator;
import thedailyscribbles.service.ModeratorService;

@RestController
public class ModeratorController {
	
	@Autowired
	ModeratorService moderatorService;
	
	@PostMapping("mod/login")
	public ResponseEntity<Moderator> login(@RequestBody LoginRequestDto login) throws ModeratorNotFoundException,InvalidPasswordException{
		Moderator moderatorResponse = moderatorService.login(login.getUserName(), login.getPassword());
		return ResponseEntity.ok(moderatorResponse);
	}
	
	@GetMapping("mod/shadowpost")
	public void shadowPost(@RequestParam("postid") Integer postId) throws PostNotFoundException {
		moderatorService.shadowPost(postId);
	}
	
}


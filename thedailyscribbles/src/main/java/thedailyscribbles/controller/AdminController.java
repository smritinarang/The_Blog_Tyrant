package thedailyscribbles.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import thedailyscribbles.dto.IntegerListDto;
import thedailyscribbles.dto.LoginRequestDto;
import thedailyscribbles.exception.AdminNotFoundException;
import thedailyscribbles.exception.BloggerNotFoundException;
import thedailyscribbles.exception.CommunityNotFoundException;
import thedailyscribbles.exception.InvalidPasswordException;
import thedailyscribbles.model.Admin;
import thedailyscribbles.model.Community;
import thedailyscribbles.model.Moderator;
import thedailyscribbles.service.AdminService;

@RestController
public class AdminController {
	@Autowired
	AdminService adminService;
	
	@PostMapping("/admin/login")
	public Admin login(@RequestBody LoginRequestDto login) throws AdminNotFoundException,InvalidPasswordException{
		return adminService.login(login.getUserName(), login.getPassword());
	}
	
	@PostMapping("admin/createcommunity")
	public Community createCommunity(@RequestBody Community community) {
		return adminService.createCommunity(community);
	}
	
	@GetMapping("admin/deletecommunity")
	public void deleteCommunity(@RequestParam("communityid") Integer communityId) throws CommunityNotFoundException {
		adminService.deleteCommunity(communityId);
	}
	
	@PostMapping("admin/updatecommunity")
	public Community updateCommunity(@RequestBody Community community) {
		return adminService.updateCommunity(community);
	}
	
	@GetMapping("admin/community")
	public Community viewCommunity(@RequestParam("communityname") String communityName) throws CommunityNotFoundException {
		return adminService.viewCommunity(communityName);
	}
	
	@GetMapping("admin/viewallcommunities")
	public List<Community> viewAllCommunities(){
		return adminService.getAllCommunities();
	}
	
	@GetMapping("admin/assignmoderator")
	public Moderator createModerator(@RequestParam("bloggerid") Integer bloggerId)throws BloggerNotFoundException {
		return adminService.createModerator(bloggerId);
	}
	
	@PostMapping("admin/{communityid}/addbloggers")
	public String addBloggersToCommunity(@PathVariable("communityid") Integer communityId,@RequestBody IntegerListDto integerList) throws BloggerNotFoundException,CommunityNotFoundException {
		return adminService.addBloggersToCommunity(communityId, integerList.getIntegerList());
	}
	
	@PostMapping("admin/{communityid}/removebloggers")
	public String removeBloggersFromCommunity(@PathVariable("communityid") Integer communityId,@RequestBody IntegerListDto integerList) throws BloggerNotFoundException,CommunityNotFoundException {
		return adminService.removeBloggersFromCommunity(communityId, integerList.getIntegerList());
	}
}

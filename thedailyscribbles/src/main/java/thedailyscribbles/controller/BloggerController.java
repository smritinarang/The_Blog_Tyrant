package thedailyscribbles.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import thedailyscribbles.dto.ChangePasswordRequestDto;
import thedailyscribbles.dto.LoginRequestDto;
import thedailyscribbles.dto.UpdateNameRequestDto;
import thedailyscribbles.exception.BloggerAlreadyExistsException;
import thedailyscribbles.exception.BloggerNotFoundException;
import thedailyscribbles.exception.CommentNotFoundException;
import thedailyscribbles.exception.InvalidPasswordException;
import thedailyscribbles.exception.PostNotFoundException;
import thedailyscribbles.model.Blogger;
import thedailyscribbles.model.Comment;
import thedailyscribbles.model.Community;
import thedailyscribbles.model.Post;
import thedailyscribbles.service.BloggerService;
import thedailyscribbles.service.PostService;

@RestController
public class BloggerController {
	@Autowired
	BloggerService bloggerService;
	
	@Autowired
	PostService postService;
	
	@PostMapping("/register")
	public ResponseEntity<Blogger> createBlogger(@RequestBody Blogger blogger) throws BloggerAlreadyExistsException {
			Blogger bloggerTemp = bloggerService.createAccount(blogger);
			return ResponseEntity.ok(bloggerTemp);
	}
	
	@PostMapping("/login")
	public Blogger login(@RequestBody LoginRequestDto login) throws BloggerNotFoundException,InvalidPasswordException{
		return bloggerService.login(login.getUserName(), login.getPassword());
	}
	
	@PostMapping("/updatename")
	public Blogger updateName(@RequestBody UpdateNameRequestDto updateNameReq) throws BloggerNotFoundException{
		return bloggerService.updateName(updateNameReq.getOldName(), updateNameReq.getNewName());
	}
	
	@PostMapping("/changepassword")
	public Blogger changePassword(@RequestBody ChangePasswordRequestDto changePasswordReq) throws BloggerNotFoundException,InvalidPasswordException{
		return bloggerService.changePassword(changePasswordReq.getBloggerName(), changePasswordReq.getOldPassword(), changePasswordReq.getNewPassword());
	}
	
	@PostMapping("/deleteaccount")
	public void deleteAccount(@RequestBody LoginRequestDto deleteReq) throws BloggerNotFoundException,InvalidPasswordException{
		bloggerService.deleteProfile(deleteReq.getUserName(), deleteReq.getPassword());
	}
	
//	@PostMapping("{name}/createpost")
//	public Post createPost(@PathVariable("name") String bloggerName,@RequestBody Post post) throws BloggerNotFoundException {
//		return bloggerService.createPost(bloggerName,post);
//	}
	
	@PostMapping("{id}/createpost")
	public Post createPostByBloggerId(@PathVariable("id") Integer bloggerId,@RequestBody Post post) throws BloggerNotFoundException {
		return bloggerService.createPost(bloggerId,post);
	}
	
	@GetMapping("/searchpost")
	public List<Post> searchPost(@RequestParam("title") String title){
		return bloggerService.searchPost(title);
	}
	
	
	@PostMapping("{id}/{postId}/addcomment")
	public Comment addComment(@PathVariable("id") Integer bloggerId,@PathVariable("postId") Integer postId,@RequestBody Comment comment) throws BloggerNotFoundException,PostNotFoundException {
		return bloggerService.addComment(bloggerId, postId, comment);
	}
	
	@GetMapping("/bloggerposts")
	public List<Post> findPostsByBloggerName(@RequestParam("bloggername") String bloggerName) throws BloggerNotFoundException,PostNotFoundException{
		return bloggerService.searchPostByBlogger(bloggerName);
	}
	
	@GetMapping("{id}/{postId}/voteup")
	public void votePostUp(@PathVariable("id") Integer bloggerId, @PathVariable("postId") Integer postId) throws BloggerNotFoundException,PostNotFoundException {
		bloggerService.upVotePost(bloggerId, postId);
	}
	
	@GetMapping("{id}/{postId}/votedown")
	public void votePostDown(@PathVariable("id") Integer bloggerId, @PathVariable("postId") Integer postId) throws BloggerNotFoundException,PostNotFoundException {
		bloggerService.downVotePost(bloggerId, postId);
	}
	
	@GetMapping("{bloggerid}/{commentid}/deletecomment")
	public void deleteComment(@PathVariable("bloggerid") Integer bloggerId, @PathVariable("commentid") Integer commentId) throws BloggerNotFoundException,CommentNotFoundException {
		bloggerService.deleteComment(bloggerId, commentId);
	}
	
	@PostMapping("{bloggerid}/{commentid}/editcomment")
	public Comment editComment(@PathVariable("bloggerid") Integer bloggerId, @PathVariable("commentid") Integer commentId,@RequestBody Comment comment) throws BloggerNotFoundException,CommentNotFoundException {
		return bloggerService.editComment(bloggerId, comment);
	}
	
//	@GetMapping("{bloggerid}/{postid}/deletepost")
//	public void deletePost(@PathVariable("bloggerid") Integer bloggerId, @PathVariable("postid") Integer postId) throws BloggerNotFoundException,PostNotFoundException {
//		bloggerService.deletePost(bloggerId, postId);
//	}
	
	@PostMapping("{bloggerid}/{postid}/editpost")
	public Post editPost(@PathVariable("bloggerid") Integer bloggerId, @PathVariable("postid") Integer postId,@RequestBody Post post) throws BloggerNotFoundException,PostNotFoundException {
		return bloggerService.editPost(bloggerId, post);
	}
	
	@PostMapping("sortbyname")
	public List<Blogger> sortedByName(){
		return bloggerService.findAllSortedByName();
	}
	
	@GetMapping("/fetchAllPosts")
	public List<Post> fetchAllPosts(){
		return postService.fetchAllPosts();
	}
	
	@GetMapping("/fetchallposts")
	public List<Post> fetchPostsByType(@RequestParam("sortby") String sortType) throws NumberFormatException, BloggerNotFoundException, PostNotFoundException{
		if((sortType.compareTo("latest")!=0) && 
				(sortType.compareTo("hot")!=0) && (sortType.compareTo("best")!=0) ) {
			return bloggerService.searchPostByBloggerId(Integer.parseInt(sortType));
		}
		return postService.fetchPostsByType(sortType);
	}
	
	@GetMapping("/findBlogOwner/{postId}")
	public Blogger findBloggerByPostId(@PathVariable("postId") Integer postId) throws PostNotFoundException {
		Post post = postService.findById(postId);
		return post.getBlogOwner();
	}
	
	@GetMapping("/findPostById/{postId}")
	public Post findPostById(@PathVariable("postId") Integer postId) throws PostNotFoundException {
		Post post = postService.findById(postId);
		return post;
	}
	
	@GetMapping("/fetchPostComments/{postId}")
	public List<Comment> fetchPostComments(@PathVariable("postId") Integer postId) throws PostNotFoundException {
		Post post = postService.findById(postId);
		return post.getComments();
	}
	
	@GetMapping("/bloggerposts/{id}")
	public List<Post> findPostsByBloggerName(@PathVariable("id") Integer bloggerId) throws BloggerNotFoundException,PostNotFoundException{
		return bloggerService.searchPostByBloggerId(bloggerId);
	}
	
	@PostMapping("/updateprofile")
	public Blogger updateprofile(@RequestBody Blogger blogger) throws BloggerNotFoundException{
		return bloggerService.updateAccount(blogger);
	}
	
	@GetMapping("/{postid}/deletepost")
	public void deletePost(@PathVariable("postid") Integer postId) throws PostNotFoundException, BloggerNotFoundException {
		bloggerService.deletePost(postId);
	}
	
	@GetMapping("/findblogger/{id}")
	public Blogger findBloggerById(@PathVariable("id") Integer bloggerId) throws BloggerNotFoundException{
		return bloggerService.findById(bloggerId);
	}
	
	@GetMapping("/fetchallbloggers")
	public List<Blogger> fetchAllBloggers() {
		return bloggerService.findAllSortedByName();
	}
	
	@GetMapping("/getcommunities")
	public List<Community> getCommunities(@RequestParam("bloggerid") Integer bloggerId) throws BloggerNotFoundException {
		Blogger bloggerTemp = bloggerService.findById(bloggerId);
		return bloggerTemp.getCommunities();
	}
	
}

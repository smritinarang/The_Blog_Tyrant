package thedailyscribbles.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import thedailyscribbles.exception.CommentNotFoundException;
import thedailyscribbles.model.Comment;
import thedailyscribbles.repository.CommentRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CommentServiceTest {
	
	@InjectMocks
	CommentServiceImpl commentService;
	
	@Mock
	CommentRepository commentRepo;
	
	private Comment comment;
	
	@BeforeEach
	public void setup() {
		comment = new Comment();
		comment.setCommentId(23);
		comment.setBlogger(null);
		comment.setCommentDescription("random comment");
	}
	
	@Test
	void testAddComment() {
		when(commentRepo.save(comment)).thenReturn(comment);
		Comment resultComment = commentService.addComment(comment);
		assertEquals(resultComment,comment);
	}
	
	@Test
	void testUpdateComment() {
		when(commentRepo.save(comment)).thenReturn(comment);
		Comment resultComment = commentService.addComment(comment);
		assertEquals(resultComment,comment);
	}
	
	@Test
	void testFindById() throws CommentNotFoundException {
		when(commentRepo.findById(anyInt())).thenReturn(Optional.of(comment));
		Comment resultComment = commentService.findById(comment.getCommentId());
		assertEquals(resultComment,comment);
	}
	

	@Test
	void testFindById_WithInvalidId_ShouldThrowCommentNotFoundException() {
		// Arrange
		Integer commentId = 2;
		when(commentRepo.findById(commentId)).thenReturn(Optional.empty());
		
		// Act and Assert
		CommentNotFoundException exception = assertThrows(CommentNotFoundException.class, () -> commentService.findById(commentId));
		assertEquals("Comment not Found", exception.getMessage());
	}
	
	@Test
	void testDeleteComment() {
		commentService.deleteComment(comment);
		verify(commentRepo).delete(comment);
	}
}

package thedailyscribbles.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import thedailyscribbles.exception.PostNotFoundException;
import thedailyscribbles.model.Post;
import thedailyscribbles.repository.PostRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PostServiceTest {
    @Mock
    private PostRepository postRepo;

    @InjectMocks
    private PostServiceImpl postService;

    private Post post;

    @BeforeEach
    void setUp() {
        post = new Post();
        post.setPostId(1);
        post.setTitle("Test Post");
        post.setData("This is a test post.");
    }

    @Test
    void testCreatePost() {
        when(postRepo.save(post)).thenReturn(post);

        Post createdPost = postService.createPost(post);

        verify(postRepo, times(1)).save(post);
        assertEquals(post, createdPost);
    }
    
    @Test
    void testUpdatePost() {
        when(postRepo.save(post)).thenReturn(post);

        Post updatedPost = postService.updatePost(post);

        verify(postRepo, times(1)).save(post);
        assertEquals(post, updatedPost);
    }
    

    @Test
    void testSearchPost() {
        when(postRepo.findAllByTitle(post.getTitle())).thenReturn(Arrays.asList(post));

        List<Post> foundPosts = postService.searchPost(post.getTitle());

        verify(postRepo, times(1)).findAllByTitle(post.getTitle());
        assertEquals(Arrays.asList(post), foundPosts);
    }

    @Test
    void testFindByIdWhenPostExists() throws PostNotFoundException {
        when(postRepo.findById(post.getPostId())).thenReturn(Optional.of(post));

        Post foundPost = postService.findById(Math.toIntExact(post.getPostId()));

        verify(postRepo, times(1)).findById(post.getPostId());
        assertEquals(post, foundPost);
    }

    @Test
    void testFindByIdWhenPostDoesNotExist() {
        when(postRepo.findById(post.getPostId())).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.findById(Math.toIntExact(post.getPostId())));

        verify(postRepo, times(1)).findById(post.getPostId());
    }


}

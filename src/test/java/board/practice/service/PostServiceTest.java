package board.practice.service;

import board.practice.domain.Post;
import board.practice.domain.User;
import board.practice.repository.PostRepository;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired private PostService postService;
    @Autowired private UserService userService;

    @Test
    public void 게시글_작성() throws Exception {
        //given
        clearDatabase();
        User user = createUser();
        Post post = createPost(user);

        //when
        List<Post> posts = postService.findAll();

        //then
        assertEquals("포스트가 1개여야 한다", 1, posts.size());
        assertEquals("제목은 title이어야 한다", "title", posts.get(0).getTitle());
        assertEquals("유저는 testUser이어야 한다", "testUser", posts.get(0).getAuthor().getUsername());
        
    }

    private void clearDatabase() {
        postService.findAll().stream().forEach(post -> postService.deletePost(post.getId(), post.getAuthor().getUsername()));
    }

    private Post createPost(User user) {
        Post post = new Post();
        post.setAuthor(user);

        return postService.createPost(user.getId(), "title", "content");
    }

    private User createUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("1234");
        userService.join(user);
        return user;
    }
    
    @Test
    public void 게시글_조회() throws Exception {
        //given
        clearDatabase();
        User user = createUser();
        Post post = createPost(user);
        
        //when
        Post post1 = postService.findOne(post.getId());

        //then
        assertEquals("조회한 제목이 title이어야 합니다.", "title", post1.getTitle());
    }

    @Test
    public void 게시글_수정() throws Exception {
        //given
        clearDatabase();
        User user = createUser();
        Post post = createPost(user);

        //when
        postService.updatePost(post.getId(), "newTitle", "newContent");

        //then
        Post findPost = postService.findOne(post.getId());
        assertEquals("글 제목이 newTitle로 바뀌어야 합니다.", "newTitle", findPost.getTitle());
    }

    @Test
    public void 게시글_삭제() throws Exception {
        //given
        clearDatabase();
        User user = createUser();
        Post post = createPost(user);

        //when
        postService.deletePost(post.getId(), post.getAuthor().getUsername());

        //then
        Post findPost = postService.findOne(post.getId());
        assertNull("삭제된 포스트는 Null이어야 합니다.", findPost);
    }
}
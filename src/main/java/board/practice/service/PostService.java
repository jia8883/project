package board.practice.service;

import board.practice.domain.Post;
import board.practice.domain.User;
import board.practice.repository.PostRepository;
import board.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post createPost(Long userId, String title, String content) {

        User author = userRepository.findOne(userId);
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(author);

        return postRepository.save(post);
    }

    public void updatePost(Long postId, String title, String content) {
        // Post post = postRepository.findOne(postId);
        Post post = postRepository.findOne(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시글을 찾을 수 없습니다."));

        post.setTitle(title);
        post.setContent(content);
    }

    public void deletePost(Long postId, String username) {
        // Post post = postRepository.findOne(postId);
        Post post = postRepository.findOne(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시글을 찾을 수 없습니다."));

        if (!post.getAuthor().getUsername().equals(username)) {
            throw new RuntimeException("삭제할 권한이 없습니다.");
        }
        postRepository.delete(post);
    }


    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findOne(Long postId) {
        // return postRepository.findOne(postId);
        // 아래처럼 PostServiceTest의 삭제 메서드를 위해 null반환이 가능하게 함
        return postRepository.findOne(postId).orElse(null);
    }
}

package board.practice.api;

import board.practice.domain.Post;
import board.practice.repository.PostRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostRepository postRepository;

    @GetMapping("/api/posts")
    public List<PostDto> posts() {
        List<Post> posts = postRepository.findAllWithUser();
        List<PostDto> result = posts.stream().map(p -> new PostDto(p))
                .collect(Collectors.toList());
        return result;
    }

    @Data
    static class PostDto {

        private Long postId;
        private String title;
        private String content;
        private String authorName;
        private LocalDateTime created_at;

        public PostDto(Post post) {
            this.postId = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.authorName = post.getAuthor().getUsername();
            this.created_at = post.getCreated_at();
        }
    }


}

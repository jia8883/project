package board.practice;

import board.practice.domain.Post;
import board.practice.domain.User;
import board.practice.service.PostService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final PostService postService;

        public void dbInit() {
            User user = new User();
            user.setUsername("Kim");
            user.setPassword("password");
            em.persist(user);

            Post post1 = postService.createPost(user.getId(), "title1", "content1");
            Post post2 = postService.createPost(user.getId(), "title2", "content2");

            em.persist(post1);
            em.persist(post2);
        }
    }
}

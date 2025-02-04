package board.practice.repository;

import board.practice.domain.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    public Optional<Post> findOne(Long post_id) {
        // return em.find(Post.class, post_id);
        // PostServiceTest의 삭제메서드 때문에 아래로 리턴값을 바꿈
        return Optional.ofNullable(em.find(Post.class, post_id));
    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class).getResultList();
    }

    public void delete(Post post) {
        em.remove(post);
    }

    public List<Post> findAllWithUser() {
        return em.createQuery("select p from Post p" +
                " join fetch p.author a", Post.class)
                .getResultList();
    }
}

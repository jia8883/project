package board.practice.service;

import board.practice.domain.User;
import board.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입
    @Transactional
    public Long join(User user) {

        validationDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validationDuplicateUser(User user) {
        List<User> findUser = userRepository.findByUsername(user.getUsername());
        if (!findUser.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 수정
    @Transactional
    public void update(Long id, String username) {
        User user = userRepository.findOne(id);
        user.setUsername(username);
    }

    // 회원 조회
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }
}

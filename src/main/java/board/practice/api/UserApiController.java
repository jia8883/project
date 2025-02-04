package board.practice.api;

import board.practice.domain.User;
import board.practice.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    // 유저 등록
    @PostMapping("/api/users")
    public CreateUserResponse saveUser(@RequestBody @Valid CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        Long userId = userService.join(user);
        return new CreateUserResponse(userId);

    }

    @Data
    @AllArgsConstructor
    static class CreateUserResponse {
        private Long id;
    }

    @Data
    static class CreateUserRequest {
        private String username;
        private String password;
    }

    // 이름 수정
    @PutMapping("/api/users/{id}")
    public UpdateUserResponse updateUser(@PathVariable("id") Long id,
                                         @RequestBody @Valid UpdateUserRequest request) {
        userService.update(id, request.getUsername());
        User findUser = userService.findOne(id);
        return new UpdateUserResponse(findUser.getId(), findUser.getUsername());
    }

    @Data
    @AllArgsConstructor
    static class UpdateUserResponse {
        private Long id;
        private String username;
    }

    @Data
    static class UpdateUserRequest {
        private String username;

    }

    // 전체 조회
    @GetMapping("/api/users")
    public Result users() {
        List<User> findUsers = userService.findAll();
        List<UserDto> collect = findUsers.stream()
                .map(u -> new UserDto(u.getUsername()))
                .collect(Collectors.toList());
        return new Result<>(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class UserDto {
        private String username;
    }
}

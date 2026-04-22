package com.example.timetabledevelop.user.service;

import com.example.timetabledevelop.user.dto.CreateUserRequest;
import com.example.timetabledevelop.user.dto.CreateUserResponse;
import com.example.timetabledevelop.user.entity.User;
import com.example.timetabledevelop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;

    /**
     * User 생성
     * @param request
     * @return CreateUserResponse
     */
    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        User user = new User(
                request.getUserName(),
                request.getEmail(),
                request.getUserPass()
        );
        User savedUser = userRepository.save(user);
        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getUserPass()
        );
    }


}

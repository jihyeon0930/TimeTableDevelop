package com.example.timetabledevelop.user.service;

import com.example.timetabledevelop.user.dto.*;
import com.example.timetabledevelop.user.entity.User;
import com.example.timetabledevelop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * User 생성
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
                savedUser.getUserPass(),
                savedUser.getCreatedAt()
        );
    }

    /**
     * User 전체 조회
     */
    @Transactional(readOnly = true)
    public List<GetUserAllResponse> getAll() {
        List<User> users = userRepository.findAll();
        List<GetUserAllResponse> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(new GetUserAllResponse(
                    user.getId(),
                    user.getUserName(),
                    user.getEmail()
            ));
        }
        return dtos;
    }

    /**
     * User 단건 조회
     */
    @Transactional(readOnly = true)
    public GetUserResponse getOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저 입니다.")
        );
        return new GetUserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getUserPass(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    /**
     * User 수정
     */
    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저 입니다.")
        );
        user.update(
                request.getUserName(),
                request.getEmail(),
                request.getUserPass()
        );
        return new UpdateUserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getUserPass(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    /**
     * User 삭제
     */
    @Transactional
    public void delete(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalStateException("존재하지 않는 유저 입니다.");
        }
        userRepository.deleteById(userId);
    }
}
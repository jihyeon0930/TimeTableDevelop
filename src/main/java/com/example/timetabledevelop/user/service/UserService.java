package com.example.timetabledevelop.user.service;

import com.example.timetabledevelop.global.config.PasswordEncoder;
import com.example.timetabledevelop.global.exception.DuplicateException;
import com.example.timetabledevelop.global.exception.NotFoundException;
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

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /**
     * User 생성
     *
     * @param request
     * @return CreateUserResponse
     */
    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getUserPass());

        User user = new User(
                request.getUserName(),
                request.getEmail(),
                encodedPassword
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
     *
     * @return 전체 User 리스트 (userPass 제외)
     */
    @Transactional(readOnly = true)
    public List<GetUserAllResponse> getAll() {
        List<User> users = userRepository.findAll();
        List<GetUserAllResponse> dtos = new ArrayList<>();
        for (User user : users) {
            GetUserAllResponse dto = new GetUserAllResponse(
                    user.getId(),
                    user.getUserName(),
                    user.getEmail()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * User 단건 조회
     *
     * @param userId
     * @return 단건 User 정보
     */
    @Transactional(readOnly = true)
    public GetUserResponse getOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("존재하지 않는 유저 입니다.")
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
     * User 정보 수정
     *
     * @param userId
     * @param request
     * @return
     */
    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("존재하지 않는 유저 입니다.")
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

    @Transactional
    public void delete(Long userId) {
        boolean existence = userRepository.existsById(userId);
        if(!existence) {
            throw new IllegalStateException("존재하지 않는 유저 입니다.");
        }
        userRepository.deleteById(userId);
    }
}

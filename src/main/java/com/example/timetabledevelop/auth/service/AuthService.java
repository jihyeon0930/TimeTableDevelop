package com.example.timetabledevelop.auth.service;

import com.example.timetabledevelop.auth.dto.LoginRequest;
import com.example.timetabledevelop.user.entity.User;
import com.example.timetabledevelop.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public void login(LoginRequest request, HttpSession session) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 이메일"));

        if (!user.getUserPass().equals(request.getUserPass())) {
            throw new IllegalStateException("비밀번호 틀림");
        }

        session.setAttribute("USER_ID", user.getId());
    }
}

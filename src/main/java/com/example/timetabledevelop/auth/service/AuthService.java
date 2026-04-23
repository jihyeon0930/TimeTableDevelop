package com.example.timetabledevelop.auth.service;

import com.example.timetabledevelop.auth.dto.LoginRequest;
import com.example.timetabledevelop.global.config.PasswordEncoder;
import com.example.timetabledevelop.global.exception.NotFoundException;
import com.example.timetabledevelop.global.exception.UnauthorizedException;
import com.example.timetabledevelop.user.entity.User;
import com.example.timetabledevelop.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void login(LoginRequest request, HttpSession session) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 이메일"));

        if (!passwordEncoder.matches(request.getUserPass(), user.getUserPass())) {
            throw new UnauthorizedException("비밀번호가 틀렸습니다.");
        }

        session.setAttribute("USER_ID", user.getId());
    }
}

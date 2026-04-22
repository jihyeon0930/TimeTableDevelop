package com.example.timetabledevelop.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class UpdateUserRequest {
    private String userName;
    private String email;
    private String userPass;
}

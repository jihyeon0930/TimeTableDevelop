package com.example.timetabledevelop.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateUserResponse {
    private final Long id;
    private final String userName;
    private final String email;
    private final String userPass;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}

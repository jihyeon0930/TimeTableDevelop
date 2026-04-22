package com.example.timetabledevelop.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetUserAllResponse {
    private final Long id;
    private final String userName;
    private final String email;
}

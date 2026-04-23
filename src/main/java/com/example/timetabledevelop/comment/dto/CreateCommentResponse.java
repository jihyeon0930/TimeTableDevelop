package com.example.timetabledevelop.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateCommentResponse {
    private final Long id;
    private final String content;
    private final Long userId;
    private final Long scheduleId;
    private final LocalDateTime createdAt;
}

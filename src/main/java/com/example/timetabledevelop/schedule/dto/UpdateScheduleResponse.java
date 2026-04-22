package com.example.timetabledevelop.schedule.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateScheduleResponse {
    private final Long id;
    private final Long userId;
    private final String userName;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
}

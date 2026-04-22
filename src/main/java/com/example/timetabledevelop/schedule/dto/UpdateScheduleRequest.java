package com.example.timetabledevelop.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {
    @NotBlank(message = "제목은 필수입니다")
    @Size(max = 10, message = "제목은 10글자 이내입니다")
    private String title;

    private String content;
}

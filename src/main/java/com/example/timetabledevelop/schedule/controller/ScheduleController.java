package com.example.timetabledevelop.schedule.controller;

import com.example.timetabledevelop.schedule.dto.CreateScheduleRequest;
import com.example.timetabledevelop.schedule.dto.CreateScheduleResponse;
import com.example.timetabledevelop.schedule.dto.GetScheduleAllResponse;
import com.example.timetabledevelop.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponse> saveSchedule(@PathVariable Long userId, @RequestBody CreateScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(userId, request));
    }

    @GetMapping
    public RequestEntity<List<GetScheduleAllResponse>> getSchedules(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getAll(userId));
    }
}

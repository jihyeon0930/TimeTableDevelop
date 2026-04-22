package com.example.timetabledevelop.schedule.service;

import com.example.timetabledevelop.schedule.dto.*;
import com.example.timetabledevelop.schedule.entity.Schedule;
import com.example.timetabledevelop.schedule.repository.ScheduleRepository;
import com.example.timetabledevelop.user.entity.User;
import com.example.timetabledevelop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * 스케쥴 생성
     *
     * @param userId
     * @param request
     * @return
     */
    @Transactional
    public CreateScheduleResponse save(Long userId, CreateScheduleRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저 입니다.")
        );
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                user
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getUser().getId(),
                savedSchedule.getUser().getUserName(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt()
        );
    }

    /**
     * 스케쥴 전체 조회
     *
     * @param userId
     * @return id, userId, name, title 만 조회
     */
    @Transactional(readOnly = true)
    public List<GetScheduleAllResponse> getAll(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저 입니다.")
        );
        List<Schedule> schedule = scheduleRepository.findByUserId(userId);
        return schedule.stream().
                map(s -> new GetScheduleAllResponse(
                        s.getId(),
                        s.getUser().getId(),
                        s.getUser().getUserName(),
                        s.getTitle()
                ))
                .toList();
    }

    /**
     * 스케쥴 단건 조회
     *
     * @param scheduleId
     * @return 정보 전체 조회 (내용까지)
     */
    @Transactional(readOnly = true)
    public GetScheduleResponse getOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 스케쥴 입니다.")
        );
        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getUser().getUserName(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    /**
     * 스케쥴 수정
     *
     * @param scheduleId
     * @param request
     * @return
     */
    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 스케쥴 입니다.")
        );
        schedule.update(
                request.getTitle(),
                request.getContent()
        );
        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getUser().getUserName(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    public void delete(Long scheduleId) {
        boolean existence = scheduleRepository.existsById(scheduleId);
        if(!existence) {
            throw new IllegalStateException("조재하지 않는 스케쥴 입니다.");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}

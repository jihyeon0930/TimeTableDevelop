package com.example.timetabledevelop.schedule.service;

import com.example.timetabledevelop.schedule.dto.CreateScheduleRequest;
import com.example.timetabledevelop.schedule.dto.CreateScheduleResponse;
import com.example.timetabledevelop.schedule.dto.GetScheduleAllResponse;
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
     * @return
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
}

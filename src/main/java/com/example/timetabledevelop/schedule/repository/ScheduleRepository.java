package com.example.timetabledevelop.schedule.repository;

import com.example.timetabledevelop.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByUser(Long userId);
}

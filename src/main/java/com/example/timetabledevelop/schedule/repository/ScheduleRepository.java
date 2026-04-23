package com.example.timetabledevelop.schedule.repository;

import com.example.timetabledevelop.schedule.dto.GetSchedulePageResponse;
import com.example.timetabledevelop.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByUserId(Long userId);

    @Query(
            value = """
            SELECT new com.example.timetabledevelop.schedule.dto.GetSchedulePageResponse(
                s.id,
                s.title,
                s.content,
                COUNT(c.id),
                s.createdAt,
                s.modifiedAt,
                u.userName
            )
            FROM Schedule s
            JOIN s.user u
            LEFT JOIN Comment c ON c.schedule = s
            GROUP BY s.id, s.title, s.content, s.createdAt, s.modifiedAt, u.userName
        """,
            countQuery = """
            SELECT COUNT(s)
            FROM Schedule s
        """
    )
    Page<GetSchedulePageResponse> findAllWithCommentCount(Pageable pageable);
}

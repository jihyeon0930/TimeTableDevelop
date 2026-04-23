package com.example.timetabledevelop.comment.service;

import com.example.timetabledevelop.comment.dto.*;
import com.example.timetabledevelop.comment.entity.Comment;
import com.example.timetabledevelop.comment.repository.CommentRepository;
import com.example.timetabledevelop.global.exception.NotFoundException;
import com.example.timetabledevelop.schedule.entity.Schedule;
import com.example.timetabledevelop.schedule.repository.ScheduleRepository;
import com.example.timetabledevelop.user.dto.UpdateUserResponse;
import com.example.timetabledevelop.user.entity.User;
import com.example.timetabledevelop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    /**
     * 유저 생성
     * @param userId
     * @param scheduleId
     * @param request
     * @return
     */
    @Transactional
    public CreateCommentResponse save(Long userId, Long scheduleId, CreateCommentRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("유저 없음"));
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new NotFoundException("일정 없음"));
        Comment comment = new Comment(
                request.getContent(),
                user,
                schedule
        );
        Comment saved = commentRepository.save(comment);
        return new CreateCommentResponse(
                saved.getId(),
                saved.getContent(),
                saved.getUser().getId(),
                saved.getSchedule().getId(),
                saved.getCreatedAt()
        );
    }

    /**
     * 전체 댓글 조회
     * @param scheduleId
     * @return
     */
    @Transactional(readOnly = true)
    public List<GetCommentAllResponse> getAll(Long scheduleId) {
        List<Comment> comments = commentRepository.findByScheduleId(scheduleId);
        return comments.stream()
                .map(c -> new GetCommentAllResponse(
                        c.getId(),
                        c.getContent(),
                        c.getUser().getId(),
                        c.getSchedule().getId()
                ))
                .toList();
    }

    /**
     * 단일 댓글 조회
     * @param commentId
     * @return
     */
    @Transactional
    public GetCommentResponse getOne(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("댓글이 존재하지 않습니다.")
        );
        return new GetCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getSchedule().getId(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    /**
     * 댓글 수정
     * @param commentId
     * @param request
     * @return
     */
    @Transactional
    public UpdateCommentResponse update(Long commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("댓글이 존재하지 않습니다.")
        );

        comment.update(request.getContent());
        return new UpdateCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getSchedule().getId(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }


    /**
     * 댓글 삭제
     * @param commentId
     */
    @Transactional
    public void delete(Long commentId) {
        boolean existence = commentRepository.existsById(commentId);
        if(!existence) {
            throw new IllegalStateException("존재하지 않는 유저 입니다.");
        }
        commentRepository.deleteById(commentId);
    }
}

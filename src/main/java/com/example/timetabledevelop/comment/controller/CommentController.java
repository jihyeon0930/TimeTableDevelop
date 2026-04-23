package com.example.timetabledevelop.comment.controller;

import com.example.timetabledevelop.comment.dto.*;
import com.example.timetabledevelop.comment.service.CommentService;
import com.example.timetabledevelop.user.service.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CreateCommentResponse> saveComment(
            @PathVariable Long scheduleId,
            @RequestParam Long userId,
            @RequestBody CreateCommentRequest request
            ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(userId, scheduleId, request));
    }

    @GetMapping
    public ResponseEntity<List<GetCommentAllResponse>> getComments(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAll(scheduleId));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<GetCommentResponse> getComment(@PathVariable Long commentId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getOne(commentId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(@PathVariable Long commentId, @RequestBody UpdateCommentRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(commentId, request));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        userService.delete(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

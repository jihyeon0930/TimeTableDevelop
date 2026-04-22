package com.example.timetabledevelop.schedule.entity;

import com.example.timetabledevelop.entity.BaseEntity;
import com.example.timetabledevelop.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public Schedule (String title, String content){
        this.title = title;
        this.content = content;
    }


}

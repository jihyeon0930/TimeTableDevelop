package com.example.timetabledevelop.user.entity;

import com.example.timetabledevelop.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "user_name")
    private String userName;

    @NotBlank
    @Email
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @Size(min = 4, max = 8)
    @Column(name = "user_pass")
    private String userPass;

    public User(String userName, String email, String userPass) {
        this.userName = userName;
        this.email = email;
        this.userPass = userPass;
    }

    public void update(String userName, String email, String userPass) {
        this.userName = userName;
        this.email = email;
        this.userPass = userPass;
    }

}

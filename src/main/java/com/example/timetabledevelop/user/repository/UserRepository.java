package com.example.timetabledevelop.user.repository;

import com.example.timetabledevelop.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

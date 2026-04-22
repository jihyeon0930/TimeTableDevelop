package com.example.timetabledevelop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TimeTableDevelopApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeTableDevelopApplication.class, args);
    }

}

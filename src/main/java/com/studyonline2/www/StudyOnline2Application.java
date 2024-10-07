package com.studyonline2.www;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.studyonline2.www.mapper")
public class StudyOnline2Application {

    public static void main(String[] args) {
        SpringApplication.run(StudyOnline2Application.class, args);
    }

}

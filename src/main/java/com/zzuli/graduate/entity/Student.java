package com.zzuli.graduate.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "student")
@Data
public class Student {
    @Id
    private String studentId;
    private String name;
    private String major;
    private String className;
    private String phone;
    private Integer totalCredits;
    private Integer earnedCredits;
    private String thesisStatus;
    private String internStatus;

    @Convert(converter = GradeMapConverter.class)
    @Column(columnDefinition = "TEXT")
    private Map<String, Integer> grades;

    @Convert(converter = HonorListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<Honor> honors;

    public Student() {}

    public Student(String studentId, String name, String major, String className, String phone) {
        this.studentId = studentId;
        this.name = name;
        this.major = major;
        this.className = className;
        this.phone = phone;
    }

    @Data
    public static class Honor {
        private String title;
        private String year;
        public Honor() {}
        public Honor(String title, String year) {
            this.title = title;
            this.year = year;
        }
    }
}
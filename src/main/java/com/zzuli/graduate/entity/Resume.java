package com.zzuli.graduate.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "resume")
@Data
public class Resume {
    @Id
    private String studentId;
    @Column(columnDefinition = "TEXT")
    private String education;
    @Column(columnDefinition = "TEXT")
    private String skills;
    @Column(columnDefinition = "TEXT")
    private String experience;
    @Column(columnDefinition = "TEXT")
    private String selfEvaluation;
    private String phone;
    private String email;
    private LocalDate updateTime;

    public Resume() {}
    public Resume(String studentId, String education, String skills, String experience,
                  String selfEvaluation, String phone, String email, LocalDate updateTime) {
        this.studentId = studentId;
        this.education = education;
        this.skills = skills;
        this.experience = experience;
        this.selfEvaluation = selfEvaluation;
        this.phone = phone;
        this.email = email;
        this.updateTime = updateTime;
    }
}
package com.zzuli.graduate.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "resume_delivery")
@Data
public class ResumeDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentId;
    private Long recruitmentId;
    private String companyName;
    private String jobTitle;
    private LocalDate deliveryDate;
    private String status;

    public ResumeDelivery() {}
    public ResumeDelivery(Long id, String studentId, Long recruitmentId, String companyName,
                          String jobTitle, LocalDate deliveryDate, String status) {
        this.id = id;
        this.studentId = studentId;
        this.recruitmentId = recruitmentId;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.deliveryDate = deliveryDate;
        this.status = status;
    }
}
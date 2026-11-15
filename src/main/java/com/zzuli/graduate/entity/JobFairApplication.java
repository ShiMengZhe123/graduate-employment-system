package com.zzuli.graduate.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "job_fair_application")
@Data
public class JobFairApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long jobFairId;
    private String companyCode;
    private String companyName;
    private String status;
    private LocalDate applyDate;

    public JobFairApplication() {}
    public JobFairApplication(Long id, Long jobFairId, String companyCode, String companyName, String status) {
        this.id = id;
        this.jobFairId = jobFairId;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.status = status;
        this.applyDate = LocalDate.now();
    }
}
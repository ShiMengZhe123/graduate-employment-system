package com.zzuli.graduate.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "recruitment")
@Data
public class Recruitment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobTitle;
    private String companyName;
    private LocalDate publishDate;
    @Column(columnDefinition = "TEXT")
    private String details;
    private String status;

    public Recruitment() {}
    public Recruitment(Long id, String jobTitle, String companyName, LocalDate publishDate, String details, String status) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.publishDate = publishDate;
        this.details = details;
        this.status = status;
    }
}
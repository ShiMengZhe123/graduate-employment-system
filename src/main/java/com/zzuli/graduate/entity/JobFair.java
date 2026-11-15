package com.zzuli.graduate.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "job_fair")
@Data
public class JobFair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String date;
    private String location;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String status;
    private LocalDate createTime;

    public JobFair() {}
    public JobFair(Long id, String title, String date, String location, String description, String status) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.location = location;
        this.description = description;
        this.status = status;
        this.createTime = LocalDate.now();
    }
}
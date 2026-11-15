package com.zzuli.graduate.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ApplicationRecord {
    private Long id;
    private String studentId;
    private Long recruitmentId;
    private LocalDate applyDate;
    private String status;
}
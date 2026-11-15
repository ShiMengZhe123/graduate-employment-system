package com.zzuli.graduate.repository;

import com.zzuli.graduate.entity.JobFair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobFairRepository extends JpaRepository<JobFair, Long> {
    List<JobFair> findByStatus(String status);
}
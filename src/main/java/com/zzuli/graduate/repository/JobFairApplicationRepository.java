package com.zzuli.graduate.repository;

import com.zzuli.graduate.entity.JobFairApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobFairApplicationRepository extends JpaRepository<JobFairApplication, Long> {
    List<JobFairApplication> findByCompanyCode(String companyCode);
    boolean existsByJobFairIdAndCompanyCode(Long jobFairId, String companyCode);
}
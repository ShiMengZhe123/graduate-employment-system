package com.zzuli.graduate.repository;

import com.zzuli.graduate.entity.ResumeDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResumeDeliveryRepository extends JpaRepository<ResumeDelivery, Long> {
    List<ResumeDelivery> findByStudentId(String studentId);
    List<ResumeDelivery> findByCompanyName(String companyName);
}
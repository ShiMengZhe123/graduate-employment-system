package com.zzuli.graduate.repository;

import com.zzuli.graduate.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    List<Recruitment> findByStatus(String status);
    List<Recruitment> findByCompanyName(String companyName);
}
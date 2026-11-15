package com.zzuli.graduate.controller;

import com.zzuli.graduate.entity.News;
import com.zzuli.graduate.entity.Recruitment;
import com.zzuli.graduate.repository.NewsRepository;
import com.zzuli.graduate.repository.RecruitmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired private NewsRepository newsRepository;
    @Autowired private RecruitmentRepository recruitmentRepository;

    @GetMapping("/news")
    public List<News> getNews() {
        return newsRepository.findAll();
    }

    @GetMapping("/jobs")
    public List<Recruitment> getJobs() {
        return recruitmentRepository.findByStatus("approved");
    }
}
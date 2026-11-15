package com.zzuli.graduate.controller;

import com.zzuli.graduate.repository.NewsRepository;
import com.zzuli.graduate.repository.RecruitmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired private NewsRepository newsRepository;
    @Autowired private RecruitmentRepository recruitmentRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("newsList", newsRepository.findAll());
        model.addAttribute("recruitments", recruitmentRepository.findAll());
        // 直接返回视图名称 "index"，对应 src/main/resources/templates/index.html
        return "index";
    }

    @GetMapping("/career")
    public String career() {
        return "career";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/school-home")
    public String schoolHome() {
        return "redirect:https://www.zzuli.edu.cn/";
    }
}
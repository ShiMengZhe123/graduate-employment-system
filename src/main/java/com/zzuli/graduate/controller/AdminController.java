package com.zzuli.graduate.controller;

import com.zzuli.graduate.entity.News;
import com.zzuli.graduate.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private StudentRepository studentRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private RecruitmentRepository recruitmentRepository;
    @Autowired private NewsRepository newsRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/";
        model.addAttribute("studentCount", studentRepository.count());
        model.addAttribute("companyCount", companyRepository.count());
        model.addAttribute("jobCount", recruitmentRepository.count());
        model.addAttribute("newsList", newsRepository.findAll());
        model.addAttribute("majorRate", Map.of("计算机",92,"艺术设计",88,"经管",85,"外语",90));
        model.addAttribute("studentsSample", studentRepository.findAll().stream().limit(3).collect(Collectors.toList()));
        return "admin_dashboard";
    }

    @PostMapping("/add-news")
    public String addNews(@RequestParam String title, @RequestParam String content, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        newsRepository.save(new News(title, LocalDate.now(), content));
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete-news/{id}")
    public String deleteNews(@PathVariable Long id, HttpSession session) {
        if (isAdmin(session)) newsRepository.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/manage-recruitments")
    public String manageRecruitments(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/";
        model.addAttribute("recruitments", recruitmentRepository.findAll());
        return "admin_manage_recruitments";
    }

    @PostMapping("/review-recruitment/{id}")
    public String reviewRecruitment(@PathVariable Long id, @RequestParam String action, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/";
        // 修正：使用 recruitmentRepository 而不是 RecruitmentRepository
        recruitmentRepository.findById(id).ifPresent(rec -> {
            if ("approve".equals(action)) {
                rec.setStatus("approved");
            } else if ("reject".equals(action)) {
                rec.setStatus("rejected");
            }
            recruitmentRepository.save(rec);
        });
        return "redirect:/admin/manage-recruitments";
    }

    private boolean isAdmin(HttpSession session) {
        return session.getAttribute("role") != null && "admin".equals(session.getAttribute("role"));
    }
}
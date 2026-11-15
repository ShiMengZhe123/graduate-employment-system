package com.zzuli.graduate.controller;

import com.zzuli.graduate.entity.*;
import com.zzuli.graduate.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired private StudentRepository studentRepository;
    @Autowired private RecruitmentRepository recruitmentRepository;
    @Autowired private ResumeRepository resumeRepository;
    @Autowired private ResumeDeliveryRepository deliveryRepository;

    // 页面
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("user");
        if (student == null) return "redirect:/";
        model.addAttribute("student", student);
        model.addAttribute("creditPercent", 92);
        model.addAttribute("thesisStatus", "已通过答辩");
        model.addAttribute("internStatus", "实习已完成");
        model.addAttribute("recruitments", recruitmentRepository.findByStatus("approved").stream().limit(3).collect(Collectors.toList()));
        return "student_dashboard";
    }

    @GetMapping("/resume")
    public String resumePage(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("user");
        if (student == null) return "redirect:/";
        Resume resume = resumeRepository.findById(student.getStudentId()).orElseGet(() -> {
            Resume r = new Resume();
            r.setStudentId(student.getStudentId());
            r.setPhone(student.getPhone());
            return r;
        });
        model.addAttribute("resume", resume);
        return "student_resume";
    }

    @PostMapping("/save-resume")
    public String saveResume(@ModelAttribute Resume resume, HttpSession session) {
        Student student = (Student) session.getAttribute("user");
        if (student == null) return "redirect:/";
        resume.setStudentId(student.getStudentId());
        resume.setUpdateTime(LocalDate.now());
        resumeRepository.save(resume);
        return "redirect:/student/resume?success";
    }

    @PostMapping("/deliver/{recruitmentId}")
    public String deliverResume(@PathVariable Long recruitmentId, HttpSession session) {
        Student student = (Student) session.getAttribute("user");
        if (student == null) return "redirect:/";
        Recruitment rec = recruitmentRepository.findById(recruitmentId).orElse(null);
        if (rec == null) return "redirect:/student/dashboard?error=岗位不存在";
        boolean already = deliveryRepository.findByStudentId(student.getStudentId()).stream()
                .anyMatch(d -> d.getRecruitmentId().equals(recruitmentId));
        if (already) return "redirect:/student/dashboard?error=您已投递过该岗位";
        deliveryRepository.save(new ResumeDelivery(null, student.getStudentId(), recruitmentId,
                rec.getCompanyName(), rec.getJobTitle(), LocalDate.now(), "已投递"));
        return "redirect:/student/my-deliveries";
    }

    @GetMapping("/my-deliveries")
    public String myDeliveries(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("user");
        if (student == null) return "redirect:/";
        model.addAttribute("deliveries", deliveryRepository.findByStudentId(student.getStudentId()));
        return "student_deliveries";
    }

    // JSON API
    @GetMapping("/api/profile")
    @ResponseBody
    public Student getProfileApi(HttpSession session) {
        return (Student) session.getAttribute("user");
    }

    @GetMapping("/api/resume")
    @ResponseBody
    public Resume getResumeApi(HttpSession session) {
        Student student = (Student) session.getAttribute("user");
        if (student == null) return null;
        return resumeRepository.findById(student.getStudentId()).orElseGet(() -> {
            Resume r = new Resume();
            r.setStudentId(student.getStudentId());
            r.setPhone(student.getPhone());
            return r;
        });
    }

    @PostMapping("/api/resume")
    @ResponseBody
    public Map<String, String> saveResumeApi(@RequestBody Resume resume, HttpSession session) {
        Student student = (Student) session.getAttribute("user");
        Map<String, String> result = new HashMap<>();
        if (student == null) {
            result.put("status", "error");
            result.put("message", "未登录");
            return result;
        }
        resume.setStudentId(student.getStudentId());
        resume.setUpdateTime(LocalDate.now());
        resumeRepository.save(resume);
        result.put("status", "success");
        return result;
    }

    @GetMapping("/api/deliveries")
    @ResponseBody
    public List<ResumeDelivery> getDeliveriesApi(HttpSession session) {
        Student student = (Student) session.getAttribute("user");
        if (student == null) return new ArrayList<>();
        return deliveryRepository.findByStudentId(student.getStudentId());
    }

    @PostMapping("/api/deliver")
    @ResponseBody
    public Map<String, String> deliverResumeApi(@RequestParam Long recruitmentId, HttpSession session) {
        Student student = (Student) session.getAttribute("user");
        Map<String, String> result = new HashMap<>();
        if (student == null) {
            result.put("status", "error");
            result.put("message", "未登录");
            return result;
        }
        Recruitment rec = recruitmentRepository.findById(recruitmentId).orElse(null);
        if (rec == null) {
            result.put("status", "error");
            result.put("message", "岗位不存在");
            return result;
        }
        boolean already = deliveryRepository.findByStudentId(student.getStudentId()).stream()
                .anyMatch(d -> d.getRecruitmentId().equals(recruitmentId));
        if (already) {
            result.put("status", "error");
            result.put("message", "您已投递过该岗位");
            return result;
        }
        deliveryRepository.save(new ResumeDelivery(null, student.getStudentId(), recruitmentId,
                rec.getCompanyName(), rec.getJobTitle(), LocalDate.now(), "已投递"));
        result.put("status", "success");
        return result;
    }

    @GetMapping("/api/recruitments")
    @ResponseBody
    public List<Recruitment> getRecruitmentsApi() {
        return recruitmentRepository.findByStatus("approved");
    }

    @PostMapping("/api/quiz/submit")
    @ResponseBody
    public Map<String, Object> submitQuiz(@RequestBody QuizAnswer quizAnswer) {
        int total = quizAnswer.getAnswers().stream().mapToInt(Integer::intValue).sum();
        String type, suggestion;
        if (total < 15) {
            type = "研究型 (Investigative)";
            suggestion = "适合从事科研、技术开发、数据分析等岗位，建议深造或进入研发部门。";
        } else if (total < 22) {
            type = "企业型 (Enterprising)";
            suggestion = "适合管理、销售、创业等岗位，可考虑 MBA 或参加领导力培训。";
        } else {
            type = "艺术型 (Artistic)";
            suggestion = "适合设计、媒体、创意策划等岗位，多积累作品集。";
        }
        Map<String, Object> result = new HashMap<>();
        result.put("type", type);
        result.put("score", total);
        result.put("suggestion", suggestion);
        return result;
    }

    @GetMapping("/center")
    public String studentCenter() {
        return "student_center";
    }
}
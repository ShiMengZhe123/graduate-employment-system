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
@RequestMapping("/company")
public class CompanyController {

    @Autowired private RecruitmentRepository recruitmentRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private ResumeDeliveryRepository deliveryRepository;
    @Autowired private ResumeRepository resumeRepository;
    @Autowired private JobFairRepository jobFairRepository;
    @Autowired private JobFairApplicationRepository applicationRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Company company = (Company) session.getAttribute("user");
        if (company == null) return "redirect:/";
        model.addAttribute("company", company);
        model.addAttribute("myJobs", recruitmentRepository.findByCompanyName(company.getName()));
        List<String> majors = studentRepository.findAll().stream()
                .map(Student::getMajor).distinct().collect(Collectors.toList());
        model.addAttribute("majors", majors);
        return "company_dashboard";
    }

    @PostMapping("/add-job")
    public String addJob(@RequestParam String jobTitle, @RequestParam String salary,
                         @RequestParam String location, @RequestParam String requirement,
                         HttpSession session) {
        Company company = (Company) session.getAttribute("user");
        if (company == null) return "redirect:/";
        String details = "💰 薪资：" + salary + "\n📍 地点：" + location + "\n📝 要求：" + requirement;
        recruitmentRepository.save(new Recruitment(null, jobTitle, company.getName(), LocalDate.now(), details, "pending"));
        return "redirect:/company/dashboard";
    }

    @PostMapping("/delete-job/{id}")
    public String deleteJob(@PathVariable Long id, HttpSession session) {
        Company company = (Company) session.getAttribute("user");
        if (company != null) {
            recruitmentRepository.findById(id).ifPresent(rec -> {
                if (rec.getCompanyName().equals(company.getName()))
                    recruitmentRepository.deleteById(id);
            });
        }
        return "redirect:/company/dashboard";
    }

    @GetMapping("/search-student")
    public String searchStudent(@RequestParam(required = false) String major,
                                HttpSession session, Model model) {
        Company company = (Company) session.getAttribute("user");
        if (company == null) return "redirect:/";
        List<Student> students = (major == null || major.isEmpty()) ?
                studentRepository.findAll() :
                studentRepository.findAll().stream().filter(s -> s.getMajor().contains(major)).collect(Collectors.toList());
        model.addAttribute("students", students);
        model.addAttribute("company", company);
        model.addAttribute("myJobs", recruitmentRepository.findByCompanyName(company.getName()));
        model.addAttribute("majors", studentRepository.findAll().stream().map(Student::getMajor).distinct().collect(Collectors.toList()));
        model.addAttribute("searchMajor", major);
        return "company_dashboard";
    }

    @GetMapping("/received-resumes")
    public String receivedResumes(HttpSession session, Model model) {
        Company company = (Company) session.getAttribute("user");
        if (company == null) return "redirect:/";
        List<ResumeDelivery> received = deliveryRepository.findByCompanyName(company.getName());
        List<Map<String, Object>> deliveryDetails = new ArrayList<>();
        for (ResumeDelivery d : received) {
            Map<String, Object> detail = new HashMap<>();
            detail.put("delivery", d);
            detail.put("student", studentRepository.findById(d.getStudentId()).orElse(null));
            detail.put("resume", resumeRepository.findById(d.getStudentId()).orElse(null));
            deliveryDetails.add(detail);
        }
        model.addAttribute("deliveries", deliveryDetails);
        model.addAttribute("company", company);
        return "company_received_resumes";
    }

    @PostMapping("/update-delivery-status/{deliveryId}")
    public String updateDeliveryStatus(@PathVariable Long deliveryId, @RequestParam String status, HttpSession session) {
        Company company = (Company) session.getAttribute("user");
        if (company != null) {
            deliveryRepository.findById(deliveryId).ifPresent(d -> {
                if (d.getCompanyName().equals(company.getName())) {
                    d.setStatus(status);
                    deliveryRepository.save(d);
                }
            });
        }
        return "redirect:/company/received-resumes";
    }

    @GetMapping("/job-fairs")
    public String listJobFairs(HttpSession session, Model model) {
        Company company = (Company) session.getAttribute("user");
        if (company == null) return "redirect:/";
        model.addAttribute("jobFairs", jobFairRepository.findByStatus("approved"));
        model.addAttribute("company", company);
        Set<Long> appliedIds = applicationRepository.findByCompanyCode(company.getCreditCode()).stream()
                .map(JobFairApplication::getJobFairId).collect(Collectors.toSet());
        model.addAttribute("appliedIds", appliedIds);
        return "company_jobfairs";
    }

    @PostMapping("/apply-jobfair/{jobFairId}")
    public String applyJobFair(@PathVariable Long jobFairId, HttpSession session) {
        Company company = (Company) session.getAttribute("user");
        if (company == null) return "redirect:/";
        if (!applicationRepository.existsByJobFairIdAndCompanyCode(jobFairId, company.getCreditCode())) {
            applicationRepository.save(new JobFairApplication(null, jobFairId, company.getCreditCode(),
                    company.getName(), "pending"));
        }
        return "redirect:/company/job-fairs";
    }
}
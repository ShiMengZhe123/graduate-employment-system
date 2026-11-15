package com.zzuli.graduate.controller;

import com.zzuli.graduate.entity.*;
import com.zzuli.graduate.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired private StudentRepository studentRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private AdminRepository adminRepository;

    // ========== 登录 ==========
    @PostMapping("/login")
    public String login(@RequestParam String role,
                        @RequestParam(required = false) String studentId,
                        @RequestParam(required = false) String name,
                        @RequestParam(required = false) String code,
                        @RequestParam(required = false) String contactPhone,
                        @RequestParam(required = false) String username,
                        @RequestParam(required = false) String password,
                        HttpSession session) {

        if ("student".equals(role)) {
            studentRepository.findById(studentId).ifPresent(s -> {
                if (s.getName().equals(name)) {
                    session.setAttribute("user", s);
                    session.setAttribute("role", "student");
                }
            });
            if (session.getAttribute("role") != null) return "redirect:/student/center";
        } else if ("company".equals(role)) {
            companyRepository.findById(code).ifPresent(c -> {
                if (c.getContactPhone().equals(contactPhone)) {
                    session.setAttribute("user", c);
                    session.setAttribute("role", "company");
                }
            });
            if (session.getAttribute("role") != null) return "redirect:/company/dashboard";
        } else if ("admin".equals(role)) {
            adminRepository.findById(username).ifPresent(a -> {
                if (a.getPassword().equals(password)) {
                    session.setAttribute("user", a);
                    session.setAttribute("role", "admin");
                }
            });
            if (session.getAttribute("role") != null) return "redirect:/admin/dashboard";
        }
        return "redirect:/?error=1";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // ========== 注册页面 ==========
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // ========== 学生注册 ==========
    @PostMapping("/register/student")
    public String registerStudent(@RequestParam String studentId,
                                  @RequestParam String name,
                                  @RequestParam(required = false) String major,
                                  @RequestParam(required = false) String className,
                                  @RequestParam(required = false) String phone) {
        if (studentRepository.existsById(studentId)) {
            return "redirect:/register?error=学号已存在";
        }
        Student student = new Student(studentId, name, major, className, phone);
        student.setTotalCredits(0);
        student.setEarnedCredits(0);
        student.setThesisStatus("未开始");
        student.setInternStatus("未开始");
        studentRepository.save(student);
        return "redirect:/?registerSuccess=1";
    }

    // ========== 企业注册 ==========
    @PostMapping("/register/company")
    public String registerCompany(@RequestParam String creditCode,
                                  @RequestParam String name,
                                  @RequestParam String contactPhone,
                                  @RequestParam(required = false) String address,
                                  @RequestParam(required = false) String description) {
        if (companyRepository.existsById(creditCode)) {
            return "redirect:/register?error=信用代码已存在";
        }
        Company company = new Company(creditCode, name, contactPhone, address);
        company.setDescription(description == null ? "" : description);
        company.setVerified(true);
        companyRepository.save(company);
        return "redirect:/?registerSuccess=1";
    }

    // ========== 管理员注册（可根据需要注释） ==========
    @PostMapping("/register/admin")
    public String registerAdmin(@RequestParam String username,
                                @RequestParam String password,
                                @RequestParam(required = false) String role) {
        if (adminRepository.existsById(username)) {
            return "redirect:/register?error=用户名已存在";
        }
        Admin admin = new Admin(username, password, role == null ? "普通管理员" : role);
        adminRepository.save(admin);
        return "redirect:/?registerSuccess=1";
    }
}
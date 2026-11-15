package com.zzuli.graduate.component;

import com.zzuli.graduate.entity.*;
import com.zzuli.graduate.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private StudentRepository studentRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private AdminRepository adminRepository;
    @Autowired private NewsRepository newsRepository;
    @Autowired private RecruitmentRepository recruitmentRepository;
    @Autowired private ResumeRepository resumeRepository;
    @Autowired private JobFairRepository jobFairRepository;
    @Autowired private JobFairApplicationRepository jobFairApplicationRepository;

    @Override
    public void run(String... args) {
        if (studentRepository.count() == 0) {
            // 学生张三
            Student zhang = new Student("542413330431", "张三", "行业软件", "行软4班", "99999999999");
            Map<String, Integer> gradesZhang = new LinkedHashMap<>();
            gradesZhang.put("高等数学", 92);
            gradesZhang.put("大学英语", 88);
            gradesZhang.put("Java程序设计", 95);
            gradesZhang.put("数据库原理", 90);
            gradesZhang.put("web前端", 86);
            zhang.setGrades(gradesZhang);
            List<Student.Honor> honorsZhang = new ArrayList<>();
            honorsZhang.add(new Student.Honor("校三等奖学金", "2024"));
            honorsZhang.add(new Student.Honor("全国大学生计算机设计大赛省级二等奖", "2025"));
            honorsZhang.add(new Student.Honor("优秀共青团员", "2025"));
            honorsZhang.add(new Student.Honor("校就业办优秀干事", "2025"));
            zhang.setHonors(honorsZhang);
            zhang.setTotalCredits(160);
            zhang.setEarnedCredits(148);
            zhang.setThesisStatus("已通过答辩");
            zhang.setInternStatus("实习已完成");

            // 学生李四
            Student li = new Student("542413330400", "李四", "软件工程", "软工2班", "13800138002");
            Map<String, Integer> gradesLi = new LinkedHashMap<>();
            gradesLi.put("高等数学", 78);
            gradesLi.put("大学英语", 85);
            gradesLi.put("Java程序设计", 88);
            gradesLi.put("数据库原理", 82);
            gradesLi.put("软件工程", 79);
            li.setGrades(gradesLi);
            List<Student.Honor> honorsLi = new ArrayList<>();
            honorsLi.add(new Student.Honor("校三等奖学金", "2024"));
            li.setHonors(honorsLi);
            li.setTotalCredits(160);
            li.setEarnedCredits(132);
            li.setThesisStatus("答辩准备中");
            li.setInternStatus("实习进行中");

            studentRepository.save(zhang);
            studentRepository.save(li);

            // 企业
            companyRepository.save(new Company("华为123", "华为技术有限公司", "11111111111", "深圳市龙岗区坂田华为基地"));
            companyRepository.save(new Company("腾讯123", "阿里巴巴集团", "22222222222", "杭州市余杭区文一西路969号"));

            // 管理员
            adminRepository.save(new Admin("石梦哲", "123", "超级管理员"));

            // 新闻
            newsRepository.save(new News("2026届毕业生春季双选会即将举办", LocalDate.of(2026, 5, 20),
                    "🎉 为促进2026届毕业生高质量就业，我校定于2026年5月28日（周四）9:00-16:00在科学校区体育馆举办“2026届毕业生春季双选会”。届时将有华为、腾讯、字节跳动等200余家知名企业参会，提供岗位5000余个。请毕业生提前准备好简历，着装得体参会。详情咨询就业指导中心电话：0371-86608111。"));
            newsRepository.save(new News("关于做好2026届毕业生求职创业补贴发放工作的通知", LocalDate.of(2026, 5, 18),
                    "📢 根据河南省人力资源和社会保障厅通知，2026届毕业生求职创业补贴申请工作已启动。补贴对象：毕业年度内有就业创业意愿并积极求职的城乡低保家庭、贫困残疾人家庭、建档立卡贫困家庭、获得国家助学贷款的毕业生等。补贴标准：2000元/人。申请时间：5月20日-6月10日，登录“河南就业网上办事大厅”提交材料。"));
            newsRepository.save(new News("名企宣讲会：腾讯、阿里、字节跳动专场", LocalDate.of(2026, 5, 15),
                    "💼 5月22日-5月25日，就业指导中心将举办三场名企线上宣讲会：\n• 腾讯：5月22日 19:00，腾讯会议号 123-456-789\n• 阿里巴巴：5月23日 19:00，钉钉群号 35678902\n• 字节跳动：5月25日 19:00，抖音直播搜索“字节跳动招聘”\n宣讲会内容包括企业介绍、岗位解读、简历面试技巧，并设有抽奖环节。请同学们提前10分钟进入。"));
            newsRepository.save(new News("关于选拔2026届优秀毕业生赴海外合作院校攻读硕士的通知", LocalDate.of(2026, 5, 10),
                    "🌍 我校与英国诺丁汉大学、澳大利亚悉尼科技大学等合作开展“1+1”硕士项目。现面向2026届本科毕业生选拔20名优秀学生，专业不限，GPA≥3.0，雅思6.5（单项不低于6.0）。报名截止6月15日，详情咨询国际交流处。"));

            // 招聘信息
            recruitmentRepository.save(new Recruitment(null, "2026校园招聘", "华为技术有限公司",
                    LocalDate.of(2026, 5, 22), "🔹 岗位：软件开发工程师、算法工程师、硬件工程师、测试工程师\n🔹 工作地点：深圳、北京、上海、杭州、成都\n🔹 招聘要求：2026届本科及以上学历，计算机、通信、电子等相关专业；熟悉Java/Python/C++至少一种语言；通过英语四级。\n🔹 薪资待遇：本科年薪20-30万，硕士25-40万，14薪，六险一金，员工宿舍，带薪年假，加班补贴。\n🔹 投递方式：登录华为招聘官网 career.huawei.com 注册并投递，截止日期2026年6月10日。", "approved"));
            recruitmentRepository.save(new Recruitment(null, "软件工程师岗", "腾讯科技",
                    LocalDate.of(2026, 5, 19), "🔹 岗位职责：参与微信/QQ/腾讯云等核心产品后台开发；负责高并发系统设计与优化；编写技术文档。\n🔹 任职要求：计算机相关专业，熟悉Linux环境，掌握C++/Go语言；有分布式系统经验者优先。\n🔹 工作地点：深圳总部、北京、上海、广州\n🔹 福利待遇：年薪30-50万，16薪，股票期权，免费三餐，班车接送，年度体检，腾讯学院培训。\n🔹 投递：join.qq.com 搜索“软件工程师”投递，简历命名格式：姓名-学校-专业。", "pending"));
            recruitmentRepository.save(new Recruitment(null, "产品经理培训生", "阿里巴巴集团",
                    LocalDate.of(2026, 5, 16), "🔹 职位描述：负责淘宝/天猫/阿里云等业务线的产品设计、用户调研、需求分析、项目推进；与研发、运营团队紧密合作，完成产品迭代。\n🔹 任职资格：2026届本科及以上，专业不限；对互联网产品有热情，逻辑清晰，沟通能力强；有创业或产品项目经验者加分。\n🔹 工作城市：杭州、北京、上海\n🔹 培养计划：为期一年的轮岗培训，由资深产品总监亲自带教，表现优异者直接晋升P6。\n🔹 投递方式：campus.alibaba.com 在线申请，截止6月5日。", "approved"));
            recruitmentRepository.save(new Recruitment(null, "Java开发工程师（实习转正）", "字节跳动",
                    LocalDate.of(2026, 5, 14), "🔹 岗位：Java后端开发实习生（可转正）\n🔹 工作地点：北京、上海、成都\n🔹 要求：2026届本科及以上，Java基础扎实，熟悉Spring Boot/MyBatis，有项目经验优先。\n🔹 待遇：实习日薪400-500元，免费三餐+下午茶，租房补贴；转正后年薪25-40万。\n🔹 投递：jobs.bytedance.com 搜索“后端开发实习生”投递，简历直达HR。", "pending"));

            // 简历示例（张三）
            Resume resumeZhang = new Resume("542413330431",
                    "郑州轻工业大学 行业软件 2026届本科，GPA 3.8",
                    "Java, Spring Boot, MySQL, JavaScript",
                    "校园二手交易平台核心开发者；华为实习3个月参与项目",
                    "热爱技术，善于团队协作，抗压能力强",
                    "99999999999",
                    "zhangsan@zzuli.edu.cn",
                    LocalDate.now());
            resumeRepository.save(resumeZhang);

            // 招聘会
            jobFairRepository.save(new JobFair(null, "郑州轻工业大学2026届毕业生春季双选会",
                    "2026-05-28", "科学校区体育馆", "200+企业，5000+岗位，涵盖IT、制造、教育等行业。", "approved"));
            jobFairRepository.save(new JobFair(null, "IT专场招聘会",
                    "2026-06-10", "东风校区就业中心", "重点邀请华为、腾讯、字节等IT企业。", "approved"));
            jobFairRepository.save(new JobFair(null, "中小企业专场",
                    "2026-06-20", "科学校区双创中心", "欢迎广大中小企业报名。", "pending"));
        }
    }
}
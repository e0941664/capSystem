package team2.capSystem.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team2.capSystem.helper.userSessionDetails;
import team2.capSystem.model.CourseDetail;
import team2.capSystem.model.Student;
import team2.capSystem.model.StudentCourse;
import team2.capSystem.repo.CourseDetailRepository;
import team2.capSystem.repo.StudentCourseRepository;
import team2.capSystem.repo.StudentRepository;
import team2.capSystem.services.CourseService;
import team2.capSystem.services.StudentService;

@Controller
@RequestMapping(path = "/student")
public class StudentController {
    
    @Autowired StudentRepository sRepo;
    @Autowired StudentCourseRepository scRepo;
    @Autowired CourseDetailRepository cdRepo;
    
    @Autowired 
    private StudentService studService;
    // @Autowired
    // private CourseService courseService;

    
    @RequestMapping("/student-dashboard")
    public String showDashboard(){
        return "students/student-dashboard";
    }

    @RequestMapping(path = "/course-history")
    public String showCourseHistory(HttpSession session, Model model){
        userSessionDetails usd = (userSessionDetails)session.getAttribute("userSessionDetails");
        List<StudentCourse> reslt =  studService.getStudentCourseBySession(usd);
        model.addAttribute("studCourse", reslt);
        return "students/student-course";

    }

    //to do service
    @RequestMapping(path = "/enroll")
    public String showAvailbleCourses(HttpSession session, Model model){
        userSessionDetails usd = (userSessionDetails)session.getAttribute("userSessionDetails");
        // List<CourseDetail> availCourse = cdRepo.findByStartDateAfter(LocalDate.now());
        // List<StudentCourse> takenCourse = scRepo.findSCByStudentId(usd.getUserId());
        // List<CourseDetail> enrollCourses = new ArrayList<CourseDetail>();
        // if (takenCourse.size() != 0){
        //     for(CourseDetail cdCourse : availCourse){
        //         for(StudentCourse scourse : takenCourse){
        //             if (scourse.getCourse().getCourse().getCourseId() == cdCourse.getCourse().getCourseId()){
        //                 continue;
        //             }
        //         }
        //         enrollCourses.add(cdCourse);
        //     }
        // }
        // else{
        //     enrollCourses = availCourse;
        // }
        
        List<CourseDetail> enrollCourses = studService.getStudentAvailCourses(usd);

        model.addAttribute("enrollCourses", enrollCourses);
        return "students/student-enroll-course";

    }
    
    @PostMapping("/enrollCourse")
    public String enrollCourse(@ModelAttribute CourseDetail cd, Model model, HttpSession session) {
        userSessionDetails usd = (userSessionDetails)session.getAttribute("userSessionDetails");
    	Student stud = sRepo.findById(usd.getUserId()).get();
    	//Optional<CourseDetail> cdetailList = cdRepo.findById(courseId);
    	scRepo.save(new StudentCourse(stud, cd));
        System.out.println(cd.toString());
    	return "students/student-course";
    }


}

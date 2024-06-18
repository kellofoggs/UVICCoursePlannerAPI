package dev.kellyo.UVicCoursePlanner.service;

import dev.kellyo.UVicCoursePlanner.model.Course;
import dev.kellyo.UVicCoursePlanner.model.FullRequirementPath;
import dev.kellyo.UVicCoursePlanner.model.Requirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

//Singleton
@Service
public class CourseService {

    //Framework instantiates this class
    @Autowired
    private CourseRepository courseRepo;
    public List<Course> allCourses(){
        return courseRepo.findAll();
    }

    public CourseService getCourseService(){
        return this;
    }

    public List<Course> subCourses(List<String> code_list){
        return null;
//                courseRepo.findAllById(code_list);
    }

    public List<Course> findMultipleCoursesByCode(List<String> courseCodeList){
        List<Course> output_list = new LinkedList<>();

        for (String courseCode: courseCodeList){
            output_list.add(courseRepo.findCourseByCourseCode(courseCode).get());
        }

        return output_list;
    }

    //Some case where the course requirement no longer exists, drop that requirement from tree
    public Optional<HashMap<String, List>> constructPreReqsTree(String head_course){
        Optional<Course> head_opt = courseRepo.findCourseByCourseCode(head_course);
        if (head_opt.isPresent()){
            Course head = head_opt.get();
            FullRequirementPath path = new FullRequirementPath();

//            Optional<Requirement>  full_tree = Optional.of(head.expandedTreeWrapper(this));
//            Optional<Requirement>  full_tree = Optional.of(head.expandedTreeWrapper());

            Course course = head.clone();
            Requirement current = head.getPrereqs().clone();
            course.setPrereqs(current);
            return Optional.of(path.build_pre_req_graph(current, this));


//            return full_tree;
        }
        else
        {
            return Optional.empty();
        }

    }
    public Optional<Course> singleCourse(String course_code){
//        Course course = courseRepo.findCourseByCourseCode(course_code).get();
        return courseRepo.findCourseByCourseCode(course_code);
    }

    public Optional<Course> filterByDept(String department){

        return null;
    }


}

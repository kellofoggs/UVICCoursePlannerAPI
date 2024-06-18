package dev.kellyo.UVicCoursePlanner.controller;

import dev.kellyo.UVicCoursePlanner.model.Course;
import dev.kellyo.UVicCoursePlanner.model.Requirement;
import dev.kellyo.UVicCoursePlanner.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/v1/courses")
public class CoursesController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return new ResponseEntity<List<Course>>(courseService.allCourses(), HttpStatus.OK);
    }

    @GetMapping("/{courseCode}")
    public ResponseEntity<Optional<Course>> getCourse(@PathVariable String courseCode) {
//        System.out.println(courseCode);
        ResponseEntity<Optional<Course>> output = new ResponseEntity<Optional<Course>>(courseService.singleCourse(courseCode), HttpStatus.OK);
        System.out.println(output.getBody().get());
        return output;
    }
    @GetMapping("/filter/byDepartment{departmentName}")
    public ResponseEntity<Optional<Course>> get_filter_by_courses(){
//        ResponseEntity<Optional<Course>> output = new ResponseEntity<Optional<Course>>(courseService.singleCourse(courseCode), HttpStatus.OK);


        return null;
    }
    /**
     * @param courseCode , the course code of the course whose prereq tree requested
     * @return output
     */
    @GetMapping("/getPreReqs/{courseCode}")
    public ResponseEntity<Optional<HashMap<String, List>>> getCoursePreReqTree(@PathVariable String courseCode) {
        System.out.println(courseCode);

        Course headCourse = courseService.singleCourse(courseCode).get();
        ResponseEntity<Optional<HashMap<String, List>>> output = new ResponseEntity<Optional<HashMap<String, List>>>(courseService.constructPreReqsTree(courseCode), HttpStatus.OK);
        System.out.println(output);

        return output;
    }

    @GetMapping("/getPreReqs")
    public ResponseEntity<List<Requirement>> getAllCoursePreReqTrees() {
        List<Course> courses = courseService.allCourses();
        long time_before = System.nanoTime();
                System.out.println(courses.size());

        List<Requirement> output = new LinkedList<>();
        for (Course course : courses) {
            output.add(course.expandedTreeWrapper(courseService));

        }



        long time_after = System.nanoTime();

        long time_passed = time_after - time_before;
        //        System.out.println("Before: "+time_before);
//    pu//        System.out.println("After: "+time_after);blic ResponseEntity<Optional<CoursePath>>
        System.out.println("Elapsed time in seconds: " + time_passed / 1000000000);
        return new ResponseEntity<List<Requirement>>(output, HttpStatus.OK);
    }

    @GetMapping("/allCoursesAndDepartments")
    public ResponseEntity<Object> getAllCoursesAndDepartments(){
        LinkedList courseNames = new LinkedList<>();
        HashMap<String, LinkedList<String>> departments = new HashMap<>();
        for (Course course: courseService.allCourses()){
            String course_name = course.getCourseName();
            String subject_header = course_name.split("[0-9]")[0];
            if (!departments.containsKey(subject_header)){
                departments.put(subject_header, new LinkedList<>());

            }else{
                departments.get(subject_header).add(course_name);
            }

        }

        return new ResponseEntity<>(departments, HttpStatus.OK);





//        return null;
    }
}

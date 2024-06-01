package dev.kellyo.UVicCoursePlanner.model;

import dev.kellyo.UVicCoursePlanner.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "undergraduate_courses")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Course implements Cloneable{
    //Directed graph
    //Attributes

    /*Attributes taken straight from JSON database*/
    //The alphanumeric code of a course (i.e. CSC 110
    @Id
    private ObjectId id;

    // Made up of header ad number i.e. ENGL101 or MATH101
    @Indexed
    @HashIndexed
    private String courseCode;

    //The plain language name of a course (i.e. Fundamentals of Programming I)
    private String courseName;

    //Description of what the course is all about
    private String courseDescription;

    //String of how many credits the course is worth
    private String units;

    //Additional notes to consider about the course
    private String notes;

    //Department that offers/oversees the course
    private String department;

    //Weekly hours a course is scheduled for
    private Object hours;

    @Setter
    private Requirement prereqs;
    private Requirement coreqs;
    private List<String> prereqCourses;

//    public Course(
//            ObjectId id, String courseCode, String courseName, String courseDescription,
//            String units, String department, Object hours
//
//    ){
//        this.id = id;
//        this.courseCode = courseCode;
//        this.courseName = courseName;
//        this.courseDescription = courseDescription;
//        this.units = units;
//        this.department = department;
//        this.hours = hours;
//        this.courseList = new LinkedList<>();
//
//
//    }
//

    public boolean canTakeCourse(HashSet takenCourses) {
        //If there actually is some sort of requirement that isn't empty
        if (this.prereqs.getName() != null) {

            // If all requirements are satisfied then we can take the course
            return (this.prereqs.isSatisfied(takenCourses));

        } else {

            return true;
        }


    }


//    public void addToCourseList(String courseCode){
//        if (courseList == null){
//            courseList = new LinkedList<>();
//        }
//        courseList.add(courseCode);
//    }


    /**
     * Modified dfs wrapper for constructing full recursive pre reqs tree
     * @param courseService: reference to the course service object
     * @return
    */
    public Requirement expandedTreeWrapper(CourseService courseService){

        Course course = this.clone();
        Requirement current = this.prereqs.clone();
        course.prereqs = current;

        HashSet<Requirement> visited = new HashSet<>();

//
//        long time_before = System.nanoTime();
        this.getExpandedTree(current, visited, courseService);
//        long time_after = System.nanoTime();
//
//        long time_passed = time_after-time_before;
////        System.out.println("Before: "+time_before);
////        System.out.println("After: "+time_after);
//        System.out.println("Elapsed time in seconds: " + time_passed/1000000000);
        System.out.println(course);
        return current;

//        return prereqs;
    }


    /**
     * Recursive method that goes down sub req tree to build new sub reqs if the current requirement is a course
     * @param requirement The current requirement being looked at in the dfs progression. Can be a course, unit, 'complete' or other type
     * @param visitedSet Set that contains visited nodes for dfs
     * @param courseService: reference to the course service object
     * @return Eventually the last requirement is returned
     * */
    private Requirement getExpandedTree(Requirement requirement, HashSet<Requirement> visitedSet, CourseService courseService){
        visitedSet.add(requirement);
        ArrayList<Requirement> sub_maps = requirement.getSub_maps();

        //When we get course requirement replace that requirement's submaps with requirements of that course
        if (!requirement.checkNull() ) {
            if (requirement.getType().equals("course")) {
                String name = requirement.getName();
                Optional<Course> courseOptional = courseService.singleCourse(name);
                Course courseObj = null;
                if (courseOptional.isPresent()) {
                    courseObj = courseOptional.get();
                    sub_maps.add(courseObj.getPrereqs());
                    requirement.setSub_maps(sub_maps);
                }


            }


            for (Requirement child : requirement.getSub_maps()) {
                // If the visited set does not contain the requirement, look at the requirement
                if (!visitedSet.contains(child)) {
                    getExpandedTree(child, visitedSet, courseService);
                }
            }
        }

            return requirement;

    }

    @Override
    public boolean equals(Object otherObject){
        if (otherObject instanceof Course) {
            Course other_course = (Course) otherObject;
            return other_course.getCourseName().equals(this.getCourseName());
        }else {
            return false;
        }
    }

    @Override
    public Course clone() {
        try {
            Course clone = (Course) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                ", units='" + units + '\'' +
                ", notes='" + notes + '\'' +
                ", department='" + department + '\'' +
                ", hours=" + hours +
                ", prereqs=" + prereqs +
                ", coreqs=" + coreqs +
                '}';
    }
}




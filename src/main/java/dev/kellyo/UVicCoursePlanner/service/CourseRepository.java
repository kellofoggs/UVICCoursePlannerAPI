//CRUD repository
package dev.kellyo.UVicCoursePlanner.service;

import dev.kellyo.UVicCoursePlanner.model.Course;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Talks to mongo database
@Repository
public interface CourseRepository extends MongoRepository<Course, ObjectId> {
// SLANT FONT from https://patorjk.com/software/taag/#p=display&f=Slant&t=Create
    /*
     *    ______                __
     *   / ____/_______  ____ _/ /____
     *  / /   / ___/ _ \/ __ `/ __/ _ \
     * / /___/ /  /  __/ /_/ / /_/  __/
     * \____/_/   \___/\__,_/\__/\___/
     *
     */

    /*
     *       ____  _________    ____
     *     / __ \/ ____/   |  / __ \
     *    / /_/ / __/ / /| | / / / /
     *   / _, _/ /___/ ___ |/ /_/ /
     *  /_/ |_/_____/_/  |_/_____/
     *
     *
     */
    Optional<Course> findCourseByCourseCode(String course_code);

    List<Course> findCoursesByDepartment(String department_code);


}

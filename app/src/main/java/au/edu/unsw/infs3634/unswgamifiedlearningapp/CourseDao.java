package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM course_table")
    List<Course> getCourses();

    @Query("SELECT * FROM COURSE_TABLE WHERE courseId == :courseId")
    Course getCourse(String courseId);

    @Insert
    void insertCourse(Course... courses);

    @Delete
    void deleteAll(Course... courses);

    @Query("SELECT * FROM course_table WHERE courseName ==:courseName")
    Course getCourseByName(String courseName);

}

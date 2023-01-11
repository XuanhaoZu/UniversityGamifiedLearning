package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "course_table")
public class Course {

    public Course(@NonNull String courseId, String courseName, String courseDuration, String courseCalorie, String courseDifficulty, String courseEquipment, String courseType, String courseVideo) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDuration = courseDuration;
        this.courseCalorie = courseCalorie;
        this.courseDifficulty = courseDifficulty;
        this.courseEquipment = courseEquipment;
        this.courseType = courseType;
        this.courseVideo = courseVideo;
    }


    @PrimaryKey
    @NonNull
    @SerializedName("CourseID")
    @Expose
    private String courseId;

    @SerializedName("CourseName")
    @Expose
    private String courseName;

    @SerializedName("Duration")
    @Expose
    private String courseDuration;

    @SerializedName("Calorie")
    @Expose
    private String courseCalorie;

    @SerializedName("Difficulty")
    @Expose
    private String courseDifficulty;

    @SerializedName("Equipment")
    @Expose
    private String courseEquipment;


    @SerializedName("Type")
    @Expose
    private String courseType;

    @SerializedName("Video")
    @Expose
    private String courseVideo;


    @NonNull
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(@NonNull String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getCourseCalorie() {
        return courseCalorie;
    }

    public void setCourseCalorie(String courseCalorie) {
        this.courseCalorie = courseCalorie;
    }

    public String getCourseDifficulty() {
        return courseDifficulty;
    }

    public void setCourseDifficulty(String courseDifficulty) {
        this.courseDifficulty = courseDifficulty;
    }

    public String getCourseEquipment() {
        return courseEquipment;
    }

    public void setCourseEquipment(String courseEquipment) {
        this.courseEquipment = courseEquipment;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseVideo() {
        return courseVideo;
    }

    public void setCourseVideo(String courseVideo) {
        this.courseVideo = courseVideo;
    }


    public static ArrayList<Course> getCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(new Course("1", "Descending Intervals Bodyweight HIIT Circuits", "36", "209-363", "4", "No Equipment", "Plyometric", "fKMGZW8cA1U"));
        courses.add(new Course("2", "Fat Burning HIIT Cardio Workout", "38", "290-489", "4", "No Equipment", "Cardiovascular", "QOHJTIIfs9g"));
        courses.add(new Course("3", "Easy Warm-up Cardio", "6", "23-42", "1", "No Equipment", "Warm-up", "R0mMyV5OtcM"));
        courses.add(new Course("4", "Lower Back Stretching Routine", "5", "15-20", "1", "Mat, No Equipment", "Flexibility", "9iljr_dEUPY"));
        courses.add(new Course("5", "Pilates Flow - Lower Body Pilates Workout", "14", "68-107", "2", "Exercise Band", "Toning", "jBBE6kQ2hVk"));
        courses.add(new Course("6", "Shoulder and Neck Exercises and Stretches", "34", "74-110", "2", "Mat, Physio-ball", "Flexibility", "ZvQn0y-_k_k"));
        courses.add(new Course("7", "Mass Building Lower Body Workout", "33", "142-318", "3", "Dumbbell", "Strength Training", "bosWj6aPRBc"));
        courses.add(new Course("8", "Fun Upper Body Workout for Great Arms & Shoulders", "28", "140-290", "3", "Dumbbell", "Strength Training", "wCbI4zEbW5M"));
        courses.add(new Course("9", "Lower Body HIIT and Strength Workout - Intense ", "56", "381-597", "5", "Dumbbell, No Equipment", "Strength Training, Toning", "g6ALZjh8nDs"));
        courses.add(new Course("10", "1000 Calorie Workout Video", "93", "810-1260", "5", "Dumbbell, Mat", "HIIT, Plyometric, Strength Training", "OgFaIqVQPsE"));

        return courses;
    }

}

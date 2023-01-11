package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "plan_table")
public class Plan {

    public Plan(@NonNull int planId, String courseName, String days, String progress, String userId) {
        this.planId = planId;
        this.courseName = courseName;
        this.days = days;
        this.progress = progress;
        this.userId = userId;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @SerializedName("PlanID")
    @Expose
    private int planId;

    @SerializedName("CourseName")
    @Expose
    private String courseName;

    @SerializedName("Days")
    @Expose
    private String days;

    @SerializedName("Progress")
    @Expose
    private String progress;

    @SerializedName("UserID")
    @Expose
    private String userId;


    @NonNull
    public int getPlanId() {
        return planId;
    }

    public void setPlanId(@NonNull int planId) {
        this.planId = planId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}


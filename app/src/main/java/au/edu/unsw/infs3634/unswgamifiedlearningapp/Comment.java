package au.edu.unsw.infs3634.unswgamifiedlearningapp;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "comment_table")
public class Comment {


    public Comment(int cid, String content, String courseId, String userId) {

        this.cid = cid;
        this.content = content;
        this.courseId = courseId;
        this.userId = userId;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @SerializedName("CommentID")
    @Expose
    private int cid;

    @SerializedName("Content")
    @Expose
    private String content;

    @SerializedName("CourseId")
    @Expose
    private String courseId;

    @SerializedName("UserId")
    @Expose
    private String userId;


    @NonNull
    public int getCid() {
        return cid;
    }

    public void setCid(@NonNull int cid) {
        this.cid = cid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String useId) {
        this.userId = useId;
    }


}

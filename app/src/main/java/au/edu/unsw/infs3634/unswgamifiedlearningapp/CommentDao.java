package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CommentDao {
    @Query("SELECT * FROM comment_table WHERE courseId == :courseId")
    List<Comment> getComments(String courseId);

    @Insert
    void insertComment(Comment comment);
}

package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class, Plan.class, User.class, Comment.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CourseDao courseDao();

    public abstract PlanDao planDao();

    public abstract UserDao userDao();

    public abstract CommentDao commentDao();
}

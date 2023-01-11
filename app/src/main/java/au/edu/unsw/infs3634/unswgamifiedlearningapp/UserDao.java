package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user_table WHERE zid == :zid")
    User getUser(String zid);

    @Insert
    void insetUser(User user);

}

package au.edu.unsw.infs3634.unswgamifiedlearningapp;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlanDao {
    //Retrieve plans of specific user
    @Query("SELECT * FROM plan_table WHERE userId == :userId and days > 0 and ((progress-1) <days)")
    List<Plan> getPlans(String userId);

    @Insert
    void insertPlan(Plan plan);

    //Update progress of specific plan
    @Query("UPDATE plan_table SET progress = progress+1 WHERE userId = :userId AND courseName = :courseName")
    void update(String userId, String courseName);

    ////Retrieve plans by userId & courseName
    @Query("SELECT * FROM plan_table WHERE userId == :userId AND courseName == :courseName")
    Plan getPlan(String userId, String courseName);

    //Update plan if user reset
    @Query("UPDATE plan_table SET days = :days WHERE userId = :userId AND courseName = :courseName")
    void changePlan(String userId, String courseName, String days);

    //sum of progress group by user
    @Query("select planId, courseName, sum(progress) as progress,sum(days) as days,userId from plan_table Group by userId Order by sum(progress) DESC;")
    List<Plan> getRanking();

    //get plan by planId
    @Query("SELECT * from plan_table WHERE planId ==:planId")
    Plan getPlanById(String planId);

}

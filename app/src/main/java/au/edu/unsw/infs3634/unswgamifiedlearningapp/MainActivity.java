package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    //    private MyDatabaseHelper dbHelper;
    public static String Userid;
    private TextView signIn;
    private EditText zid, psw;
    private AppDatabase mDb;
    private Button btnLogin;
    private ArrayList<Course> mCourses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-database").build();


        zid = findViewById(R.id.etUserzid);
        psw = findViewById(R.id.etPsw);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        User user = mDb.userDao().getUser(String.valueOf(zid.getText()));
                        try {
                            if (String.valueOf(psw.getText()).equals(String.valueOf(user.getPassword()))) {
                                Userid = String.valueOf(zid.getText());
                                Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Login failed. Please check zid and password again.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Login failed. Please check zid and password again.", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });
            }
        });


        //add sample data
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //user sample
                    mDb.userDao().insetUser(new User("z0000001", "Evelyn", "z0000001", "evelyn@gmail.com", R.drawable.avatar1));
                    mDb.userDao().insetUser(new User("z0000002", "Tony", "z0000002", "tony@gmail.com", R.drawable.avatar2));
                    mDb.userDao().insetUser(new User("z0000003", "Cindy", "z0000003", "cindy@gmail.com", R.drawable.avatar3));

                    //plan sample
                    mDb.planDao().insertPlan(new Plan(0, "Descending Intervals Bodyweight HIIT Circuits", "5", "4", "z0000001"));
                    mDb.planDao().insertPlan(new Plan(0, "Fat Burning HIIT Cardio Workout", "4", "3", "z0000001"));
                    mDb.planDao().insertPlan(new Plan(0, "Easy Warm-up Cardio", "15", "3", "z0000001"));

                    mDb.planDao().insertPlan(new Plan(0, "Descending Intervals Bodyweight HIIT Circuits", "5", "1", "z0000002"));
                    mDb.planDao().insertPlan(new Plan(0, "Fat Burning HIIT Cardio Workout", "4", "0", "z0000002"));
                    mDb.planDao().insertPlan(new Plan(0, "Easy Warm-up Cardio", "5", "1", "z0000002"));


                    mDb.planDao().insertPlan(new Plan(0, "Descending Intervals Bodyweight HIIT Circuits", "20", "20", "z0000003"));
                    mDb.planDao().insertPlan(new Plan(0, "Fat Burning HIIT Cardio Workout", "10", "1", "z0000003"));
                    mDb.planDao().insertPlan(new Plan(0, "Easy Warm-up Cardio", "8", "8", "z0000003"));

                    //comment sample
                    mDb.commentDao().insertComment(new Comment(0,"Good course!","5","z0000001"));
                    mDb.commentDao().insertComment(new Comment(0,"Very useful.","5","z0000002"));
                    mDb.commentDao().insertComment(new Comment(0,"Moderate amount of exercise.","6","z0000003"));
                    mDb.commentDao().insertComment(new Comment(0,"I like the course.","6","z0000001"));
                    mDb.commentDao().insertComment(new Comment(0,"It's a bit of a workout for me","7","z0000002"));
                    mDb.commentDao().insertComment(new Comment(0,"good","7","z0000003"));


                    mCourses = Course.getCourses();
                    for (Course course : mCourses) {
                        mDb.courseDao().insertCourse(course);
                    }



                } catch (Exception e) {

                }


            }
        });


        //Go to Sign up screen
        signIn = findViewById(R.id.tvSignIn);
        signIn.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });


    }


}
package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class PlanActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    public static final String INTENT_MESSAGE1 = "planActivity";
    private static final String TAG = "PlanActivity";
    private RecyclerView mRecyclerView;
    private PlanAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private AppDatabase mDb;
    private Button btnShareProgress;
    private EditText email;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-database").build();


        mRecyclerView = findViewById(R.id.rvPlans);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // Implement the ClickListener for the adapter
        PlanAdapter.RecyclerViewClickListener listener = new PlanAdapter.RecyclerViewClickListener() {
            @Override
            public void onPlanClick(View view, String planId) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Plan plan = mDb.planDao().getPlanById(planId);
                        Course course = mDb.courseDao().getCourseByName(plan.getCourseName());
                        launchClassDetailActivity(course.getCourseId());
                    }
                });

            }
        };


        // Plan RecyclerView
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<Plan> plans = (ArrayList<Plan>) mDb.planDao().getPlans(MainActivity.Userid);

                // if no plans yet
                if (plans.size() == 0) {
                    TextView noPlan = findViewById(R.id.tvNoPlan);
                    noPlan.setVisibility(View.VISIBLE);
                    noPlan.setText("You don't have a plan yet");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new PlanAdapter(plans, listener);
                        mRecyclerView.setAdapter(mAdapter);


                        //Share My Progress function
                        btnShareProgress = findViewById(R.id.btnPShareProgress);
                        //get email address
                        email = findViewById(R.id.etPemail);
                        String emailAddress = String.valueOf(email.getText());
                        btnShareProgress.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(Intent.ACTION_SEND);
                                i.setType("message/rfc822");
                                i.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
                                i.putExtra(Intent.EXTRA_SUBJECT, "Share Gamified Learning Progress");
                                i.putExtra(Intent.EXTRA_TEXT, getPlan(plans));
                                try {
                                    startActivity(Intent.createChooser(i, "Send mail..."));
                                } catch (android.content.ActivityNotFoundException ex) {

                                }
                            }
                        });

                    }
                });

            }
        });


        bottomNavigationView = findViewById(R.id.bottomNav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent = null;
                switch (menuItem.getItemId()) {
                    case R.id.recommend:
                        intent = new Intent(PlanActivity.this, ArticleActivity.class);
                        break;
                    case R.id.course:
                        intent = new Intent(PlanActivity.this, CourseActivity.class);
                        break;
                    case R.id.plan:
                        intent = new Intent(PlanActivity.this, PlanActivity.class);
                        break;
                    case R.id.ranking:
                        intent = new Intent(PlanActivity.this, RankingActivity.class);
                        break;
                    case R.id.profile:
                        intent = new Intent(PlanActivity.this, ProfileActivity.class);
                        break;
                }
                startActivity(intent);
                return true;
            }
        });
    }

    private void launchClassDetailActivity(String message) {
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra(CourseDetailActivity.INTENT_FROM_PLAN, message);
        startActivity(intent);
    }


    //String of plan sharing
    public String getPlan(ArrayList<Plan> plans) {
        String s = null;
        for (int i = 0; i < plans.size(); i++) {
            s = s + plans.get(i).getCourseName() + ": " + plans.get(i).getProgress() + "/" + plans.get(i).getDays() + "\n";
        }
        return s;
    }
}
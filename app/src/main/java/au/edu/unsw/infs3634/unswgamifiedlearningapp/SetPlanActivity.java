package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.Executors;

public class SetPlanActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    public static final String TOSETPLAN_MESSAGE = "au.edu.unsw.infs3634.unswgamifiedlearningapp.intent_message";
    private TextView name, type, difficulty, cal, equipment, duration;
    private Button btnSave, btnCancel;
    EditText mPlan;
    private AppDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setplan);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        name = findViewById(R.id.tvSPName);
        type = findViewById(R.id.tvSPType);
        difficulty = findViewById(R.id.tvSPDifficulty);
        duration = findViewById(R.id.tvSPDuration);
        cal = findViewById(R.id.tvSPCalorie);
        equipment = findViewById(R.id.tvSPEquipment);
        mPlan = findViewById(R.id.etSPTotalTimes);
        btnSave = findViewById(R.id.btnSPSave);
        btnCancel = findViewById(R.id.btnSpCancel);

        mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-database").build();

        //Get String from intent
        name.setText((String) b.get("name"));
        type.setText((String) b.get("type"));
        difficulty.setText((String) b.get("difficulty"));
        duration.setText((String) b.get("duration"));
        cal.setText((String) b.get("calorie"));
        equipment.setText((String) b.get("equipment"));


        //Save new plan
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String times = String.valueOf(mPlan.getText());

                if (isNumeric(times)) {
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            Plan oldPlan = mDb.planDao().getPlan(MainActivity.Userid, (String) b.get("name"));
                            if (oldPlan == null) {
                                Plan plan = new Plan(0, (String) b.get("name"), times, "0", MainActivity.Userid);
                                mDb.planDao().insertPlan(plan);
                                Log.d("TAG", "run:insertplan ");
                            } else {
                                mDb.planDao().changePlan(MainActivity.Userid, (String) b.get("name"), times);
                            }
                        }
                    });
                    Toast.makeText(SetPlanActivity.this, "Set plan successful", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(SetPlanActivity.this, "Please enter a valid number.", Toast.LENGTH_SHORT).show();
                    mPlan.setText("");
                }
            }
        });


        //Cancel button - back to courseDetail Activity
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //bottom navigation bar
        bottomNavigationView = findViewById(R.id.bottomNav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent = null;
                switch (menuItem.getItemId()) {
                    case R.id.recommend:
                        intent = new Intent(SetPlanActivity.this, ArticleActivity.class);
                        break;
                    case R.id.course:
                        intent = new Intent(SetPlanActivity.this, CourseActivity.class);
                        break;
                    case R.id.plan:
                        intent = new Intent(SetPlanActivity.this, PlanActivity.class);
                        break;
                    case R.id.ranking:
                        intent = new Intent(SetPlanActivity.this, RankingActivity.class);
                        break;
                    case R.id.profile:
                        intent = new Intent(SetPlanActivity.this, ProfileActivity.class);
                        break;
                }
                startActivity(intent);
                return true;
            }
        });
    }


    //Check the input of plan days
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

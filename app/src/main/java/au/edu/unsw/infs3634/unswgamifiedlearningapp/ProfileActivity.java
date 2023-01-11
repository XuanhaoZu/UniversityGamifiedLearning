package au.edu.unsw.infs3634.unswgamifiedlearningapp;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private AppDatabase mDb;
    private ImageView iv1, iv2, iv3, iv5, iv10, iv20;
    private EditText weight, height;
    private Button btn;
    private TextView tvResult, tvMyMedal;
    private int day = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("My Profile");
        ImageView ivProfile = findViewById(R.id.ivProfile);
        TextView tvName = findViewById(R.id.tvUserName);
        TextView tvZid = findViewById(R.id.tvZid);
        TextView tvEmail = findViewById(R.id.tvEmail);

        mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-database").build();

        //User Profile - basic information
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                User user = mDb.userDao().getUser(MainActivity.Userid);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(ProfileActivity.this)
                                .load(user.getImageId())
                                .circleCrop()
                                .into(ivProfile);
                    }
                });
                tvZid.setText(user.getZid());
                tvName.setText(user.getName());
                tvEmail.setText(user.getEmail());
            }
        });


        //User profile - My Medal
        iv1 = findViewById(R.id.ivR1);
        iv2 = findViewById(R.id.ivR2);
        iv3 = findViewById(R.id.ivR3);
        iv5 = findViewById(R.id.ivR5);
        iv10 = findViewById(R.id.ivR10);
        iv20 = findViewById(R.id.ivR20);


        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<Plan> plans = (ArrayList<Plan>) mDb.planDao().getPlans(MainActivity.Userid);
//                Log.d("progress", plans.get(0).getDays()+"///"+plans.get(0).getProgress());

                for (int i = 0; i < plans.size(); i++) {
                    Log.d("progress", plans.get(i).getDays() + "/" + plans.get(i).getProgress());
                    if (plans.get(i).getDays().equals(plans.get(i).getProgress())) {
                        day++;
                        Log.d("progress", "DAY++");
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("progress", String.valueOf(day));
                        switch (day) {
                            case 0:
                                tvMyMedal = findViewById(R.id.tvMedal);
                                tvMyMedal.setText("My Medal - You don't have a medal yet.");
                                break;
                            case 1:
                                iv1.setVisibility(View.VISIBLE);
                                break;
                            case 2:
                                iv1.setVisibility(View.VISIBLE);
                                iv2.setVisibility(View.VISIBLE);
                                break;
                            case 3:
                                iv1.setVisibility(View.VISIBLE);
                                iv2.setVisibility(View.VISIBLE);
                                iv3.setVisibility(View.VISIBLE);
                                break;
                            case 5:
                                iv1.setVisibility(View.VISIBLE);
                                iv2.setVisibility(View.VISIBLE);
                                iv3.setVisibility(View.VISIBLE);
                                iv5.setVisibility(View.VISIBLE);
                                break;
                            case 10:
                                iv1.setVisibility(View.VISIBLE);
                                iv2.setVisibility(View.VISIBLE);
                                iv3.setVisibility(View.VISIBLE);
                                iv5.setVisibility(View.VISIBLE);
                                iv10.setVisibility(View.VISIBLE);
                                break;
                            case 20:
                                iv1.setVisibility(View.VISIBLE);
                                iv2.setVisibility(View.VISIBLE);
                                iv3.setVisibility(View.VISIBLE);
                                iv5.setVisibility(View.VISIBLE);
                                iv10.setVisibility(View.VISIBLE);
                                iv20.setVisibility(View.VISIBLE);
                                break;

                        }

                    }
                });


            }
        });


        //BMI calculator
        weight = findViewById(R.id.etWeight);
        height = findViewById(R.id.etHeight);
        btn = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double w = Double.parseDouble(String.valueOf(weight.getText()));
                    double h = Double.parseDouble(String.valueOf(height.getText()));
                    double result = (w / (h * h)) * 10000;

                    String s;
                    if (result < 18.5) {
                        s = "Your result is " + result + ", " + "Underweight.";
                    } else if (result < 25) {
                        s = "Your result is " + result + ", " + "Healthy Weight";

                    } else if (result < 30) {
                        s = "Your result is " + result + ", " + "Overweight.";
                    } else {
                        s = "Your result is " + result + ", " + "Obese.";
                    }
                    tvResult.setText(s);
                } catch (Exception e) {
                    Toast.makeText(ProfileActivity.this, "Please enter valid numbers.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //bottom Navigation Bar
        bottomNavigationView = findViewById(R.id.bottomNav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent = null;
                switch (menuItem.getItemId()) {
                    case R.id.recommend:
                        intent = new Intent(ProfileActivity.this, ArticleActivity.class);
                        break;
                    case R.id.course:
                        intent = new Intent(ProfileActivity.this, CourseActivity.class);
                        break;
                    case R.id.plan:
                        intent = new Intent(ProfileActivity.this, PlanActivity.class);
                        break;
                    case R.id.ranking:
                        intent = new Intent(ProfileActivity.this, RankingActivity.class);
                        break;
                    case R.id.profile:
                        intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                        break;
                }
                startActivity(intent);
                return true;
            }
        });
    }
}

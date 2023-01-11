package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class RankingActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private RecyclerView mRecyclerView;
    private RankingAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private AppDatabase mDb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        setTitle("Ranking");
        mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-database").build();

        //Ranking
        // Get a handle to the RecyclerView
        mRecyclerView = findViewById(R.id.lvRanking);
        mRecyclerView.setHasFixedSize(true);
        // Set the layout manager (Linear)
        layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(layoutManager);
        // Implement the ClickListener for the adapter
        RankingAdapter.RecyclerViewClickListener listener = new RankingAdapter.RecyclerViewClickListener() {
            @Override
            public void onPlanClick(View view, String courseId) {
            }
        };
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<Plan> ranking = (ArrayList<Plan>) mDb.planDao().getRanking();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new RankingAdapter(ranking, listener);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });


            }
        });


        //Bottom Navigation Bar
        bottomNavigationView = findViewById(R.id.bottomNav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent = null;
                switch (menuItem.getItemId()) {
                    case R.id.recommend:
                        intent = new Intent(RankingActivity.this, ArticleActivity.class);
                        break;
                    case R.id.course:
                        intent = new Intent(RankingActivity.this, CourseActivity.class);
                        break;
                    case R.id.plan:
                        intent = new Intent(RankingActivity.this, PlanActivity.class);
                        break;
                    case R.id.ranking:
                        intent = new Intent(RankingActivity.this, RankingActivity.class);
                        break;
                    case R.id.profile:
                        intent = new Intent(RankingActivity.this, ProfileActivity.class);
                        break;
                }
                startActivity(intent);
                return true;
            }
        });
    }

}

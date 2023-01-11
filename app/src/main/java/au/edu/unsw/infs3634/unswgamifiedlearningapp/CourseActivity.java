package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class CourseActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private static final String TAG = "CourseActivity";
    private RecyclerView mRecyclerView;
    private CourseAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private AppDatabase mDb;
//    private ArrayList<Course> mCourses = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        setTitle("Fitness Courses");

        // Get a handle to the RecyclerView
        mRecyclerView = findViewById(R.id.rvCourses);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // Implement the ClickListener for the adapter
        CourseAdapter.RecyclerViewClickListener listener = new CourseAdapter.RecyclerViewClickListener() {
            @Override
            public void onCourseClick(View view, String courseCode) {
                launchClassDetailActivity(courseCode);
            }
        };
        mAdapter = new CourseAdapter(new ArrayList<Course>(), listener);
        mRecyclerView.setAdapter(mAdapter);


        //Database - CourseDatabase
        mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-database").build();


        //Get a list of course for RecyclerView
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<Course> courses = (ArrayList<Course>) mDb.courseDao().getCourses();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setCourse(courses);
                        mAdapter.sort(CourseAdapter.SORT_METHOD_NAME);
                    }
                });

            }
        });


        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final ArrayList<Course> courses = (ArrayList<Course>) mDb.courseDao().getCourses();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setCourse(courses);
                        mAdapter.sort(CourseAdapter.SORT_METHOD_NAME);

                    }
                });

            }
        });






        //bottomNavigationView
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
                        intent = new Intent(CourseActivity.this, ArticleActivity.class);
                        break;
                    case R.id.course:
                        intent = new Intent(CourseActivity.this, CourseActivity.class);
                        break;
                    case R.id.plan:
                        intent = new Intent(CourseActivity.this, PlanActivity.class);
                        break;
                    case R.id.ranking:
                        intent = new Intent(CourseActivity.this, RankingActivity.class);
                        break;
                    case R.id.profile:
                        intent = new Intent(CourseActivity.this, ProfileActivity.class);
                        break;
                }
                startActivity(intent);
                return true;
            }
        });

    }


    // Go to CourseDetail Activity
    private void launchClassDetailActivity(String message) {
        Intent intent = new Intent(this, CourseDetailActivity.class);
        intent.putExtra(CourseDetailActivity.INTENT_MESSAGE, message);
        startActivity(intent);
    }


    // Instantiate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.class_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.class_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    // React to user interaction with the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_name:
                // sort by new cases
                mAdapter.sort(CourseAdapter.SORT_METHOD_NAME);
                return true;
            case R.id.sort_difficulty:
                // sort by total cases
                mAdapter.sort(CourseAdapter.SORT_METHOD_DIFFICULTY);
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
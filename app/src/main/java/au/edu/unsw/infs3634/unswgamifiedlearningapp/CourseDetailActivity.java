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
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class CourseDetailActivity extends AppCompatActivity {
    public static final String INTENT_FROM_PLAN = "fromPlan";
    private static final String TAG = "CourseDetailActivity";
    public static final String INTENT_MESSAGE = "au.edu.unsw.infs3634.unswgamifiedlearningapp.intent_message";
    private TextView courseName;
    private TextView mCourseTitle;
    private EditText etComment;
    private VideoView courseVideo;
    BottomNavigationView bottomNavigationView;
    private Button btnFinish, btnAddCourse, btnAddComment;
    private AppDatabase mDb;
    private YouTubePlayerTracker tracker;
    private String name;
    private String courseId;
    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursedetail);
        btnAddCourse = findViewById(R.id.btnAddToPlan);
        btnFinish = findViewById(R.id.btnDFinish);


        // Instantiate a CountryDatabase object for "country-database"
        mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-database").build();


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        if (intent.hasExtra(INTENT_MESSAGE)) {

            courseId = intent.getStringExtra(INTENT_MESSAGE);

        } else if (intent.hasExtra(INTENT_FROM_PLAN)) {

            courseId = intent.getStringExtra(INTENT_FROM_PLAN);
        }

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Course course = mDb.courseDao().getCourse(courseId);
                // Update the view in UI Thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setTitle(course.getCourseName());

                        YouTubePlayerView youTubePlayerView = findViewById(R.id.videoView);
                        youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> {
                            youTubePlayer.loadVideo(course.getCourseVideo(), 0);

                            //track video duration then update progress of plan
                            tracker = new YouTubePlayerTracker();

                            youTubePlayer.addListener(tracker);

                            //finish and record progress
                            btnFinish.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (tracker.getCurrentSecond() * 100 / tracker.getVideoDuration() > 50) {
                                        Log.d(TAG, String.valueOf(tracker.getCurrentSecond() * 100 / tracker.getVideoDuration()));
                                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDb.planDao().update(MainActivity.Userid, course.getCourseName());
                                                Log.d(TAG, "progress +1");
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(CourseDetailActivity.this, "Congratulation! Your progress +1!", Toast.LENGTH_SHORT);
                                                        launchPlanActivity();
                                                    }
                                                });

                                            }
                                        });

                                    } else {
                                        Toast.makeText(CourseDetailActivity.this, "You haven't spent enough time exercising with videos!", Toast.LENGTH_SHORT);
                                        launchPlanActivity();
                                    }


                                }
                            });


                        });

                        //intent message for setplan activity
                        bundle.putString("name", course.getCourseName());
                        bundle.putString("difficulty", course.getCourseDifficulty());
                        bundle.putString("duration", course.getCourseDuration());
                        bundle.putString("calorie", course.getCourseCalorie());
                        bundle.putString("type", course.getCourseType());
                        bundle.putString("equipment", course.getCourseEquipment());


                    }
                });
            }
        });


        //Comment function
        mRecyclerView = findViewById(R.id.rvComment);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        CommentAdapter.RecyclerViewClickListener listener = new CommentAdapter.RecyclerViewClickListener() {
            @Override
            public void onCommentClick(View view, String code) {
            }
        };

        mAdapter = new CommentAdapter(new ArrayList<Comment>(), listener);
        mRecyclerView.setAdapter(mAdapter);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<Comment> comments = (ArrayList<Comment>) mDb.commentDao().getComments(courseId);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setComments(comments);
                    }
                });


                //Add new comment
                btnAddComment = findViewById(R.id.btnCommentSend);
                etComment = findViewById(R.id.etComment);
                btnAddComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s = String.valueOf(etComment.getText());
                        Comment comment = new Comment(0, s, courseId, MainActivity.Userid);
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                mDb.commentDao().insertComment(comment);
                                ArrayList<Comment> commentsNew = (ArrayList<Comment>) mDb.commentDao().getComments(courseId);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mAdapter.setComments(commentsNew);
                                    }
                                });

                            }
                        });

                    }
                });


            }
        });


        //Add new plan --> go to setPlanActivity
        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSetPlanActivity(bundle);
            }
        });


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
                        intent = new Intent(CourseDetailActivity.this, ArticleActivity.class);
                        break;
                    case R.id.course:
                        intent = new Intent(CourseDetailActivity.this, CourseActivity.class);
                        break;
                    case R.id.plan:
                        intent = new Intent(CourseDetailActivity.this, PlanActivity.class);
                        break;
                    case R.id.ranking:
                        intent = new Intent(CourseDetailActivity.this, RankingActivity.class);
                        break;
                    case R.id.profile:
                        intent = new Intent(CourseDetailActivity.this, ProfileActivity.class);
                        break;
                }
                startActivity(intent);
                return true;
            }
        });

    }


    // Called when the user taps the Launch SetPlanActivity button
    private void launchSetPlanActivity(Bundle bundle) {
        Intent intent = new Intent(this, SetPlanActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    private void launchPlanActivity() {
        Intent intent = new Intent(this, PlanActivity.class);
        startActivity(intent);
    }


}

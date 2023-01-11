package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private RecyclerView recRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArticleAdapter mAdapter;
    private static final String TAG = "ArticleActivity";
    private ArrayList<Article> articles;
    private ProgressBar pbWaiting;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        setTitle("Daily Health News");

        //set loading progress bar
        pbWaiting = findViewById(R.id.pbWaiting);

        //Article recyclerView for list of health news
        recRecyclerView = findViewById(R.id.recyclerViewRec);
        recRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recRecyclerView.setLayoutManager(layoutManager);

        ArticleAdapter.RecyclerViewClickListener listener = new ArticleAdapter.RecyclerViewClickListener() {
            @Override
            public void onArticleClick(View view, String url) {

                try {
                    Log.d(TAG, url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                }
            }
        };

        mAdapter = new ArticleAdapter(new ArrayList<Article>(), listener);


        //Retrofit for News api
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ArticleService service = retrofit.create(ArticleService.class);

        Call<Response> responseCall = service.getResponse("health", "us", "385aafffb0d74093bb661fbd34bfc025");

        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.d(TAG, "API call successful");
                Log.d(TAG, response.body().getStatus());
                articles = (ArrayList<Article>) response.body().getArticles();

                mAdapter.setArticle(articles);

                //remove loading progressbar
                pbWaiting.setVisibility(View.GONE);

                recRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d(TAG, "API call failed");
                Toast.makeText(ArticleActivity.this, "There has been an error. Please check your network connection.", Toast.LENGTH_SHORT).show();

            }
        });


        //navigation bar
        bottomNavigationView = findViewById(R.id.bottomNav);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent = null;
                switch (menuItem.getItemId()) {
                    case R.id.recommend:
                        intent = new Intent(ArticleActivity.this, ArticleActivity.class);
                        break;
                    case R.id.course:
                        intent = new Intent(ArticleActivity.this, CourseActivity.class);
                        break;
                    case R.id.plan:
                        intent = new Intent(ArticleActivity.this, PlanActivity.class);
                        break;
                    case R.id.ranking:
                        intent = new Intent(ArticleActivity.this, RankingActivity.class);
                        break;
                    case R.id.profile:
                        intent = new Intent(ArticleActivity.this, ProfileActivity.class);
                        break;
                }
                startActivity(intent);
                return true;
            }
        });
    }


}

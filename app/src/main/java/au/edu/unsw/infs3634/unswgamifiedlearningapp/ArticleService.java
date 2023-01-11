package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleService {
    @GET("top-headlines")
    Call<Response> getResponse(@Query("category") String category, @Query("country") String country, @Query("apiKey") String apiKey);
}

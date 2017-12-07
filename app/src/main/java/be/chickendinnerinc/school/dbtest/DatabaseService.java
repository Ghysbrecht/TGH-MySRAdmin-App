package be.chickendinnerinc.school.dbtest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Thomas on 7/12/2017.
 */

public interface DatabaseService {
    //Get all highscores
    @GET("leaderboards.json")
    Call<List<Score>> allScores();
}

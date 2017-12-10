package be.chickendinnerinc.school.dbtest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Thomas on 7/12/2017.
 */

public interface DatabaseService {
    //Get all highscores
    @GET("leaderboards.json")
    Call<List<Score>> allScores();

    //Create a user
    @POST("participants.json")
    Call<String> createJob(@Body Participant participant);
}

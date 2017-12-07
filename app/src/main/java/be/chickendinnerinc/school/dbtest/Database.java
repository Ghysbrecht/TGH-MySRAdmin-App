package be.chickendinnerinc.school.dbtest;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Thomas on 7/12/2017.
 */

public class Database {
    private DatabaseService databaseService;
    private IScoreListener listener;

    Database(String url){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        databaseService = retrofit.create(DatabaseService.class);
    }

    public void getAllHigschores(IScoreListener endpoint){
        this.listener = endpoint;
        Call<List<Score>> call = databaseService.allScores();
        call.enqueue(new Callback<List<Score>>() {
            @Override
            public void onResponse(Call<List<Score>> call, Response<List<Score>> response) {
                if (response.body() != null) {
                    listener.populate(response.body());
                } else {
                    Log.e("REST", "HTTP REST Request returned no data to parse");
                }
            }
            @Override
            public void onFailure(Call<List<Score>> call, Throwable t) {
                Log.e("REST", "HTTP REST Request failed: " + t);
            }
        });
    }
}

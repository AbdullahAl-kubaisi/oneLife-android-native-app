package ch.zli.aa.onelife_fitnesstracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.zli.aa.onelife_fitnesstracker.R;
import ch.zli.aa.onelife_fitnesstracker.adapters.TutorialAdapterHome;
import ch.zli.aa.onelife_fitnesstracker.models_tutorials.JSON_TutorialResponseModel;
import ch.zli.aa.onelife_fitnesstracker.models_tutorials.VideoYT;
import ch.zli.aa.onelife_fitnesstracker.network.YoutubeAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class rvLearnFragmentNotFrag extends AppCompatActivity {

    Context context;
    List<VideoYT> tutorials;
    public TutorialAdapterHome tutorialAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rv_learn_not);

        RecyclerView rvTutorials = findViewById(R.id.rvTutorials);
        tutorials = new ArrayList<>();

        tutorialAdapter = new TutorialAdapterHome(context, tutorials);
        rvTutorials.setAdapter(tutorialAdapter);
        rvTutorials.setLayoutManager(new LinearLayoutManager(context));

        if(tutorials.size() == 0) {
            getJson();
        }
    }

    private void getJson() {
        String category = getIntent().getStringExtra("category");
        Log.i("rvLearnFragmentNotFrag", "|||||||||||||||||||category: " + category);

        String YT_API_KEY = YoutubeAPI.API_KEY + getString(R.string.YOUTUBE_API);
        String url;

        switch (category) {
            case "StretchingBefore_query":
                url = YoutubeAPI.BASE_URL + YoutubeAPI.search + YoutubeAPI.part + YoutubeAPI.max + YoutubeAPI.order
                        + YoutubeAPI.StretchingBefore_query + YoutubeAPI.type + YT_API_KEY;
                break;
            case "Yoga_query":
                url = YoutubeAPI.BASE_URL + YoutubeAPI.search + YoutubeAPI.part + YoutubeAPI.max + YoutubeAPI.order
                        + YoutubeAPI.Yoga_query + YoutubeAPI.type + YT_API_KEY;
                break;
            case "Pilates_query":
                url = YoutubeAPI.BASE_URL + YoutubeAPI.search + YoutubeAPI.part + YoutubeAPI.max + YoutubeAPI.order
                        + YoutubeAPI.Pilates_query + YoutubeAPI.type + YT_API_KEY;
            default:
                url = YoutubeAPI.BASE_URL + YoutubeAPI.search + YoutubeAPI.part + YoutubeAPI.max + YoutubeAPI.order
                        + YoutubeAPI.Workout_query + YoutubeAPI.type + YT_API_KEY;
                break;
        }
        Call<JSON_TutorialResponseModel> data = YoutubeAPI.getVideoAPI().getYT(url);
        data.enqueue(new Callback<JSON_TutorialResponseModel>() {
            @Override
            public void onResponse(Call<JSON_TutorialResponseModel> call, Response<JSON_TutorialResponseModel> response) {
                if (response.errorBody() == null) {
                    JSON_TutorialResponseModel responseModel = response.body();

                    tutorials.addAll(responseModel.getItems());
                    tutorialAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JSON_TutorialResponseModel> call, Throwable t) { }
        });
    }
}
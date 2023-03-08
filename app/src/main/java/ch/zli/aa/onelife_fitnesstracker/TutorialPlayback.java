package ch.zli.aa.onelife_fitnesstracker;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;

import ch.zli.aa.onelife_fitnesstracker.models_tutorials.VideoYT;


public class TutorialPlayback extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;
    TextView tvTitle, tvDescription, tvPublishedAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_playback);
        tvTitle = findViewById(R.id.tvTitlePlayback);
        tvDescription = findViewById(R.id.tvDescriptionPlayback);
        youTubePlayerView = findViewById(R.id.player);
        tvPublishedAt = findViewById(R.id.tvPublishedAt);

        VideoYT videoYT = (VideoYT) Parcels.unwrap(getIntent().getParcelableExtra("videoYT"));

        String title = videoYT.getSnippet().getTitle();
        String description = videoYT.getSnippet().getDescription();
        String video_url = videoYT.getId().getVideoId();
        long published = videoYT.getSnippet().getPublishedAt().getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/dd/yyyy");
        String simpleDateFormatString = simpleDateFormat.format(published);


        youTubePlayerView.initialize(getString(R.string.YOUTUBE_API), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.loadVideo(video_url);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }
        });
        tvTitle.setText(title);
        tvDescription.setText(description);
        tvPublishedAt.setText(getString(R.string.Published) + simpleDateFormatString);
    }
}

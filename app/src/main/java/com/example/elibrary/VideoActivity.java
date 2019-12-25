package com.example.elibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.elibrary.helpers.AppActivity;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class VideoActivity extends AppActivity implements YouTubePlayerListener {
    private YouTubePlayerView youTubePlayerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initializedView();
        initializedListners();

    }




    @Override
    protected void initializedView() {
        youTubePlayerView=findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);



    }

    @Override
    protected void initializedListners() {

       youTubePlayerView.addYouTubePlayerListener(this);


    }

    @Override
    public void onReady(YouTubePlayer youTubePlayer) {
        String string=getIntent().getExtras().getString("bookurl");
//        Log.e("initializedListners: ", string);
        youTubePlayer.loadVideo(string,0);
    }

    @Override
    public void onApiChange(YouTubePlayer youTubePlayer) {

    }

    @Override
    public void onCurrentSecond(YouTubePlayer youTubePlayer, float v) {

    }

    @Override
    public void onError(YouTubePlayer youTubePlayer, PlayerConstants.PlayerError playerError) {

    }

    @Override
    public void onPlaybackQualityChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackQuality playbackQuality) {

    }

    @Override
    public void onPlaybackRateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackRate playbackRate) {

    }

    @Override
    public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState playerState) {

    }

    @Override
    public void onVideoDuration(YouTubePlayer youTubePlayer, float v) {

    }

    @Override
    public void onVideoId(YouTubePlayer youTubePlayer, String s) {

    }

    @Override
    public void onVideoLoadedFraction(YouTubePlayer youTubePlayer, float v) {

    }
}

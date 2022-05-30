package com.gi.programing_quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.gi.programing_quiz.Network.Retro;
import com.gi.programing_quiz.Network.RetroInterface;
import com.gi.programing_quiz.Pojo.LinkPojo;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPlayer extends AppCompatActivity {
    YouTubePlayerView youTubePlayerView;
    String titleId;
    TextView showText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);


        showText = findViewById(R.id.showText);
        titleId = getIntent().getStringExtra("titleId");
        getLink();

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    public void getLink() {
        Retro.getRetrofit(this).create(RetroInterface.class).fetchVideoLink(titleId).enqueue(new Callback<LinkPojo>() {
            @Override
            public void onResponse(Call<LinkPojo> call, Response<LinkPojo> response) {
                Log.d("gilog", response.body().toString());
                LinkPojo pojo = response.body();
                if (pojo.getStatus().equals("success")) {
                    setVideo(pojo.getVideo_link());
                } else {
                    showText.setVisibility(View.VISIBLE);
                    youTubePlayerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<LinkPojo> call, Throwable t) {
                Log.e("gilog", t.toString());

            }
        });
    }

    public void setVideo(String uriLink) {

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = uriLink;
                youTubePlayer.loadVideo(videoId, 0);
            }

            @Override
            public void onError(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerError error) {
                String videoId = uriLink;
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        youTubePlayerView.destroyDrawingCache();
    }
}
package com.example.du.hienglish.mvvm.view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.du.hienglish.R;
import com.example.du.hienglish.network.config.HttpConfig;
import com.example.du.hienglish.network.config.UrlConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity {
    private static final String TAG = "VideoActivity";
    public static final String VIDEO_NAME = "video_name";

    private String videoName;

    @BindView(R.id.title_bar_back)
    Button backBtn;
    @BindView(R.id.video)
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        videoName = intent.getStringExtra(VIDEO_NAME);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initVideoView();
    }

    private void initVideoView() {
        String videoPath = HttpConfig.BASE_URL + UrlConfig.video + videoName + ".mp4";
        Log.d(TAG, videoPath);
        videoView.setVideoPath(videoPath);
        MediaController mediaController = new MediaController(this);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (videoView.isPlaying()){
                    videoView.pause();
                }else {
                    videoView.start();
                }
                return false;
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });
    }
}

package com.example.du.hienglish.mvvm.view;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.du.hienglish.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class LiveOnActivity extends AppCompatActivity {
    public static final String LIVE_ON_URL = "live_on_url";
    public static final String ROOM_NAME = "room_name";
    @BindView(R.id.vitamio_videoView)
    VideoView mVideoView;
    @BindView(R.id.text_title)
    TextView titleText;
    @BindView(R.id.btn_back)
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        Window window = getWindow();
        window.setFormat(PixelFormat.TRANSLUCENT);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_live_on);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String liveOnUrl = intent.getStringExtra(LIVE_ON_URL);
        String roomName = intent.getStringExtra(ROOM_NAME);
        mVideoView.setVideoPath(liveOnUrl);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();

        titleText.setText(roomName + titleText.getText().toString());
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
    }
}

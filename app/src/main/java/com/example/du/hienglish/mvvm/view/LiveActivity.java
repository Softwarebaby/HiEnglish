package com.example.du.hienglish.mvvm.view;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.du.hienglish.HttpMethod;
import com.example.du.hienglish.R;
import com.example.du.hienglish.mvvm.model.User;
import com.example.du.hienglish.network.config.LiveConfig;
import com.example.du.hienglish.network.http.HttpSubscriber;
import com.example.du.hienglish.network.http.SubscriberOnNextListener;
import com.le.skin.LePublisherSkinView;
import com.le.skin.test.SettingActivity;
import com.le.skin.ui.SkinParams;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveActivity extends AppCompatActivity {
    public static final String USERNAME = "username";
    public static final String TELEPHONE = "telephone";
    public static final String ID = "id";
    public static final String IS_SHOW_BTN = "is_show_btn";

    private SubscriberOnNextListener updateUserLiveOnNext;

    private int userId;
    private boolean isShowBtn = false;

    @BindView(R.id.lpsv_stream_recorder)
    LePublisherSkinView skinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFormat(PixelFormat.TRANSLUCENT);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_live);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String teacherName = intent.getStringExtra(USERNAME);
        String activityId = intent.getStringExtra(TELEPHONE);
        userId = intent.getIntExtra(ID, 0);
        isShowBtn = intent.getBooleanExtra(IS_SHOW_BTN, false);

        SkinParams params = skinView.getSkinParams();
        params.setLanscape(false);
        test(params);
        skinView.initPublish(LiveConfig.USER_ID, LiveConfig.SECRET_KEY, activityId,teacherName + " 的直播间", isShowBtn);
    }

    @Override
    protected void onResume() {
        super.onResume();
        skinView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        skinView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateUserLiveOff();
        skinView.onDestroy();
    }

    private void test(SkinParams params) {
        if (com.letv.recorder.letvrecorderskin.BuildConfig.DEBUG) {
            SettingActivity.jsonToObj(params, this);
        }
    }

    private void updateUserLiveOff() {
        updateUserLiveOnNext = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
            }
        };
        User user = new User();
        user.setuId(userId);
        user.setuIde(1);
        user.setuState(1);
        user.setuLive(0);
        HttpMethod.getInstance().updateUserStatus(new HttpSubscriber(updateUserLiveOnNext, getApplicationContext()), user);
    }
}

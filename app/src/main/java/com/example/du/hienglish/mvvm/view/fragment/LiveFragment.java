package com.example.du.hienglish.mvvm.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.du.hienglish.HttpMethod;
import com.example.du.hienglish.R;
import com.example.du.hienglish.mvvm.model.User;
import com.example.du.hienglish.mvvm.view.LiveActivity;
import com.example.du.hienglish.mvvm.view.LiveOnActivity;
import com.example.du.hienglish.mvvm.view.adapter.LiveAdapter;
import com.example.du.hienglish.mvvm.view.widget.AlertDialog;
import com.example.du.hienglish.network.config.LiveConfig;
import com.example.du.hienglish.network.http.HttpSubscriber;
import com.example.du.hienglish.network.http.SubscriberOnNextListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class LiveFragment extends Fragment {
    private SubscriberOnNextListener<List<User>> getTeacherLiveListOnNext;
    private SubscriberOnNextListener updateUserLiveOnNext;
    private LiveAdapter liveAdapter;
    private List<User> userLiveList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.btn_live)
    FloatingActionButton liveBtn;

    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live, container, false);
        ButterKnife.bind(this, view);

        initFloatingBtn();
        initSwipeRefresh();
        getTeacherLiveList();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTeacherLiveListOnNext = new SubscriberOnNextListener<List<User>>() {
            @Override
            public void onNext(List<User> userList) {
                swipeRefresh.setRefreshing(false);
                List<User> userLiveOn = new ArrayList<>();
                List<User> userLiveOff = new ArrayList<>();
                for (User user : userList) {
                    int state = user.getuState();
                    if (state == 1 && user.getuId() != userId) {
                        userLiveOn.add(user);
                    } else if (state == 0 && user.getuId() != userId) {
                        userLiveOff.add(user);
                    }
                }
                userList.clear();
                userList.addAll(userLiveOn);
                userList.addAll(userLiveOff);
                userLiveList = userList;
                liveAdapter = new LiveAdapter(userList);
                listView.setAdapter(liveAdapter);
                LiveOnItemClickListener liveOnItemClickListener = new LiveOnItemClickListener();
                listView.setOnItemClickListener(liveOnItemClickListener);
            }
        };
    }

    @SuppressLint("RestrictedApi")
    private void initFloatingBtn() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_info", MODE_PRIVATE);
        userId = sharedPreferences.getInt("id", 0);
        int identity = sharedPreferences.getInt("identity", 0);
        String username = sharedPreferences.getString("username", "");
        String telephone = sharedPreferences.getString("telephone", "");
        if (identity == 1) {
            checkSelfPermission();
            liveBtn.setVisibility(View.VISIBLE);
            liveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateUserLiveOn();
                    Intent intent = new Intent(getActivity(), LiveActivity.class);
                    intent.putExtra(LiveActivity.USERNAME, username);
                    intent.putExtra(LiveActivity.TELEPHONE, telephone);
                    intent.putExtra(LiveActivity.ID, userId);
                    intent.putExtra(LiveActivity.IS_SHOW_BTN, true);
                    startActivity(intent);
                }
            });
        } else {
            liveBtn.setVisibility(View.GONE);
        }
    }

    private void initSwipeRefresh() {
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTeacherLiveList();
            }
        });
    }

    /**
     * 检查权限,获取所有需要的权限
     * 当targetSdkVersion大于23并且打算在6.0手机上运行时,请动态申请SDK所需要的权限
     */
    public void checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE}, 0);
        }
    }

    private void getTeacherLiveList() {
        HttpMethod.getInstance().getTeacherList(new HttpSubscriber<>(getTeacherLiveListOnNext, getActivity()));
    }

    private String getLiveOnUrl(String streamName) {
        return "rtmp://" + LiveConfig.DOMAIN_NAME + "/live/" + streamName;
    }

    private void updateUserLiveOn() {
        updateUserLiveOnNext = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
            }
        };
        User user = new User();
        user.setuId(userId);
        user.setuIde(1);
        user.setuState(1);
        user.setuLive(1);
        HttpMethod.getInstance().updateUserStatus(new HttpSubscriber(updateUserLiveOnNext, getActivity()), user);
    }

    private class LiveOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            User user = userLiveList.get(position);
            int isLive = user.getuLive();
            if (isLive == 1) {
//                Intent intent = new Intent(getActivity(), LiveOnActivity.class);
//                String url = getLiveOnUrl(user.getuTel());
//                intent.putExtra(LiveOnActivity.LIVE_ON_URL, url);
//                intent.putExtra(LiveOnActivity.ROOM_NAME, user.getuName());
                Intent intent = new Intent(getActivity(), LiveActivity.class);
                intent.putExtra(LiveActivity.USERNAME, user.getuName());
                intent.putExtra(LiveActivity.IS_SHOW_BTN, false);
                startActivity(intent);
            } else {
                AlertDialog.show(getActivity(), "对不起，该老师未直播！");
            }
        }
    }
}

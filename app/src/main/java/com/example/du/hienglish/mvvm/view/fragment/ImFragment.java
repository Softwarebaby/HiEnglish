package com.example.du.hienglish.mvvm.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.du.hienglish.HttpMethod;
import com.example.du.hienglish.R;
import com.example.du.hienglish.mvvm.model.User;
import com.example.du.hienglish.mvvm.view.ImActivity;
import com.example.du.hienglish.mvvm.view.adapter.ImAdapter;
import com.example.du.hienglish.mvvm.view.widget.AlertDialog;
import com.example.du.hienglish.network.http.HttpSubscriber;
import com.example.du.hienglish.network.http.SubscriberOnNextListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class ImFragment extends Fragment {
    private SubscriberOnNextListener<List<User>> getTeacherImListOnNext;
    private ImAdapter imAdapter;
    private List<User> userImList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.list_view)
    ListView listView;

    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_im, container, false);
        ButterKnife.bind(this, view);
        initSwipeRefresh();
        getCurrentUserId();
        getTeacherImList();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTeacherImListOnNext = new SubscriberOnNextListener<List<User>>() {
            @Override
            public void onNext(List<User> userList) {
                swipeRefresh.setRefreshing(false);
                List<User> userImOn = new ArrayList<>();
                List<User> userImOff = new ArrayList<>();
                for (User user : userList) {
                    int state = user.getuState();
                    if (state == 1 && user.getuId() != userId) {
                        userImOn.add(user);
                    } else if (state == 0 && user.getuId() != userId) {
                        userImOff.add(user);
                    }
                }
                userList.clear();
                userList.addAll(userImOn);
                userList.addAll(userImOff);
                userImList = userList;
                imAdapter = new ImAdapter(userList);
                listView.setAdapter(imAdapter);
                ImOnItemClickListener imOnItemClickListener = new ImOnItemClickListener();
                listView.setOnItemClickListener(imOnItemClickListener);
            }
        };
    }

    private void getCurrentUserId() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_info", MODE_PRIVATE);
        userId = sharedPreferences.getInt("id", 0);
    }

    private void initSwipeRefresh() {
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTeacherImList();
            }
        });
    }

    private void getTeacherImList() {
        HttpMethod.getInstance().getTeacherList(new HttpSubscriber<>(getTeacherImListOnNext, getActivity()));
    }

    private class ImOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            User user = userImList.get(position);
            int state = user.getuState();
            if (state == 1) {
                Intent intent = new Intent(getActivity(), ImActivity.class);
                intent.putExtra(ImActivity.USER_NAME, user.getuName());
                intent.putExtra(ImActivity.USER_PHONE, user.getuTel());
                startActivity(intent);
            } else {
                AlertDialog.show(getActivity(), "对不起，该老师已离线！");
            }
        }
    }
}

package com.example.du.hienglish.mvvm.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.du.hienglish.HttpMethod;
import com.example.du.hienglish.R;
import com.example.du.hienglish.mvvm.model.Video;
import com.example.du.hienglish.mvvm.view.adapter.VideoAdapter;
import com.example.du.hienglish.network.http.HttpSubscriber;
import com.example.du.hienglish.network.http.SubscriberOnNextListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragment extends Fragment {
    private SubscriberOnNextListener<List<Video>> getVideoListOnNext;
    private VideoAdapter videoAdapter;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        initSwipeRefresh();
        getVideoList();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getVideoListOnNext = new SubscriberOnNextListener<List<Video>>() {
            @Override
            public void onNext(List<Video> videoList) {
                swipeRefresh.setRefreshing(false);
                videoAdapter = new VideoAdapter(videoList);
                recyclerView.setAdapter(videoAdapter);
            }
        };
    }

    private void initRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initSwipeRefresh() {
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getVideoList();
            }
        });
    }

    private void getVideoList() {
        HttpMethod.getInstance().getVideoList(new HttpSubscriber<>(getVideoListOnNext, getActivity()));
    }
}

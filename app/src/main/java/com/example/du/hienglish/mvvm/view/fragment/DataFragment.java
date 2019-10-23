package com.example.du.hienglish.mvvm.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.du.hienglish.HttpMethod;
import com.example.du.hienglish.R;
import com.example.du.hienglish.mvvm.model.DataInfo;
import com.example.du.hienglish.mvvm.view.adapter.DataInfoAdapter;
import com.example.du.hienglish.network.http.HttpSubscriber;
import com.example.du.hienglish.network.http.SubscriberOnNextListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DataFragment extends Fragment {
    private SubscriberOnNextListener<List<DataInfo>> getDataInfoListOnNext;
    private DataInfoAdapter dataInfoAdapter;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        initSwipeRefresh();
        getDataInfoList();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataInfoListOnNext = new SubscriberOnNextListener<List<DataInfo>>() {
            @Override
            public void onNext(List<DataInfo> dataInfoList) {
                swipeRefresh.setRefreshing(false);
                dataInfoAdapter = new DataInfoAdapter(dataInfoList);
                recyclerView.setAdapter(dataInfoAdapter);
            }
        };
    }

    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initSwipeRefresh() {
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataInfoList();
            }
        });
    }

    private void getDataInfoList() {
        HttpMethod.getInstance().getDataInfoList(new HttpSubscriber<>(getDataInfoListOnNext, getActivity()));
    }
}

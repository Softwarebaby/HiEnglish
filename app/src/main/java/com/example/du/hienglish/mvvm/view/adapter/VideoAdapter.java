package com.example.du.hienglish.mvvm.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.du.hienglish.R;
import com.example.du.hienglish.mvvm.model.Video;
import com.example.du.hienglish.mvvm.view.VideoActivity;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private Context mContext;
    private List<Video> mVideoList;

    public VideoAdapter(List<Video> videoList) {
        mVideoList = videoList;
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_video, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.videoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Video video = mVideoList.get(position);
                Intent intent = new Intent(mContext, VideoActivity.class);
                intent.putExtra(VideoActivity.VIDEO_NAME, video.getvName());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ViewHolder viewHolder, int i) {
        Video video = mVideoList.get(i);
        Glide.with(mContext).load(video.getvImage()).
                placeholder(R.drawable.img_default).into(viewHolder.videoImage);
        viewHolder.videoName.setText(video.getvName());
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView videoImage;
        TextView videoName;

        public ViewHolder(View view) {
            super(view);
            videoImage = (ImageView) view.findViewById(R.id.video_image);
            videoName = (TextView) view.findViewById(R.id.video_name);
        }
    }
}

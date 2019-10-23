package com.example.du.hienglish.mvvm.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.du.hienglish.R;
import com.example.du.hienglish.mvvm.model.DataInfo;
import com.example.du.hienglish.mvvm.view.DataActivity;

import java.util.List;

public class DataInfoAdapter extends RecyclerView.Adapter<DataInfoAdapter.ViewHolder> {
    private Context mContext;
    private List<DataInfo> mDataInfoList;

    public DataInfoAdapter(List<DataInfo> dataInfoList) {
        mDataInfoList = dataInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_data_info, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                DataInfo dataInfo = mDataInfoList.get(position);
                Intent intent = new Intent(mContext, DataActivity.class);
                intent.putExtra(DataActivity.ID, dataInfo.getdId());
                intent.putExtra(DataActivity.PIC_PATH, dataInfo.getdPicPath());
                intent.putExtra(DataActivity.TITLE, dataInfo.getdTitle());
                intent.putExtra(DataActivity.CONTENT, dataInfo.getdContent());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DataInfo dataInfo = mDataInfoList.get(i);
        viewHolder.dataTitle.setText(dataInfo.getdTitle());
        viewHolder.dataExplain.setText(dataInfo.getdExplain());
        Glide.with(mContext).load(dataInfo.getdPicPath()).
                placeholder(R.drawable.img_default).into(viewHolder.dataImage);
    }

    @Override
    public int getItemCount() {
        return mDataInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView dataImage;
        TextView dataTitle;
        TextView dataExplain;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            dataImage = view.findViewById(R.id.data_image);
            dataTitle = view.findViewById(R.id.data_title);
            dataExplain = view.findViewById(R.id.data_explain);
        }
    }
}

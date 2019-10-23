package com.example.du.hienglish.mvvm.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.du.hienglish.R;
import com.example.du.hienglish.mvvm.model.User;

import java.util.List;

public class LiveAdapter extends BaseAdapter {
    private List<User> userList;

    public LiveAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.item_live, null);
            viewHolder = new ViewHolder();
            viewHolder.userHead = (ImageView) convertView.findViewById(R.id.user_header);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.userLive = (TextView) convertView.findViewById(R.id.user_live);
            viewHolder.imageLive = (ImageView) convertView.findViewById(R.id.image_live);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        User user = userList.get(position);
        if (position == 0) {
            viewHolder.userHead.setImageResource(R.drawable.header_01);
        } else {
            viewHolder.userHead.setImageResource(R.drawable.header_02);
        }

        viewHolder.userName.setText(user.getuName());
        int isLive = user.getuLive();
        if (isLive == 1) {
            viewHolder.userLive.setText("直播中");
            viewHolder.userLive.setTextColor(parent.getContext().getColor(R.color.colorYellow));
            viewHolder.imageLive.setImageResource(R.drawable.img_live_on);
        } else {
            viewHolder.userLive.setText("未直播");
            viewHolder.userLive.setTextColor(parent.getContext().getColor(R.color.colorLight));
            viewHolder.imageLive.setImageResource(R.drawable.img_live_off);
        }
        return convertView;
    }

    private static class ViewHolder{
        ImageView userHead;
        TextView userName;
        TextView userLive;
        ImageView imageLive;
    }
}

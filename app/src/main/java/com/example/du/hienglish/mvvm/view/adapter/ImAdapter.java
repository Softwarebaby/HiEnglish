package com.example.du.hienglish.mvvm.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.du.hienglish.R;
import com.example.du.hienglish.mvvm.model.User;

import java.util.List;

public class ImAdapter extends BaseAdapter {
    private List<User> userList;

    public ImAdapter(List<User> userList) {
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
        if(convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_im, null);
            viewHolder = new ViewHolder();
            viewHolder.colorState = (ImageView) convertView.findViewById(R.id.color_state);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.userPhone = (TextView) convertView.findViewById(R.id.user_phone);
            viewHolder.userState = (TextView) convertView.findViewById(R.id.user_state);
            viewHolder.imageState = (ImageView) convertView.findViewById(R.id.image_state);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        User user = userList.get(position);
        viewHolder.userName.setText(user.getuName());
        viewHolder.userPhone.setText(user.getuTel());
        int state = user.getuState();
        if (state == 1) {
            viewHolder.userState.setText("在线");
            viewHolder.userState.setTextColor(parent.getContext().getColor(R.color.colorGreen));
            viewHolder.colorState.setBackgroundColor(parent.getContext().getColor(R.color.colorGreen));
            viewHolder.imageState.setImageResource(R.drawable.img_im_on);
        } else {
            viewHolder.userState.setText("离线");
            viewHolder.userState.setTextColor(parent.getContext().getColor(R.color.colorRed));
            viewHolder.colorState.setBackgroundColor(parent.getContext().getColor(R.color.colorRed));
            viewHolder.imageState.setImageResource(R.drawable.img_im_off);
        }
        return convertView;
    }

    private static class ViewHolder{
        ImageView colorState;
        TextView userName;
        TextView userPhone;
        TextView userState;
        ImageView imageState;
    }
}

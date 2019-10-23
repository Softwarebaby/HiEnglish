package com.example.du.hienglish.mvvm.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.du.hienglish.R;

import java.util.List;

public class TestAdapter extends BaseAdapter {
    private Context context;
    private List<String> answers;

    public TestAdapter(Context context, List<String> answers) {
        this.context = context;
        this.answers = answers;
    }

    @Override
    public int getCount() {
        return answers.size();
    }

    @Override
    public Object getItem(int position) {
        return answers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_test, null);
            holder = new ViewHolder();
            holder.tvText = (TextView) convertView.findViewById(R.id.answer_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String answer = answers.get(position);
        holder.tvText.setText(answer);
        return convertView;
    }

    private static class ViewHolder {
        TextView tvText;
    }
}

package com.example.du.hienglish.mvvm.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.du.hienglish.mvvm.model.Message;
import com.example.du.hienglish.mvvm.view.adapter.MessageAdapter;

import com.example.du.hienglish.R;
import com.example.du.hienglish.network.config.SocketConfig;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Du Senmiao
 * Function: Client实现即时通信
 * Class: ImActivity.java
 */
public class ImActivity extends AppCompatActivity {
    private static final String TAG = "ImActivity";
    public static final String USER_NAME = "user_name";
    public static final String USER_PHONE = "user_phone";

    @BindView(R.id.text_title)
    TextView titleText;
    @BindView(R.id.msg_recycler_view)
    RecyclerView messageRecyclerView;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.btn_back)
    Button btnBack;

    private MessageAdapter messageAdapter;
    private List<Message> messageList = new ArrayList<>();
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String teacherName;
    private String teacherPhone;
    private String userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im);
        ButterKnife.bind(this);

        getInfo();
        initView();
        connectSocket();
    }

    @OnClick({R.id.btn_send, R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                sendMessage();
                break;
            case R.id.btn_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void getInfo() {
        Intent intent = getIntent();
        teacherName = intent.getStringExtra(USER_NAME);
        teacherPhone = intent.getStringExtra(USER_PHONE);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_info", MODE_PRIVATE);
        userPhone = sharedPreferences.getString("telephone", "");
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        messageRecyclerView.setLayoutManager(linearLayoutManager);
        messageAdapter = new MessageAdapter(messageList);
        messageRecyclerView.setAdapter(messageAdapter);
        titleText.setText(teacherName);
    }

    /**
     * 连接ServerSocket
     */
    private void connectSocket() {
        AsyncTask asyncTask = new AsyncTask() {
            /**
             * 后台创建Socket连接，并创建输入输出流
             * @param objects
             * @return
             */
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    socket = new Socket(SocketConfig.IP, SocketConfig.PORT);
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    publishProgress("success");
                } catch (IOException e) {
                    publishProgress("failed");
                    e.printStackTrace();
                }
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        publishProgress(line);
                    }
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            /**
             * 根据返回的数据，更新UI
             * @param values
             */
            @Override
            protected void onProgressUpdate(Object[] values) {
                if ("success".equals(values[0])) {
                    Log.d(TAG, "onProgressUpdate: Socket" + "连接成功");
                } else if ("failed".equals(values[0])) {
                    Log.d(TAG, "onProgressUpdate: Socket" + "无法建立连接");
                } else {
                    Message msg2 = new Message((String) values[0], Message.TYPE_RECEIVED);
                    messageList.add(msg2);
                    messageAdapter.notifyItemInserted(messageList.size() - 1);
                    messageRecyclerView.scrollToPosition(messageList.size() - 1);
                    super.onProgressUpdate(values);
                }
            }
        };
        asyncTask.execute();
    }

    /**
     * 发送消息
     */
    private void sendMessage() {
        String out = editContent.getText().toString().trim();
        Message msg1 = new Message(out, Message.TYPE_SEND);
        messageList.add(msg1);
        messageAdapter.notifyItemInserted(messageList.size() - 1);
        messageRecyclerView.scrollToPosition(messageList.size() - 1);
        editContent.setText("");
        //创建子线程，并向输出流写入数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    writer.write(out + "\n");
                    writer.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

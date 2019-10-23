package com.example.du.hienglish.mvvm.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.du.hienglish.HttpMethod;
import com.example.du.hienglish.R;
import com.example.du.hienglish.databinding.ActivityMainBinding;
import com.example.du.hienglish.mvvm.MainContract;
import com.example.du.hienglish.mvvm.model.User;
import com.example.du.hienglish.mvvm.view.fragment.DataFragment;
import com.example.du.hienglish.mvvm.view.fragment.ImFragment;
import com.example.du.hienglish.mvvm.view.fragment.LiveFragment;
import com.example.du.hienglish.mvvm.view.fragment.TestFragment;
import com.example.du.hienglish.mvvm.view.fragment.VideoFragment;
import com.example.du.hienglish.mvvm.viewmodel.MainViewModel;
import com.example.du.hienglish.network.http.HttpSubscriber;
import com.example.du.hienglish.network.http.SubscriberOnNextListener;

import javax.inject.Inject;

/**
 * Created by Bob Du on 2019/04/04 22:45
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel>
        implements MainContract.View {
    private static final String TAG = "MainActivity";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PHONE = "user_phone";
    public static final String USER_IDENTITY = "user_identity";
    public static final String USER_STATE = "user_state";
    private String username;
    private String telephone;
    private int identity;
    private int id;

    private SubscriberOnNextListener updateUserStateOnNext;

    @Inject
    MainViewModel mainViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        username = intent.getStringExtra(USER_NAME);
        telephone = intent.getStringExtra(USER_PHONE);
        identity = intent.getIntExtra(USER_IDENTITY, 0);
        id = intent.getIntExtra(USER_ID, 0);

        saveUserInfo();
        initToolbar();
        initNav();
        initNavHeader();
        initFragment();
        updateUserState(1);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    public void initToolbar() {
        setSupportActionBar(mBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    public void initNav() {
        mBinding.navView.setItemIconTintList(null);
        mBinding.navView.setCheckedItem(R.id.nav_data);  //默认选中
        mBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_live:
                        routeTo(new LiveFragment());
                        break;
                    case R.id.nav_video:
                        routeTo(new VideoFragment());
                        break;
                    case R.id.nav_im:
                        routeTo(new ImFragment());
                        break;
                    case R.id.nav_data:
                        routeTo(new DataFragment());
                        break;
                    case R.id.nav_test:
                        routeTo(new TestFragment());
                        break;
                    default:
                        break;
                }
                mBinding.drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void initNavHeader() {
        View view = mBinding.navView.inflateHeaderView(R.layout.nav_header);
        TextView identityText = view.findViewById(R.id.identity);
        String identityStr = identity == 0 ? "同学" : "老师";
        identityText.setText(identityText.getText() + identityStr);
        TextView usernameText = view.findViewById(R.id.username);
        usernameText.setText(username);
        Log.d(TAG, username + ":" + identity);
    }

    public void initFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new DataFragment())
                .commit();
    }

    public void routeTo(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void saveUserInfo() {
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences("user_info", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("telephone", telephone);
        editor.putInt("identity", identity);
        editor.putInt("id", id);
        editor.commit();
    }

    private void updateUserState(int state) {
        updateUserStateOnNext = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
            }
        };
        User user = new User();
        user.setuId(id);
        user.setuIde(identity);
        user.setuState(state);
        HttpMethod.getInstance().updateUserStatus(new HttpSubscriber(updateUserStateOnNext, getApplicationContext()), user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.message:
                routeTo(MainActivity.this, ImActivity.class);
                break;
            case android.R.id.home:
                mBinding.drawerLayout.openDrawer(GravityCompat.START);
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder isExit = new AlertDialog.Builder(this);
            isExit.setTitle("消息提醒");
            isExit.setIcon(R.drawable.img_alert);
            isExit.setMessage("确定要退出吗?");
            isExit.setPositiveButton("确定", diaListener);
            isExit.setNegativeButton("取消", diaListener);
            isExit.show();
        }
        return false;
    }

    DialogInterface.OnClickListener diaListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int buttonId) {
            // TODO Auto-generated method stub
            switch (buttonId) {
                case AlertDialog.BUTTON_POSITIVE:
                    updateUserState(0);
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:
                    break;
                default:
                    break;
            }
        }
    };

}

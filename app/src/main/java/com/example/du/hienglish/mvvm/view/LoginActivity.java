package com.example.du.hienglish.mvvm.view;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.du.hienglish.HttpMethod;
import com.example.du.hienglish.R;
import com.example.du.hienglish.databinding.ActivityLoginBinding;
import com.example.du.hienglish.mvvm.MainContract;
import com.example.du.hienglish.mvvm.model.User;
import com.example.du.hienglish.mvvm.viewmodel.LoginViewModel;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel>
        implements MainContract.View {
    private LoginViewModel mViewModel;
    private Presenter presenter;
    private String phone;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new Presenter();
        mViewModel = new LoginViewModel(this);
        mBinding.setPresenter(presenter);
        initView();

        mViewModel.getContent().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user != null) {
                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(MainActivity.USER_ID, user.getuId());
                    intent.putExtra(MainActivity.USER_NAME, user.getuName());
                    intent.putExtra(MainActivity.USER_PHONE, user.getuTel());
                    intent.putExtra(MainActivity.USER_IDENTITY, user.getuIde());
                    intent.putExtra(MainActivity.USER_STATE, user.getuState());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean checkLogin() {
        phone = mBinding.inputPhone.getText().toString();
        password = mBinding.inputPassword.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "手机号不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phone.length() != 11) {
            Toast.makeText(this, "手机号不合法！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initView() {
        mBinding.toRegister.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_login:
                    if (checkLogin()) {
                        User user = new User();
                        user.setuTel(phone);
                        user.setuPsd(password);
                        mViewModel.loginUser(user);
                    }
                    break;
                case R.id.to_register:
                    routeTo(LoginActivity.this, RegisterActivity.class);
                    break;
                default:
                    break;
            }
        }
    }
}

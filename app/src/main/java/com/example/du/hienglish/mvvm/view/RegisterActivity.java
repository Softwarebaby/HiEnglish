package com.example.du.hienglish.mvvm.view;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.du.hienglish.R;
import com.example.du.hienglish.databinding.ActivityRegisterBinding;
import com.example.du.hienglish.mvvm.MainContract;
import com.example.du.hienglish.mvvm.model.User;
import com.example.du.hienglish.mvvm.viewmodel.RegisterViewModel;
import com.example.du.hienglish.utils.CodeUtils;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel>
        implements MainContract.View {
    private RegisterViewModel mViewModel;
    private Presenter presenter;
    private boolean isShowPwd = false;
    private int identity = 0;
    private String phone;
    private String password;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new Presenter();
        mBinding.setPresenter(presenter);
        mViewModel = new RegisterViewModel(this);
        initShowPwdBtn();
        initRadioBtn();
        refreshVerifyCode();

        mViewModel.getContent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_register;
    }

    private void initShowPwdBtn() {
        ViewGroup.LayoutParams layoutParams = mBinding.btnShowPwd.getLayoutParams();
        layoutParams.height = 100;
        layoutParams.width = 100;
        mBinding.btnShowPwd.setLayoutParams(layoutParams);
        mBinding.btnShowPwd.setBackgroundResource(R.drawable.ic_pwd_show);
        mBinding.inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        isShowPwd = true;
    }

    private void initRadioBtn() {
        mBinding.identityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.identity_student:
                        identity = 0;
                        break;
                    case R.id.identity_teacher:
                        identity = 1;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void refreshVerifyCode() {
        mBinding.verifyCode.setImageBitmap(CodeUtils.getInstance().createBitmap());
    }

    private boolean checkRegister() {
        phone = mBinding.inputPhone.getText().toString();
        name = mBinding.inputName.getText().toString();
        password = mBinding.inputPassword.getText().toString();
        String verifycode = mBinding.inputVerify.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "手机号不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phone.length() != 11) {
            Toast.makeText(this, "手机号不合法！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "昵称不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(verifycode)) {
            Toast.makeText(this, "验证码不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!CodeUtils.getInstance().getCode().equals(verifycode)) {
            Toast.makeText(this, "验证码不正确！", Toast.LENGTH_SHORT).show();
        }
        if (!mBinding.identityStudent.isChecked() && !mBinding.identityTeacher.isChecked()) {
            Toast.makeText(this, "请选择您的身份！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.btn_register:
                    if (checkRegister()) {
                        User user = new User();
                        user.setuTel(phone);
                        user.setuName(name);
                        user.setuPsd(password);
                        user.setuIde(identity);
                        mViewModel.registerUser(user);
                    }
                    break;
                case R.id.btn_show_pwd:
                    if (isShowPwd) {
                        mBinding.btnShowPwd.setBackgroundResource(R.drawable.ic_pwd_hide);
                        mBinding.inputPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        isShowPwd = false;
                    } else {
                        mBinding.btnShowPwd.setBackgroundResource(R.drawable.ic_pwd_show);
                        mBinding.inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        isShowPwd = true;
                    }
                    break;
                case R.id.verify_code:
                    refreshVerifyCode();
                    break;
                default:
                    break;
            }
        }
    }
}

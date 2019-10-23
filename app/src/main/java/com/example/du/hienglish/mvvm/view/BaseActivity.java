package com.example.du.hienglish.mvvm.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.example.du.hienglish.mvvm.viewmodel.BaseViewModel;
import dagger.android.AndroidInjection;

/**
 * Created by Bob Du on 2019/04/04 17:11
 */
public abstract class BaseActivity<T extends ViewDataBinding, D extends BaseViewModel>
        extends AppCompatActivity {
    protected T mBinding;
    protected D mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        initDataBinding();
    }

    @Override
    protected void onDestroy() {
        if (mBinding != null) {
            mBinding.unbind();
        }
        super.onDestroy();
    }

    public void routeTo(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
    }

    protected void initDataBinding() {
        int layoutId = getLayoutRes();
        mBinding = DataBindingUtil.setContentView(this, layoutId);
    }

    protected abstract int getLayoutRes();
}

package com.example.du.hienglish.mvvm.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.example.du.hienglish.R;
import com.example.du.hienglish.databinding.ActivityDataBinding;
import com.example.du.hienglish.databinding.ActivityLoginBinding;
import com.example.du.hienglish.mvvm.MainContract;
import com.example.du.hienglish.mvvm.viewmodel.DataViewModel;
import com.example.du.hienglish.mvvm.viewmodel.LoginViewModel;

import javax.inject.Inject;

public class DataActivity extends BaseActivity<ActivityDataBinding, DataViewModel>
        implements MainContract.View {
    public static final String ID = "ID";
    public static final String PIC_PATH = "pic_path";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";

    private int dataId;
    private String dataImage;
    private String dataTitle;
    private String dataContent;

    @Inject
    DataViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        dataId = intent.getIntExtra(ID, 0);
        dataImage = intent.getStringExtra(PIC_PATH);
        dataTitle = intent.getStringExtra(TITLE);
        dataContent = intent.getStringExtra(CONTENT);
        initView();
    }

    private void initView() {
        setSupportActionBar(mBinding.toolBar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mBinding.collapsingToolbar.setTitle(dataTitle);
        Glide.with(this).load(dataImage)
                .placeholder(R.drawable.img_default).into(mBinding.dataImageView);
        Glide.with(this).load(dataContent)
                .placeholder(R.drawable.img_default).into(mBinding.dataContent);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_data;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

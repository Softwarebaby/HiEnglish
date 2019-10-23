package com.example.du.hienglish.di.module;

import com.example.du.hienglish.mvvm.viewmodel.DataViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class DataActivityModule {
    @Provides
    DataViewModel provideDataViewModel() {
        return new DataViewModel();
    }
}

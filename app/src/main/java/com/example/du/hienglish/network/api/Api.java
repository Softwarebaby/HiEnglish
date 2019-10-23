package com.example.du.hienglish.network.api;

import com.example.du.hienglish.mvvm.model.User;
import com.example.du.hienglish.mvvm.model.Video;
import com.example.du.hienglish.network.config.UrlConfig;
import com.example.du.hienglish.mvvm.model.DataInfo;
import com.example.du.hienglish.network.http.HttpResult;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface Api {

    @GET (UrlConfig.get_data_info_list)
    Observable<HttpResult<List<DataInfo>>> getDataInfoList();

    @GET (UrlConfig.get_video_list)
    Observable<HttpResult<List<Video>>> getVideoList();

    @POST (UrlConfig.register)
    Observable<HttpResult> register(@Body User user);

    @POST (UrlConfig.login)
    Observable<HttpResult<User>> login(@Body User user);

    @GET (UrlConfig.get_teacher_list)
    Observable<HttpResult<List<User>>> getTeacherList();

    @POST (UrlConfig.update_user_status)
    Observable<HttpResult> updateUserStatus(@Body User user);
}

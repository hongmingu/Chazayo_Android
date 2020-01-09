package com.doitandroid.chazayo.rest;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {
    /* send instant ping */
    @Multipart
    @POST("r/rest/search_word/")
    Call<JsonObject> searchWord(@Part("search_word") RequestBody searchWord);

    /* get follow feed */
    @POST("r/rest/get_follow_feed/")
    Call<JsonObject> get_follow_feed();


}

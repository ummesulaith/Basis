package com.basis.content.basisapplication.RetrofitInterface;

import com.basis.content.basisapplication.Model.Contentdata;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface Data_retrofitInterface {
    @Headers({"Accept:text/plain"})
    @GET("HiringTask.json")
    Call<Contentdata> getdata();
}

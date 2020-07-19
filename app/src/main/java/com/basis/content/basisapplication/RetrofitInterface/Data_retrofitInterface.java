package com.basis.content.basisapplication.RetrofitInterface;

import com.basis.content.basisapplication.Model.Contentdata;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface Data_retrofitInterface {
    @Headers({"Accept:text/plain"})
    @GET("HiringTask.json")
    Observable<Contentdata> getdata();
}

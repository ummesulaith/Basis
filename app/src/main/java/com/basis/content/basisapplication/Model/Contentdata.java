package com.basis.content.basisapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Contentdata {

    @SerializedName("data")
    @Expose
    private List<DetailContentData> data;

    public Contentdata(List<DetailContentData> data) {
        this.data = data;
    }

    public List<DetailContentData> getData() {
        return data;
    }

    public void setData(List<DetailContentData> data) {
        this.data = data;
    }


}

package com.basis.content.basisapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailContentData extends Contentdata {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("text")
    @Expose
    private String text;


    public DetailContentData(List<DetailContentData> data, String id, String text) {
        super(data);
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {



        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}

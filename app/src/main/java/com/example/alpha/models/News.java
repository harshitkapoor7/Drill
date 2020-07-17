package com.example.alpha.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("totalResult")
    @Expose
    private String totalResult;

    @SerializedName("articles")
    @Expose
    private List<Article> article;

    public String getStatus() {
        return status;
    }

    public String getTotalResult() {
        return totalResult;
    }

    public List<Article> getArticle() {
        return article;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalResult(String totalResult) {
        this.totalResult = totalResult;
    }

    public void setArticle(List<Article> article) {
        this.article = article;
    }

}


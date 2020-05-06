package com.example.newscastpro.business_news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Headlines {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("totalResults")
    @Expose
    private String totalResults;

    @SerializedName("articles")
    @Expose
    private List<Business_Artcile> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public List<Business_Artcile> getArticles() {
        return articles;
    }

    public void setArticles(List<Business_Artcile> articles) {
        this.articles = articles;
    }
}

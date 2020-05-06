package com.example.newscastpro.sports_news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sports_headlines {

    @SerializedName("status")
    @Expose
    private String ststus;

    @SerializedName("totalResults")
    @Expose
    private String totalResults;

    @SerializedName("articles")
    @Expose
    List<Sports_articles> sports_artciles;

    public String getStstus() {
        return ststus;
    }

    public void setStstus(String ststus) {
        this.ststus = ststus;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public List<Sports_articles> getSports_artciles() {
        return sports_artciles;
    }

    public void setSports_artciles(List<Sports_articles> sports_artciles) {
        this.sports_artciles = sports_artciles;
    }
}

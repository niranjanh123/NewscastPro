package com.example.newscastpro.Sports_ApiClient;

import com.example.newscastpro.sports_news.Sports_headlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<Sports_headlines> getSportsHeadlines(
       @Query("country") String country,
       @Query("category") String category,
       @Query("apiKey") String apiKey
    );
}

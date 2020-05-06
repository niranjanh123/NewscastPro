package com.example.newscastpro.Sports_ApiClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private String Base_url="http://newsapi.org/v2/";
    private static Retrofit retrofit;
    private static ApiClient apiClient;

    private ApiClient(){
        retrofit = new Retrofit.Builder().baseUrl(Base_url)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
    public static synchronized com.example.newscastpro.Sports_ApiClient.ApiClient getInstance(){
        if(apiClient==null){
            apiClient = new ApiClient();
        }
        return apiClient;
    }
    public com.example.newscastpro.Sports_ApiClient.ApiInterface getApi(){
        return  retrofit.create(ApiInterface.class);
    }
}

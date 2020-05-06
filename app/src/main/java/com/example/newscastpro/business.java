package com.example.newscastpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.newscastpro.Business_ApiClient.ApiClient;
import com.example.newscastpro.business_news.Business_Artcile;
import com.example.newscastpro.business_news.Headlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class business extends AppCompatActivity {
    private String Api_key = "a249d069d2ab4f0283210ae811454246";
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Toolbar toolbar;
    Adapter1 adapter1;
    List<Business_Artcile> articles = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        recyclerView = findViewById(R.id.business_recycler_view);
        progressBar = findViewById(R.id.business_progress);
        progressBar.setVisibility(View.VISIBLE);
        toolbar = findViewById(R.id.business_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Business");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        String country = getCountry();
        retrieveJson(country);
    }
    public void retrieveJson(String country){
        Call<Headlines> headlines = ApiClient.getInstance().getApi().getHeadlines(country,"business",Api_key);
        headlines.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful()&&response.body().getArticles()!=null){
//                        if(articles.isEmpty()){
//                            articles.clear();
//                        }
                    articles = response.body().getArticles();
                    adapter1 = new Adapter1(business.this,articles);
                    recyclerView.setAdapter(adapter1);
                    progressBar.setVisibility(View.GONE);
                    adapter1.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {

            }
        });
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();

    }
}

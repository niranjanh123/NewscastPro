package com.example.newscastpro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;

import com.example.newscastpro.Sports_ApiClient.ApiClient;
import com.example.newscastpro.business_news.Headlines;
import com.example.newscastpro.sports_news.Sports_articles;
import com.example.newscastpro.sports_news.Sports_headlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sports extends AppCompatActivity {
    private String Api_key = "a249d069d2ab4f0283210ae811454246";
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Toolbar toolbar;
    Adapter2 adapter2;
    List<Sports_articles> sports_articles = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);
        recyclerView = findViewById(R.id.sports_recycler_view);
        progressBar = findViewById(R.id.sports_progress);
        progressBar.setVisibility(View.VISIBLE);
        toolbar = findViewById(R.id.sports_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Sports");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        String country = getCountry();
        retrieveJson(country);
    }

    public void retrieveJson(String country){
        Call<Sports_headlines> headlines = ApiClient.getInstance().getApi().getSportsHeadlines(country,"sports",Api_key);
        headlines.enqueue(new Callback<Sports_headlines>() {
            @Override
            public void onResponse(Call<Sports_headlines> call, Response<Sports_headlines> response) {
                if(response.isSuccessful()&&response.body().getSports_artciles()!=null){
//                        if(articles.isEmpty()){
//                            articles.clear();
//                        }
                    sports_articles = response.body().getSports_artciles();
                    adapter2 = new Adapter2(sports.this,sports_articles);
                    recyclerView.setAdapter(adapter2);
                    progressBar.setVisibility(View.GONE);
                    adapter2.notifyDataSetChanged();
            }
            }

            @Override
            public void onFailure(Call<Sports_headlines> call, Throwable t) {

            }
        });


}

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();

    }
}

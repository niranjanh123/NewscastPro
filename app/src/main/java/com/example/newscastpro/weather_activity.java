package com.example.newscastpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newscastpro.ApiClient.ApiClient;
import com.example.newscastpro.ApiClient.ApiInterface;
import com.example.newscastpro.models.Article;
import com.example.newscastpro.models.News;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class weather_activity extends AppCompatActivity {
    private static final String TAG = "weather_activity";
    RecyclerView recyclerView;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    Adapter adapter;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private String api_key = "a249d069d2ab4f0283210ae811454246";
    List<Article> articles;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_activity);
        recyclerView = findViewById(R.id.recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        progressBar = findViewById(R.id.weather_toolbar);
        progressBar.setVisibility(View.VISIBLE);
        navigationView = findViewById(R.id.navigation_drawer);
        drawerLayout =  findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.item1:
                        Intent intent = new Intent(weather_activity.this,weather_activity.class);
                        startActivity(intent);
                       // Toast.makeText(weather_activity.this,"Home",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item2:
                        Intent intent2 = new Intent(weather_activity.this,sports.class);
                        startActivity(intent2);
                      //  Toast.makeText(weather_activity.this, "Sports", Toast.LENGTH_SHORT).show();
                        return  true;
                    case R.id.item3:
                        Intent intent1 = new Intent(weather_activity.this,business.class);
                        startActivity(intent1);
                        //Toast.makeText(weather_activity.this, "Business", Toast.LENGTH_SHORT).show();
                        return  true;
                    case R.id.item4:
                        Intent intent3 = new Intent(weather_activity.this,about.class);
                        startActivity(intent3);
                       // Toast.makeText(weather_activity.this, "About", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item5:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent4 = new Intent(weather_activity.this,Login_activity.class);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent4);
                        finish();
                       // Toast.makeText(weather_activity.this, "Help", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return  false;
            }
        });
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        loadJson();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJson();
            }
        });


    }


    public void loadJson(){
        ApiInterface apiInterface = ApiClient.getRetrofitClient().create(ApiInterface.class);
        String country = Utils.getCountry();
        Call<News> call;
        call = apiInterface.getNews(country,api_key);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful()&&response.body().getArticle()!=null){
//                        if(articles.isEmpty()){
//                            articles.clear();
//                        }

                        articles = response.body().getArticle();
                        adapter = new Adapter(articles,weather_activity.this);
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        swipeRefreshLayout.setEnabled(true);
                }else{

                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                  swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.newscastpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Main_headlines_activity extends AppCompatActivity {
    WebView webView;
    Toolbar toolbar;
    ProgressBar progressBar;
    String murl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_headlines_activity);
        webView = findViewById(R.id.main_web_view);
        progressBar = findViewById(R.id.main_progress);
        progressBar.setVisibility(View.VISIBLE);
        toolbar = findViewById(R.id.main_detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        murl = intent.getStringExtra("Url");

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(murl);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String sharebody = murl;
                String sharesubject = "NewsCastPro";
                share.putExtra(Intent.EXTRA_TEXT, sharebody);
                share.putExtra(Intent.EXTRA_SUBJECT, sharesubject);
                startActivity(Intent.createChooser(share, "Share Using"));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
package com.example.newscastpro;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends Application {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    public void onCreate() {
        super.onCreate();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            startActivity(new Intent(Home.this,weather_activity.class));
        }
    }
}

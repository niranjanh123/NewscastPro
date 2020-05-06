package com.example.newscastpro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_activity extends AppCompatActivity {
    private static final String TAG = "Login_activity";
    private TextView  forgot_password;
    private TextView createAccount;
    private Button login;
    private EditText mail, pass;
    String nmail, npass;
    FirebaseAuth firebaseAuth;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        forgot_password = findViewById(R.id.forgot_password);
        createAccount = findViewById(R.id.create_account_text_view);
        login = findViewById(R.id.login_button);
        mail = findViewById(R.id.login_Email);
        pass = findViewById(R.id.login_Password);
        firebaseAuth = FirebaseAuth.getInstance();


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_activity.this, Register.class);
                startActivity(intent);
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String forgot_mail = mail.getText().toString().trim();
                if (!forgot_mail.equals("")) {
                    firebaseAuth.sendPasswordResetEmail(forgot_mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login_activity.this, "Check your mail to reset the password", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Login_activity.this, "Error to reset the password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Login_activity.this, "Enter your mail", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nmail = mail.getText().toString().trim();
                npass = pass.getText().toString().trim();
                if (TextUtils.isEmpty(nmail)) {
                    mail.setError("Enter your mail");
                }
                if (TextUtils.isEmpty(npass)) {
                    mail.setError("Enter your password");
                }else if (!(nmail.equals("")) && (!(npass.equals("")))) {
                    firebaseAuth.signInWithEmailAndPassword(nmail, npass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                checkEmailVerification();
                            } else {
                                Log.d(TAG, "onFailed: " + task.getException());
                                Toast.makeText(Login_activity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }

    private void checkEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uname = dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        boolean flag = user.isEmailVerified();
        if (flag) {
            ProgressDialog progressDialog = new ProgressDialog(Login_activity.this);
            progressDialog.setTitle("You will Login soon");
            progressDialog.setMessage("You will Login soon");
            progressDialog.show();

            Toast.makeText(Login_activity.this, "Login successfull", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login_activity.this, weather_activity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(Login_activity.this, "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}

package com.example.newscastpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private static final String TAG = "Register";
    FirebaseAuth firebaseAuth;
    Button button;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef;
    EditText username,mail,password,confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        button = findViewById(R.id.reg_button);
        username = findViewById(R.id.reg_Username);
        mail  = findViewById(R.id.reg_Email);
        password = findViewById(R.id.reg_Password);
        confirm = findViewById(R.id.reg_confirm_password);
        firebaseAuth = FirebaseAuth.getInstance();
        mRef = firebaseDatabase.getInstance().getReference("Registerations");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mname = username.getText().toString();
                final String email = mail.getText().toString().trim();
                final String mpass = password.getText().toString();
                final String mrepass = confirm.getText().toString();

                if (TextUtils.isEmpty(mname)) {
                    username.setError("Enter your name");
                }
                if (TextUtils.isEmpty(email)) {
                    mail.setError("Enter your Email");
                }
                if (TextUtils.isEmpty(mpass)) {
                    password.setError("Enter your password");
                }
                if (TextUtils.isEmpty(mrepass)) {
                    confirm.setError("Enter your password");
                }
                if (mpass.length() < 8) {
                    password.setError("Password Minimum length should be 8");
                }
                if (mrepass.length() < 8) {
                    confirm.setError("Password Minimum length should be 8");
                }
                if (!(mpass.equals(mrepass))) {
                    password.setError("The password or repassword entered is wrong");
                }
                else if (!(mname.equals("")) && (!(email.equals(""))) && (!(mpass.equals(""))) && (!(mrepass.equals(""))))
                {

                    firebaseAuth.createUserWithEmailAndPassword(email, mpass)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        Data data = new Data(mname,email,mpass,mrepass);
                                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                sendEmailVerification();
                                            }
                                        });

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(Register.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
            }
        });
    }

    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this, "Account successfully created.Please check your mail to verify your account.", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        Intent intent = new Intent(Register.this, Login_activity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Register.this, "Account Creation Failed.Please create a new Account.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(Register.this, "Was not able to send a verfication mail!! Please try again!!", Toast.LENGTH_SHORT).show();
        }
    }
}

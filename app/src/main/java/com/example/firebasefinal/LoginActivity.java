package com.example.firebasefinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText passwordET,emailET;
    Button loginBtn;
    ProgressBar progressBar;
    String email,password;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
ini_t();
loginBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        email=emailET.getText().toString();
        password=passwordET.getText().toString();
        if(email.isEmpty()){
            emailET.setError("Enter your email");
        } else if (password.isEmpty()||password.length()<6) {
            passwordET.setError("Enter Your Password");

        }
        else {
            login();
        }

    }
});

    }

    private void login() {
        loginBtn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loginBtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void ini_t() {
        passwordET=findViewById(R.id.log_passwordEt);
        emailET=findViewById(R.id.log_emailET);
        loginBtn=findViewById(R.id.login_btn);
        progressBar=findViewById(R.id.loadingBar);
        firebaseAuth=FirebaseAuth.getInstance();
    }

    public void Register(View view) {
        Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
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

public class RegisterActivity extends AppCompatActivity {
    EditText nameEt,emailET,passwordET,confirm;
    Button sign_upBTN;
    String name,email,password,confirm_password;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ini_t();
        sign_upBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=nameEt.getText().toString();
                email=emailET.getText().toString();
                password=passwordET.getText().toString();
                confirm_password=confirm.getText().toString();
                if(name.isEmpty()){
                    nameEt.setError("Enter you name");
                } else if (email.isEmpty()) {
                    emailET.setError("Enter email address");

                } else if (password.isEmpty()||password.length()<6) {
                    passwordET.setError("Enter 6 digit password");

                } else if (confirm_password.isEmpty()||confirm_password.length()<6) {
                    confirm.setError("Confirm your Password");

                } else if (!password.equals(confirm_password)) {
                    confirm.setError("Confirm Password does not match");

                }
                else {
                    signupUser();
                }
            }
        });
    }

    private void signupUser() {
        progressBar.setVisibility(View.VISIBLE);
        sign_upBTN.setVisibility(View.GONE);
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Account Created Successful", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                sign_upBTN.setVisibility(View.VISIBLE);
                Toast.makeText(RegisterActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void ini_t() {
        nameEt=findViewById(R.id.nameEt);
        emailET=findViewById(R.id.emailEt);
        passwordET=findViewById(R.id.passwordEt);
        confirm=findViewById(R.id.confirmEt);
        sign_upBTN=findViewById(R.id.sign_upBtn);
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.loadingBar);

    }
}
package com.example.goschedule20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText accountName, accountEmail, accountPassword, accountPassword2;
    Button register;
    TextView login;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        accountName = findViewById(R.id.accountName);
        accountEmail = findViewById(R.id.accountEmail);
        accountPassword = findViewById(R.id.accountPassword);
        accountPassword2 = findViewById(R.id.accountPassword2);
        register = findViewById(R.id.accountLogin);
        login = findViewById(R.id.register);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(firebaseAuth.getCurrentUser() != null){
           startActivity(new Intent(getApplicationContext(),MainActivity.class));
           finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = accountEmail.getText().toString().trim();
                String password = accountPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    accountEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    accountPassword.setError("Password is Required.");
                    return;
                }
                if(password.length() < 6){
                    accountPassword.setError("Password must have at least 6 characters.");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //Register the user in firebase
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(Register.this,  "Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}

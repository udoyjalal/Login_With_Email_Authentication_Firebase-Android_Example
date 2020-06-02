package com.example.loginwithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText emailET, passwordET;
    Button signinBTNLog;
    TextView signupTVLog;

    FirebaseAuth mfirebaseAuth;
    FirebaseAuth.AuthStateListener mauthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mfirebaseAuth = FirebaseAuth.getInstance();
        emailET = (EditText)findViewById(R.id.inputEmail);
        passwordET = (EditText)findViewById(R.id.inputPass);
        signinBTNLog = (Button)findViewById(R.id.signinBtnLogActivity);
        signupTVLog = (TextView)findViewById(R.id.textsignupLogActivity);

        mauthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mfirebaseUser = mfirebaseAuth.getCurrentUser();
                if(mfirebaseUser != null){
                    Toast.makeText(LoginActivity.this, "You are Logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                }
                else{
                   // Toast.makeText(LoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };
        signinBTNLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString();
                String pass = passwordET.getText().toString();
                if(email.isEmpty()){
                    emailET.setError("Please enter email id");
                    emailET.requestFocus();
                }
                else if(pass.isEmpty()){
                    passwordET.setError("Please enter password");
                    passwordET.requestFocus();
                }
                else if(email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && pass.isEmpty())){
                    mfirebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(home);
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Login Error! Please Login Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(LoginActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupTVLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tomain = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(tomain);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mfirebaseAuth.addAuthStateListener(mauthStateListener);
    }
}

package com.rahat_jnu.expensetracker.ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.google.firebase.auth.FirebaseUser;
import com.rahat_jnu.expensetracker.ExpenseTrackerApp;
import com.rahat_jnu.expensetracker.R;
import com.rahat_jnu.expensetracker.ui.BaseActivity;
import com.rahat_jnu.expensetracker.ui.MainActivity;

import androidx.annotation.NonNull;

public class LoginActivity extends BaseActivity {
    private TextView singUpTV;
    private EditText emailET,passET;
    private Button loginBT;
    private ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }

        initialization();

        singUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,SingUp.class);
                startActivity(i);
            }
        });

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString();
                String pass = passET.getText().toString();
                if(email.equals("")){
                    Toast.makeText(LoginActivity.this, "Enter a Email", Toast.LENGTH_SHORT).show();
                }else if(pass.equals("") || pass.length()<6){
                    Toast.makeText(LoginActivity.this, "6 characters must", Toast.LENGTH_SHORT).show();
                }else {
                    logInWith(email,pass);
                }
            }
        });

    }

    private void logInWith(String email, String pass) {

        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                     Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                     intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                     startActivity(intent);
                     finish();
                     Toast.makeText(LoginActivity.this, "Log In Successful", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void initialization() {
        singUpTV = findViewById(R.id.login);
        loginBT = findViewById(R.id.loginBT);
        emailET = findViewById(R.id.userEmailET);
        passET = findViewById(R.id.passwordET);
        firebaseAuth = FirebaseAuth.getInstance();

    }

}

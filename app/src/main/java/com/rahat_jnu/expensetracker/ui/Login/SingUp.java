package com.rahat_jnu.expensetracker.ui.Login;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rahat_jnu.expensetracker.R;
import com.rahat_jnu.expensetracker.ui.BaseActivity;
import com.rahat_jnu.expensetracker.ui.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class SingUp extends BaseActivity {
    private TextView loginTV;
    private EditText emailET,passET,confirmPassET,nameET;
    private Button singUp;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mainDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        initialization();
        onClick();


    }

    private void onClick() {
        loginTV .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SingUp.this,LoginActivity.class);
                startActivity(i);
            }
        });
        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString();
                String pass = passET.getText().toString();
                String name = nameET.getText().toString();
                String confrimPass = confirmPassET.getText().toString();
                if(email.equals("")){
                    Toast.makeText(SingUp.this, "Enter a Email", Toast.LENGTH_SHORT).show();
                }else if(pass.equals("") || pass.length()<6){
                    Toast.makeText(SingUp.this, "6 characters must", Toast.LENGTH_SHORT).show();
                }else if (!pass.matches(confrimPass)){
                    Toast.makeText(SingUp.this, "Password doesn't matched!", Toast.LENGTH_SHORT).show();
                }else {
                    progressBar.isShown();
                    createNew(email,pass,name);
                }
            }
        });

    }

    private void initialization() {
        loginTV = findViewById(R.id.singup);
        emailET = findViewById(R.id.userEmailET);
        nameET = findViewById(R.id.userName);
        passET = findViewById(R.id.passwordET);
        confirmPassET = findViewById(R.id.ConfrimPasswords);
        singUp = findViewById(R.id.loginBT);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar4);
        mainDB  = FirebaseDatabase.getInstance().getReference();


    }
    private void createNew(final String email, final String pass, final String name) {
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    DatabaseReference userDB = mainDB.child("Users").child(firebaseAuth.getCurrentUser().getUid());
                    Map<String,Object> userMap = new HashMap<>();
                    userMap.put("email",email);
                    userMap.put("name",name);
                    userMap.put("email",email);
                    userDB.setValue(userMap);
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Account Created successfully, Now Log in", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SingUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

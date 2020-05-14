package com.example.cinemabookingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText username,password;
    private Button login,signup;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intialize();
        firebaseAuth=FirebaseAuth.getInstance();
    }
    public  void  Intialize(){
        username=findViewById(R.id.userET);
        password=findViewById(R.id.passET);

        login=findViewById(R.id.loginBTN);
        signup=findViewById(R.id.singupBTN);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,signup.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    public void login() {
        if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
            if (username.getText().toString().equals("admin@gmail.com")&&password.getText().toString().equals("admin123")){
                username.setText("");
                password.setText("");
                Intent intent =new Intent(MainActivity.this,adminpanel.class);
                startActivity(intent);
            }
            else if(firebaseAuth!=null){
                if (firebaseAuth.getCurrentUser()!=null){
                    firebaseAuth.signOut();
                }
                else {
                    firebaseAuth.signInWithEmailAndPassword(username.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent intent = new Intent(getApplicationContext(),dashboard.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,"Login Failed"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
        else if(username.getText().toString().isEmpty()){
            username.requestFocus();
            Toast.makeText(this,"Please Enter Your Email",Toast.LENGTH_SHORT).show();
        }
        else if(password.getText().toString().isEmpty()){
            password.requestFocus();
            Toast.makeText(this,"Please Enter Your Password",Toast.LENGTH_SHORT).show();
        }
    }
}
package com.example.cinemabookingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class changepass extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    EditText old,newpass,repass;
    Button savebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);

        firebaseAuth=FirebaseAuth.getInstance();
        old=findViewById(R.id.oldpassword);
        newpass=findViewById(R.id.newpassword);
        repass=findViewById(R.id.retypepass);
        savebtn=findViewById(R.id.save);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!old.getText().toString().isEmpty()&&!newpass.getText().toString().isEmpty()&&!repass.getText().toString().isEmpty()) {

                    if (newpass.getText().toString().equals(repass.getText().toString())){
                        final FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                        AuthCredential authCredential = EmailAuthProvider.getCredential(firebaseAuth.getCurrentUser().getEmail(),old.getText().toString());
                        user.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    user.updatePassword(newpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(),"Password Updated Successfully",Toast.LENGTH_SHORT).show();
                                                old.setText("");
                                                newpass.setText("");
                                                repass.setText("");
                                                Intent i = new Intent(getApplicationContext(),dashboard.class);
                                                startActivity(i);
                                                finish();
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Password didn't match ",Toast.LENGTH_SHORT).show();
                        newpass.setText("");
                        repass.setText("");
                        newpass.requestFocus();
                    }


                }
                else {
                    Toast.makeText(getApplicationContext(),"please Fill All Fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


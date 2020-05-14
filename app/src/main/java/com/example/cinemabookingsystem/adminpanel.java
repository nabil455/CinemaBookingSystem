package com.example.cinemabookingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class adminpanel extends AppCompatActivity {

    EditText start,name,quantity,price;
    Button add,logout;

    DatabaseReference fd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);

        intial();
    }
    public void intial(){
        start=findViewById(R.id.startET);
        name=findViewById(R.id.nameET);

        quantity=findViewById(R.id.quantityET);
        price=findViewById(R.id.priceET);

        add=findViewById(R.id.addBTN);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

        logout=findViewById(R.id.logoutBTN);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(adminpanel.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        fd= FirebaseDatabase.getInstance().getReference("Ticket List");
    }

    public void upload(){
        try {
            if(start.getText().toString().isEmpty() &&name.getText().toString().isEmpty() &&quantity.getText().toString().isEmpty() &&price.getText().toString().isEmpty() ){
                Toast.makeText(adminpanel.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
            }

            else {
                HashMap map = new HashMap();
                map.put("start", start.getText().toString());
                map.put("name", name.getText().toString());

                map.put("quantity", quantity.getText().toString());
                map.put("price", price.getText().toString());
                fd.push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(adminpanel.this, "Succesfully Uploaded", Toast.LENGTH_SHORT).show();
                        start.setText("");
                        name.setText("");
                        quantity.setText("");
                        price.setText("");
                        name.requestFocus();
                    }
                });
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Unable To upload"+e,Toast.LENGTH_SHORT).show();
        }
    }
}

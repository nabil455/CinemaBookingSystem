package com.example.cinemabookingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fragments.Booked;
import fragments.Home;
import fragments.Profile;

public class dashboard extends AppCompatActivity {

    BottomNavigationView objBNV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Connection();
        SetBNV();

        ChangeFragments(objHomeFrag);
    }


    Home objHomeFrag;
    Booked objBookedFrag;
    Profile objProfileFrag;

    public void Connection()
    {
        try
        {
            objBNV=findViewById(R.id.BNV);

            objHomeFrag = new Home();
            objBookedFrag = new Booked();
            objProfileFrag = new Profile();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Connection: "
                    +e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void SetBNV()
    {
        try
        {
            objBNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId())
                    {
                        case R.id.Homeitem:
                            Toast.makeText(dashboard.this, "Home Selected", Toast.LENGTH_SHORT).show();
                            ChangeFragments(objHomeFrag);
                            return true;
                        case R.id.Bookeditem:
                            Toast.makeText(dashboard.this, "Booked Selected", Toast.LENGTH_SHORT).show();
                            ChangeFragments(objBookedFrag);
                            return true;
                        case R.id.Profileitem:
                            Toast.makeText(dashboard.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                            ChangeFragments(objProfileFrag);
                            return true;
                    }
                    return false;
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Fragments", Toast.LENGTH_SHORT).show();
        }
    }

    private void ChangeFragments(Fragment objFrag)
    {
        try
        {
            FragmentTransaction objFragmentTransaction = getSupportFragmentManager().beginTransaction();
            objFragmentTransaction.replace(R.id.FL, objFrag);
            objFragmentTransaction.commit();
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void Logout(View view) {
        try
        {
            startActivity(new Intent(this, MainActivity.class));
        }
        catch (Exception e)
        {
            Toast.makeText(this, "MovetoSignUp: "
                    +e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}

package fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinemabookingsystem.MainActivity;
import com.example.cinemabookingsystem.R;
import com.example.cinemabookingsystem.changepass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile extends Fragment {

    private View profileview;
    private TextView profilename,changepassword;
    private Button signout,book;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    DatabaseReference databaseReference;
    public Profile() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profileview=inflater.inflate(R.layout.fragment_profile,container,false);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        intilalize();
        return profileview;

    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListner);
    }

    public void  intilalize(){
        changepassword=profileview.findViewById(R.id.chngPassET);
        profilename=profileview.findViewById(R.id.nameET);
        signout=profileview.findViewById(R.id.logoutBTN);
        book=profileview.findViewById(R.id.bookdBTN);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Booked Tickets");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            String Name = dataSnapshot1.child("Name").getValue().toString();
                            String start = dataSnapshot1.child("Start").getValue().toString();
                            String id = dataSnapshot1.child("uid").getValue().toString();
                            Toast.makeText(profileview.getContext(), "Id is: " + id + " Name: " + Name + " AT " + start, Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        mAuthListner=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(profileview.getContext(), MainActivity.class));
                }
            }
        };
        signout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                firebaseAuth.signOut();
                return true;
            }
        });
        changepassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(profileview.getContext(), changepass.class);
                startActivity(intent);
                return true;
            }
        });
        Showname();

    }
    public void Showname(){
        String id= FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("User").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                profilename.setText(name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


}
package fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cinemabookingsystem.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import Adpter.adpter;
import Model.model;


public class Booked extends Fragment {
    private View bookedview;
    RecyclerView recyclerView;

    adpter adapterclass;

    public Booked() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bookedview=inflater.inflate(R.layout.fragment_booked,container,false);
        recyclerView = bookedview.findViewById(R.id.bookRV);
        loaddata();
        return bookedview;
    }
    public void loaddata(){
        try{
            Query query = FirebaseDatabase.getInstance().getReference().child("Ticket List");
            FirebaseRecyclerOptions<model> options = new FirebaseRecyclerOptions.Builder<model>()
                    .setQuery(query, model.class)
                    .build();
            adapterclass = new adpter(options);
            recyclerView.setLayoutManager(new LinearLayoutManager(bookedview.getContext()));
            recyclerView.setAdapter(adapterclass);
        }
        catch (Exception e){

            Toast.makeText(bookedview.getContext(),"Load Data"+e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    public void onStart() {
        super.onStart();
        adapterclass.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterclass.stopListening();
    }

}
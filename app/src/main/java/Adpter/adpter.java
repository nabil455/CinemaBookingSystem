package Adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemabookingsystem.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import Model.model;

public class adpter extends FirebaseRecyclerAdapter<model,adpter.Holder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public adpter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final Holder holder, int i, @NonNull model model) {
        holder.start.setText(model.getStart());
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());
        holder.quantity.setText(model.getQuantity());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = getRef(holder.getAdapterPosition()).getKey();
                final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Ticket List");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            final String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                            String quantity = dataSnapshot.child(id).child("quantity").getValue().toString();
                            final String sl=dataSnapshot.child(id).child("start").getValue().toString();
                            final String el=dataSnapshot.child(id).child("name").getValue().toString();
                            int temp = Integer.parseInt(quantity);
                            if (temp <= 0) {
                                Toast.makeText(holder.itemView.getContext(), "No seats Available", Toast.LENGTH_SHORT).show();
                            } else {
                                temp = temp - 1;
                                HashMap map = new HashMap();
                                map.put("quantity", Integer.toString(temp));
                                databaseReference.child(id).updateChildren(map);
                                Toast.makeText(holder.itemView.getContext(), "Ticket Booked Successfully", Toast.LENGTH_SHORT).show();
                                final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Booked Tickets");
                                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        HashMap map1 = new HashMap();
                                        map1.put("uid",uid);
                                        map1.put("From",sl);
                                        map1.put("To",el);
                                        databaseReference1.push().setValue(map1);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(holder.itemView.getContext(), "Error"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(holder.itemView.getContext(), "Error"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varOfView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_singlerow, parent, false);
        return new Holder(varOfView);
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView start,name,price,quantity;
        public Holder(@NonNull View itemView) {
            super(itemView);
            start=itemView.findViewById(R.id.startTV);
            name=itemView.findViewById(R.id.nameTV);
            price=itemView.findViewById(R.id.priceTV);
            quantity=itemView.findViewById(R.id.seatTV);
        }
    }
}
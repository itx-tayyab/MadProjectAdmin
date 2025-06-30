package com.example.madprojectadmin;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectadmin.Adapters.adapterpendingorderrecyclerview;
import com.example.madprojectadmin.Modals.modalpendingorder;
import com.example.madprojectadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityPendingOrders extends AppCompatActivity {

    RecyclerView recyclerView;
    adapterpendingorderrecyclerview adapter;
    List<modalpendingorder> orderList;
    ImageView  BackButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitypendingorder);

        BackButton = findViewById(R.id.backbutton);
        BackButton.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.PendingOrderRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();

        adapter = new adapterpendingorderrecyclerview(orderList, this);
        recyclerView.setAdapter(adapter);

        loadPendingOrders();
    }

    private void loadPendingOrders() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("PendingOrders");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderList.clear();

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String userId = userSnapshot.getKey();
                    String name = userSnapshot.child("name").getValue(String.class);

                    int totalQty = 0;
                    for (DataSnapshot itemSnap : userSnapshot.child("CartItem").getChildren()) {
                        Integer qty = itemSnap.child("quantity").getValue(Integer.class);
                        if (qty != null) totalQty += qty;
                    }

                    orderList.add(new modalpendingorder(userId, name, totalQty));
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityPendingOrders.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.example.madprojectadmin;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectadmin.Adapters.adapterorderdetails;
import com.example.madprojectadmin.Modals.modalorderdetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class ActivityOrderDetails extends AppCompatActivity {

    private TextView nameTextView, phoneTextView, addressTextView, totalAmountTextView;
    private RecyclerView recyclerView;
    private adapterorderdetails adapter;
    private List<modalorderdetails> itemList;
    private String orderId;  // Declare the orderId here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityorderdetails);

        // Get dynamic orderId
        orderId = getIntent().getStringExtra("orderId");
        if (orderId == null || orderId.isEmpty()) {
            Toast.makeText(this, "Order ID not found", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if ID is missing
            return;
        }

        // Initialize UI
        nameTextView = findViewById(R.id.orderdetailname);
        phoneTextView = findViewById(R.id.orderdetailphone);
        addressTextView = findViewById(R.id.orderdetailaddress);
        totalAmountTextView = findViewById(R.id.orderdetailtotalamount);

        recyclerView = findViewById(R.id.OrderDetailsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        adapter = new adapterorderdetails(this, itemList);
        recyclerView.setAdapter(adapter);

        // Firebase reference
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("PendingOrders").child(orderId);

        // Load customer info
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nameTextView.setText(snapshot.child("name").getValue(String.class));
                phoneTextView.setText(snapshot.child("phone").getValue(String.class));
                addressTextView.setText(snapshot.child("address").getValue(String.class));
                Long totalAmount = snapshot.child("totalAmount").getValue(Long.class);
                totalAmountTextView.setText("Rs. " + totalAmount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        // Load cart items
        ref.child("CartItem").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemList.clear();
                for (DataSnapshot itemSnap : snapshot.getChildren()) {
                    modalorderdetails item = itemSnap.getValue(modalorderdetails.class);
                    itemList.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}

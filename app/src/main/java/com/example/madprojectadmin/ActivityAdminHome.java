package com.example.madprojectadmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityAdminHome extends AppCompatActivity {

    CardView AddMenu, AllItemMenu, Profile, CreateNewUser, Logout, DispatchOrder, PendingOrder;
    TextView tvPendingCount, tvCompletedCount, tvTotalAmount;

    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acctivityadminhome);

        // Initialize views
        AddMenu = findViewById(R.id.addmenu);
        AllItemMenu = findViewById(R.id.allitemmenu);
        Profile = findViewById(R.id.profile);
        CreateNewUser = findViewById(R.id.createnewuser);
        Logout = findViewById(R.id.logout);
        DispatchOrder = findViewById(R.id.dispatchorder);
        PendingOrder = findViewById(R.id.pendingorder);

        tvPendingCount = findViewById(R.id.countpendingorder);
        tvCompletedCount = findViewById(R.id.countcompletedorder);
        tvTotalAmount = findViewById(R.id.counttotalAmount);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Navigation
        AddMenu.setOnClickListener(v -> startActivity(new Intent(ActivityAdminHome.this, ActivityAddMenu.class)));
        AllItemMenu.setOnClickListener(v -> startActivity(new Intent(ActivityAdminHome.this, ActivityAllItemMenu.class)));
        Profile.setOnClickListener(v -> startActivity(new Intent(ActivityAdminHome.this, ActivityProfile.class)));
        CreateNewUser.setOnClickListener(v -> startActivity(new Intent(ActivityAdminHome.this, ActivityCreateNewUser.class)));
        DispatchOrder.setOnClickListener(v -> startActivity(new Intent(ActivityAdminHome.this, ActivityDispatchOrder.class)));
        PendingOrder.setOnClickListener(v -> startActivity(new Intent(ActivityAdminHome.this, ActivityPendingOrders.class)));

        fetchOrderStats();
    }

    private void fetchOrderStats() {
        DatabaseReference pendingRef = databaseReference.child("PendingOrders");
        DatabaseReference completedRef = databaseReference.child("CompletedOrders");

        // Count Pending Orders
        pendingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int pendingCount = (int) snapshot.getChildrenCount();
                tvPendingCount.setText(String.valueOf(pendingCount));
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        // Count Completed Orders and Total Amount
        completedRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int completedCount = 0;
                int totalAmount = 0;

                for (DataSnapshot order : snapshot.getChildren()) {
                    completedCount++;
                    Long amount = order.child("totalAmount").getValue(Long.class);
                    if (amount != null) {
                        totalAmount += amount;
                    }
                }

                tvCompletedCount.setText(String.valueOf(completedCount));
                tvTotalAmount.setText(String.valueOf(totalAmount));
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }


}

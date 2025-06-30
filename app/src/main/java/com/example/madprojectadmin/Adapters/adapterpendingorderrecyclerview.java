package com.example.madprojectadmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectadmin.ActivityOrderDetails;
import com.example.madprojectadmin.Modals.modalpendingorder;
import com.example.madprojectadmin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class adapterpendingorderrecyclerview extends RecyclerView.Adapter<adapterpendingorderrecyclerview.ViewHolder> {
    private List<modalpendingorder> orderList;
    private Context context;

    public adapterpendingorderrecyclerview(List<modalpendingorder> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activitypendingorderrecyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        modalpendingorder order = orderList.get(position);

        holder.nameText.setText(order.getName());
        holder.qtyText.setText("Total Quantity: " + order.getTotalQuantity());

        // Open order details
        holder.itemView.setOnClickListener(v -> {
            if (order.getOrderId() == null || order.getOrderId().isEmpty()) {
                Toast.makeText(context, "Error: Order ID is missing", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(context, ActivityOrderDetails.class);
            intent.putExtra("orderId", order.getOrderId());
            context.startActivity(intent);
        });

        // Dispatch button logic
        holder.dispatchBtn.setOnClickListener(v -> {
            String orderId = order.getOrderId();
            if (orderId == null || orderId.isEmpty()) {
                Toast.makeText(context, "Invalid order ID", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference pendingRef = dbRef.child("PendingOrders").child(orderId);

            pendingRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult().exists()) {
                    Object orderData = task.getResult().getValue();

                    dbRef.child("CompletedOrders").child(orderId).setValue(orderData)
                            .addOnSuccessListener(aVoid -> {
                                pendingRef.removeValue()
                                        .addOnSuccessListener(aVoid1 -> {
                                            Toast.makeText(context, "Order dispatched", Toast.LENGTH_SHORT).show();
                                            // Optionally: Remove from list and refresh UI
                                            orderList.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, orderList.size());
                                        })
                                        .addOnFailureListener(e ->
                                                Toast.makeText(context, "Failed to delete from PendingOrders", Toast.LENGTH_SHORT).show());
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(context, "Failed to move to CompletedOrders", Toast.LENGTH_SHORT).show());
                } else {
                    Toast.makeText(context, "Order not found", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, qtyText;
        Button dispatchBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.customer_name);
            qtyText = itemView.findViewById(R.id.quantity);
            dispatchBtn = itemView.findViewById(R.id.accept);
        }
    }
}

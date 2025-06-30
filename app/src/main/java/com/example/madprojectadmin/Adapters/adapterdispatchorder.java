package com.example.madprojectadmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectadmin.Modals.modaldispatchorder;
import com.example.madprojectadmin.R;

import java.util.List;

public class adapterdispatchorder extends RecyclerView.Adapter<adapterdispatchorder.DispatchOrderViewHolder> {

    private Context context;
    private List<modaldispatchorder> orderList;

    public adapterdispatchorder(Context context, List<modaldispatchorder> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public DispatchOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activitydispatchorderrecyclerview, parent, false);
        return new DispatchOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DispatchOrderViewHolder holder, int position) {
        modaldispatchorder order = orderList.get(position);

        holder.customerName.setText(order.getCustomerName());
        holder.paymentStatus.setText(order.getPaymentStatus());

        // Set color based on payment status
        String status = order.getPaymentStatus().toLowerCase();

        int color;
        switch (status) {
            case "received":
                color = ContextCompat.getColor(context, R.color.green); // Green color for received
                break;
            case "not received":
                color = ContextCompat.getColor(context, R.color.red); // Red color for not received
                break;
            case "pending":
            default:
                color = ContextCompat.getColor(context, R.color.grey); // Grey color for pending
                break;
        }

        holder.statusCard.setCardBackgroundColor(color); // Set the background color of the CardView
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class DispatchOrderViewHolder extends RecyclerView.ViewHolder {

        TextView customerName, paymentStatus;
        CardView statusCard;

        public DispatchOrderViewHolder(View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerNameTextView); // Match the ID
            paymentStatus = itemView.findViewById(R.id.paymentStatusTextView); // Match the ID
            statusCard = itemView.findViewById(R.id.statusCardView); // Match the ID
        }
    }
}

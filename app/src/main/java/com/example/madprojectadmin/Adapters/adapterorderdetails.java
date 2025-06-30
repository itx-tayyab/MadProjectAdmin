package com.example.madprojectadmin.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.madprojectadmin.Modals.modalorderdetails;
import com.example.madprojectadmin.R;

import java.util.List;

public class adapterorderdetails extends RecyclerView.Adapter<adapterorderdetails.ViewHolder> {

    private Context context;
    private List<modalorderdetails> itemList;

    public adapterorderdetails(Context context, List<modalorderdetails> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public adapterorderdetails.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activityorderdetailsrecyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterorderdetails.ViewHolder holder, int position) {
        modalorderdetails item = itemList.get(position);

        holder.text1.setText(item.getItemName());
        holder.text2.setText("Quantity: " + item.getQuantity());
        holder.text3.setText("Price: Rs. " + item.getItemPrice());

        Glide.with(context)
                .load(item.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.img1);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img1;
        TextView text1, text2, text3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img1 = itemView.findViewById(R.id.img1);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            text3 = itemView.findViewById(R.id.text3);
        }
    }
}


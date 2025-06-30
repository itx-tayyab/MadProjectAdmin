package com.example.madprojectadmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.madprojectadmin.Modals.modalallitemmenu;
import com.example.madprojectadmin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class adapterallitemmenurecyclerview extends RecyclerView.Adapter<adapterallitemmenurecyclerview.ViewHolder> {

    private Context context;
    private List<modalallitemmenu> menuItemList;

    public adapterallitemmenurecyclerview(Context context, List<modalallitemmenu> menuItemList) {
        this.context = context;
        this.menuItemList = menuItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activityallitemmenurecyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        modalallitemmenu currentItem = menuItemList.get(position); // ✅ FIXED: use currentItem instead of undeclared variable

        holder.title.setText(currentItem.getTitle());
        holder.subtitle.setText(currentItem.getSubtitle());
        holder.price.setText("Rs " + currentItem.getPrice());

        Glide.with(context)
                .load(currentItem.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.image);

        holder.btnDelete.setOnClickListener(v -> {
            String key = currentItem.getFirebaseKey();
            String category = currentItem.getCategory();

            if (key != null && category != null) {
                DatabaseReference itemRef = FirebaseDatabase.getInstance()
                        .getReference("menu_items")
                        .child(category)
                        .child(key);

                itemRef.removeValue().addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                    menuItemList.remove(position);
                    notifyItemRemoved(position);
                }).addOnFailureListener(e -> {
                    Toast.makeText(context, "Delete failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            } else {
                Toast.makeText(context, "Missing key or category", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return menuItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, subtitle, price;
        AppCompatButton btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img1);
            title = itemView.findViewById(R.id.text1);
            subtitle = itemView.findViewById(R.id.text2);
            price = itemView.findViewById(R.id.text3);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}

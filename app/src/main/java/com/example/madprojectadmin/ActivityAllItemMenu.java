package com.example.madprojectadmin;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.madprojectadmin.Adapters.adapterallitemmenurecyclerview;
import com.example.madprojectadmin.Modals.modalallitemmenu;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityAllItemMenu extends AppCompatActivity {

    RecyclerView recyclerView;
    adapterallitemmenurecyclerview adapter;
    List<modalallitemmenu> menuItemList;
    DatabaseReference menuRef;
    ImageView BackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityallitemmenu);

        BackButton = findViewById(R.id.backbutton);
        BackButton.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.AllItemMenuRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuItemList = new ArrayList<>();
        adapter = new adapterallitemmenurecyclerview(this, menuItemList);
        recyclerView.setAdapter(adapter);

        menuRef = FirebaseDatabase.getInstance().getReference("menu_items");

        fetchAllMenuItems();
    }

    private void fetchAllMenuItems() {
        menuRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                menuItemList.clear();
                for (DataSnapshot categorySnap : snapshot.getChildren()) {
                    for (DataSnapshot itemSnap : categorySnap.getChildren()) {
                        if (itemSnap.hasChild("name")) {
                            String name = itemSnap.child("name").getValue(String.class);
                            String description = itemSnap.child("description").getValue(String.class);
                            String imageUrl = itemSnap.child("image_url").getValue(String.class);

                            // 🔐 Safe price extraction
                            double price = 0;
                            Object priceObj = itemSnap.child("price").getValue();
                            if (priceObj instanceof Long) {
                                price = ((Long) priceObj).doubleValue();
                            } else if (priceObj instanceof Double) {
                                price = (Double) priceObj;
                            } else if (priceObj instanceof String) {
                                try {
                                    price = Double.parseDouble((String) priceObj);
                                } catch (NumberFormatException e) {
                                    price = 0;
                                }
                            }

                            String key = itemSnap.getKey();
                            String category = categorySnap.getKey();

                            modalallitemmenu item = new modalallitemmenu(
                                    imageUrl,
                                    name,
                                    description,
                                    1,
                                    price,
                                    key
                            );
                            item.setCategory(category); // you'll need to add a `category` field + setter

                            menuItemList.add(item);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivityAllItemMenu.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

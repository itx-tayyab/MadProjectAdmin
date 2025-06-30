package com.example.madprojectadmin;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectadmin.Adapters.adapterallitemmenurecyclerview;
import com.example.madprojectadmin.Adapters.adapterdispatchorder;
import com.example.madprojectadmin.Modals.modalallitemmenu;
import com.example.madprojectadmin.Modals.modaldispatchorder;

import java.util.ArrayList;
import java.util.List;

public class ActivityDispatchOrder extends AppCompatActivity {

    private RecyclerView recyclerView;
    private adapterdispatchorder adapter;
    private List<modaldispatchorder> ItemMenu;

    ImageView BackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Use the correct layout file for dispatch orders
        setContentView(R.layout.activitydispatchorder);

        recyclerView = findViewById(R.id.DispatchOrderRecyclerView); // Use the correct RecyclerView ID
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemMenu = new ArrayList<>();
        ItemMenu.add(new modaldispatchorder("Joe", "Received"));
        ItemMenu.add(new modaldispatchorder("Jane Smith",  "Pending"));
        ItemMenu.add(new modaldispatchorder("Elly Clare",   "Not Received"));
        ItemMenu.add(new modaldispatchorder("Jane Smith",  "Pending"));

        adapter = new adapterdispatchorder(this, ItemMenu);
        recyclerView.setAdapter(adapter);


        BackButton = findViewById(R.id.backbutton);
        BackButton.setOnClickListener(v -> onBackPressed());
    }
}

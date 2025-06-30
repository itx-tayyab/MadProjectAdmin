package com.example.madprojectadmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ActivityAddMenu extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    EditText ItemName, ItemPrice, ItemImage, ItemDesc;
    Spinner ItemCategory;
    Button AddItem;
    ImageView imagePreview, BackButton;
    Uri selectedImageUri;
    DatabaseReference databaseReference;

    Cloudinary cloudinary;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityaddmenu);

        // Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("menu_items");

        // Cloudinary config
        Map config = new HashMap();
        config.put("cloud_name", "dgny90vlr");
        config.put("api_key", "987326812797591");
        config.put("api_secret", "A0rCw_DkY1mJsvWhujq3JZDSnUA");
        cloudinary = new Cloudinary(config);

        // UI References
        ItemName = findViewById(R.id.itemname);
        ItemPrice = findViewById(R.id.itemprice);
        ItemImage = findViewById(R.id.itemimage);
        ItemDesc = findViewById(R.id.itemdesc);
        ItemCategory = findViewById(R.id.itemcategory);
        AddItem = findViewById(R.id.btn_additem);
        BackButton = findViewById(R.id.backbutton);
        imagePreview = findViewById(R.id.imagePreview);

        // Spinner data
        String[] categories = {"Select Category","snackers", "pizza", "burgers", "Drinks", "Shawarma","Sandwiches"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ItemCategory.setAdapter(categoryAdapter);

        BackButton.setOnClickListener(v -> onBackPressed());

        ItemImage.setFocusable(false);
        ItemImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });

        AddItem.setOnClickListener(v -> {
            String name = ItemName.getText().toString().trim();
            String price = ItemPrice.getText().toString().trim();
            String desc = ItemDesc.getText().toString().trim();
            String category = ItemCategory.getSelectedItem().toString();

            if (name.isEmpty() || price.isEmpty() || desc.isEmpty() || selectedImageUri == null || category.equals("Select Category")) {
                Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            } else {
                uploadImageToCloudinary(name, price, desc, category);
            }
        });
    }

    private void uploadImageToCloudinary(String name, String price, String desc, String category) {
        try {
            File file = new File(PathUtil.getPath(this, selectedImageUri));
            new Thread(() -> {
                try {
                    Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
                    String imageUrl = uploadResult.get("secure_url").toString();

                    runOnUiThread(() -> saveToFirebase(name, price, desc, category, imageUrl));
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() ->
                            Toast.makeText(ActivityAddMenu.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Image upload error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToFirebase(String name, String price, String desc, String category, String imageUrl) {
        String id = databaseReference.child(category).push().getKey(); // push under category

        Map<String, String> item = new HashMap<>();
        item.put("id", id);
        item.put("name", name);
        item.put("price", price);
        item.put("description", desc);
        item.put("category", category);
        item.put("image_url", imageUrl);

        // Save under menu_items/category/id
        databaseReference.child(category).child(id).setValue(item).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void clearFields() {
        ItemName.setText("");
        ItemPrice.setText("");
        ItemDesc.setText("");
        ItemImage.setText("");
        ItemCategory.setSelection(0);
        imagePreview.setImageDrawable(null);
        selectedImageUri = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            ItemImage.setText(selectedImageUri.toString());
            imagePreview.setImageURI(selectedImageUri);
            Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show();
        }
    }
}

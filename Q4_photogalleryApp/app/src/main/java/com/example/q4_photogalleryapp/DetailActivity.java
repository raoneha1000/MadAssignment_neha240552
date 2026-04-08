package com.example.q4_photogalleryapp;

import android.net.Uri;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageView = findViewById(R.id.imageView);
        TextView name = findViewById(R.id.name);
        TextView path = findViewById(R.id.path);
        TextView size = findViewById(R.id.size);
        TextView date = findViewById(R.id.date);
        Button deleteBtn = findViewById(R.id.deleteBtn);

        String filePath = getIntent().getStringExtra("path");

        if (filePath == null) {
            Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        File file = new File(filePath);

        if (!file.exists()) {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load image safely
        imageView.setImageURI(null);
        imageView.setImageURI(Uri.fromFile(file));

        name.setText("Name: " + file.getName());
        path.setText("Path: " + file.getAbsolutePath());
        size.setText("Size: " + (file.length() / 1024) + " KB");

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");

        date.setText("Date: " + sdf.format(new Date(file.lastModified())));

        deleteBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Image")
                    .setMessage("Are you sure you want to delete this image?")
                    .setPositiveButton("Yes", (d, i) -> {
                        if (file.delete()) {
                            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}
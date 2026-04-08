package com.example.q4_photogalleryapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Uri imageUri;

    // 📸 Camera launcher
    private final ActivityResultLauncher<Uri> cameraLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.TakePicture(),
                    result -> {
                        if (result) {
                            Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

    // 📂 Folder picker launcher
    private final ActivityResultLauncher<Intent> folderPickerLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Uri folderUri = result.getData().getData();

                            Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                            intent.putExtra("folderUri", folderUri.toString());
                            startActivity(intent);
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 🔐 Camera permission
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
        }

        Button btnCamera = findViewById(R.id.btnCamera);
        Button btnGallery = findViewById(R.id.btnGallery);
        Button btnSelectFolder = findViewById(R.id.btnFolder);

        // 📸 Take Photo
        btnCamera.setOnClickListener(v -> openCamera());

        // 🖼️ Open default gallery
        btnGallery.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
            startActivity(intent);
        });

        // 📂 Select folder
        btnSelectFolder.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            folderPickerLauncher.launch(intent);
        });
    }

    // 📸 Open camera
    private void openCamera() {
        File file = new File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "IMG_" + System.currentTimeMillis() + ".jpg"
        );

        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageUri = FileProvider.getUriForFile(
                this,
                getPackageName() + ".provider",
                file
        );

        cameraLauncher.launch(imageUri);
    }

    // 🔐 Permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
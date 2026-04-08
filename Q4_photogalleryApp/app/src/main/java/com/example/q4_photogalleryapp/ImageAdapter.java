package com.example.q4_photogalleryapp;

import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    List<File> images;
    OnClickListener listener;

    interface OnClickListener {
        void onClick(File file);
    }

    public ImageAdapter(List<File> images, OnClickListener listener) {
        this.images = images;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ViewHolder(ImageView img) {
            super(img);
            this.img = img;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ImageView img = new ImageView(parent.getContext());

        img.setPadding(8, 8, 8, 8);

        int size = parent.getResources().getDisplayMetrics().widthPixels / 3;
        img.setLayoutParams(new ViewGroup.LayoutParams(size, size));

        img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        return new ViewHolder(img);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        File file = images.get(position);

        if (file.exists()) {
            holder.img.setImageURI(null); // prevent flicker
            holder.img.setImageURI(Uri.fromFile(file));
        }

        holder.img.setOnClickListener(v -> listener.onClick(file));
    }

    @Override
    public int getItemCount() {
        return images != null ? images.size() : 0;
    }
}
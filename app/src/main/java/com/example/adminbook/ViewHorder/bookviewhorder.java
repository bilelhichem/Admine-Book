package com.example.adminbook.ViewHorder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminbook.R;

public class bookviewhorder extends RecyclerView.ViewHolder {
    public ImageView imageView , superelem ;
    public TextView namebook , autheurbook ;
    public bookviewhorder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        namebook = itemView.findViewById(R.id.namebook);
        autheurbook = itemView.findViewById(R.id.autheurbook);
        superelem = itemView.findViewById(R.id.superelem);
    }
}

package com.example.adminbook.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminbook.Model.bookmodel;
import com.example.adminbook.R;
import com.example.adminbook.ViewHorder.bookviewhorder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class bookadapter extends RecyclerView.Adapter<bookviewhorder> {
    Context context ;
    ArrayList<bookmodel> bookmodels ;
    DatabaseReference datadb ,datafav;

    public bookadapter(Context context, ArrayList<bookmodel> bookmodels) {
        this.context = context;
        this.bookmodels = bookmodels;
        if (context != null){
            datadb = FirebaseDatabase.getInstance(context.getString(R.string.Db_url)).getReference().child("bookpdf");
             datafav = FirebaseDatabase.getInstance(context.getString(R.string.Db_url)).getReference().child("Favorite");

        }
    }

    @NonNull
    @Override
    public bookviewhorder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book,parent,false);
        return  new bookviewhorder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull bookviewhorder holder, int position) {
        Picasso.get().load(bookmodels.get(position).getImgbook()).into(holder.imageView);
        holder.namebook.setText(bookmodels.get(position).getNamebook());
        holder.autheurbook.setText(bookmodels.get(position).getAuteur());
//
holder.superelem.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        AlertDialog.Builder mydialog = new AlertDialog.Builder(context);
        mydialog.setTitle("Delete "+bookmodels.get(position).getNamebook());
        mydialog.setMessage("Do you really want to delete "
                +bookmodels.get(position).getNamebook()+"?");



        mydialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                datadb.child(bookmodels.get(position).getBookid())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(context, "Product Remove", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(context, "Error , check your internet connexion", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                datafav.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            datafav.child(dataSnapshot.getKey()).child(bookmodels.get(position).getBookid())
                                    .removeValue();

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });







            }
        });

        mydialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });




        mydialog.show();





    }
});










        }





    @Override
    public int getItemCount() {
        return bookmodels.size();
    }
}

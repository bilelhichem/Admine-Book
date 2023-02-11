package com.example.adminbook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adminbook.Adapter.bookadapter;
import com.example.adminbook.Model.bookmodel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ajoutbookfrag extends Fragment {
    RecyclerView booklist ;
    View mview ;
    DatabaseReference datadb ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_ajoutbookfrag, container, false);
     init();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        booklist.setLayoutManager(gridLayoutManager);
        learntodatabase();
        return  mview ;
    }

    public void init(){
        booklist = mview.findViewById(R.id.booklist);
        datadb = FirebaseDatabase.getInstance(getActivity().getString(R.string.Db_url)).getReference().child("bookpdf");
    }



    public void learntodatabase(){
        ArrayList<bookmodel> bookmodels = new ArrayList<>();
        datadb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            bookmodels.clear();
            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                bookmodel bookmodel = dataSnapshot.getValue(com.example.adminbook.Model.bookmodel.class);
                bookmodels.add(bookmodel);
            }
                bookadapter bookadapter = new bookadapter(getActivity(),bookmodels);
            booklist.setAdapter(bookadapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
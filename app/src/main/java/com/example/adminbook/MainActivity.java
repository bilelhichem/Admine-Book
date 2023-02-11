package com.example.adminbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
CardView cardhome , cardajoute ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.FrameLayout,new homefrag())
                .commit();
        cardhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardhome.setCardBackgroundColor(getColor(R.color.red));
                cardajoute.setCardBackgroundColor(getColor(R.color.dirbrq));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrameLayout,new homefrag())
                        .commit();
            }
        });

        cardajoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardhome.setCardBackgroundColor(getColor(R.color.dirbrq));
                cardajoute.setCardBackgroundColor(getColor(R.color.red));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrameLayout,new ajoutbookfrag())
                        .commit();
            }
        });



    }

    public void init(){
        cardhome = findViewById(R.id.cardhome);
        cardajoute = findViewById(R.id.cardajoute);

    }






}
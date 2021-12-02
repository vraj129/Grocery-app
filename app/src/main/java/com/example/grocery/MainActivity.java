package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView exclusive_recycler,groceries_recycler,bestsellerecycler;
    RecyclerView.Adapter exclusive_adapter,groceries_adapter;
    ImageView cart,logout;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        ImageSlider imageSlider = findViewById(R.id.image_slider);
        cart = findViewById(R.id.cart);
        logout = findViewById(R.id.logout);
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent i = new Intent(MainActivity.this,LoginRegistration.class);
                startActivity(i);
                finish();
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,CartActivity.class);
                startActivity(i);
            }
        });
        List<SlideModel> slideModels= new ArrayList<>();
        SlideModel e1 = new SlideModel(R.drawable.fresh3,ScaleTypes.FIT);
        SlideModel e2 = new SlideModel(R.drawable.fresh2,ScaleTypes.FIT);
        SlideModel e3 = new SlideModel(R.drawable.fresh1,ScaleTypes.FIT);
        slideModels.add(e1);
        slideModels.add(e2);
        slideModels.add(e3);
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);


        exclusive_recycler= findViewById(R.id.exclusiverecycler);
        groceries_recycler= findViewById(R.id.groceriesrecycler);
        bestsellerecycler= findViewById(R.id.bestsellerecycler);
        exclusiverecycler();
        groceriesrecycler();
        bestselle_recycler();
    }
    private void exclusiverecycler()
    {
        exclusive_recycler.setHasFixedSize(true);
        exclusive_recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<exclusive_model> exclusive_models = new ArrayList<>();
        exclusive_models.add(new exclusive_model(R.drawable.fruit1,"Organic Bananas","7pcs, Priceg"));
        exclusive_models.add(new exclusive_model(R.drawable.fruit2,"Red Apple","1kg, Priceg"));
        exclusive_models.add(new exclusive_model(R.drawable.fruit3,"King Mangoes","8pcs, Priceg"));
        exclusive_models.add(new exclusive_model(R.drawable.fruit4,"Juicy Watermelon","3kg, Priceg"));

        exclusive_adapter = new exclusive_adapter(exclusive_models,this);
        exclusive_recycler.setAdapter(exclusive_adapter);
    }
    private void groceriesrecycler()
    {

        GradientDrawable gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        GradientDrawable gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        GradientDrawable gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});

        groceries_recycler.setHasFixedSize(true);
        groceries_recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<Groceries_model> groceries_models = new ArrayList<>();
        groceries_models.add(new Groceries_model(R.drawable.grains1,"Pulses",gradient1));
        groceries_models.add(new Groceries_model(R.drawable.grains2,"Rice",gradient2));
        groceries_models.add(new Groceries_model(R.drawable.grains3,"Wheat",gradient3));
        groceries_models.add(new Groceries_model(R.drawable.grains4,"Dal",gradient4));

        groceries_adapter = new Groceries_adapter(MainActivity.this,groceries_models);
        groceries_recycler.setAdapter(groceries_adapter);
    }
    private void bestselle_recycler()
    {
        bestsellerecycler.setHasFixedSize(true);
        bestsellerecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<exclusive_model> exclusive_models = new ArrayList<>();
        exclusive_models.add(new exclusive_model(R.drawable.best1,"Red Pepper","7pcs, Priceg"));
        exclusive_models.add(new exclusive_model(R.drawable.best2,"Ginger","1kg, Priceg"));
        exclusive_models.add(new exclusive_model(R.drawable.best3,"Turmeric","8pcs, Priceg"));
        exclusive_models.add(new exclusive_model(R.drawable.best4,"Asafoetida","3kg, Priceg"));

        exclusive_adapter = new exclusive_adapter(exclusive_models,this);
        bestsellerecycler.setAdapter(exclusive_adapter);
    }
}
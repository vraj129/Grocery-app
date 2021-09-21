package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class GrainDetails extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_grain_details);
        imageView = findViewById(R.id.grain_image);
        textView = findViewById(R.id.grain_name);
        String name = getIntent().getStringExtra("name");
        String id = getIntent().getStringExtra("image");
        textView.setText(name);
        imageView.setImageResource(Integer.parseInt(id));
     }
}
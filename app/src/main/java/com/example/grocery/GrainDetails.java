package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class GrainDetails extends AppCompatActivity {
    int quantity = 1;

    ImageView imageView,additem,removeitem;
    TextView textView,quantity_no;
    Button addtocart;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_grain_details);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        imageView = findViewById(R.id.grain_image);
        textView = findViewById(R.id.grain_name);
        String name = getIntent().getStringExtra("name");
        String id = getIntent().getStringExtra("image");
        textView.setText(name);
        imageView.setImageResource(Integer.parseInt(id));
        additem = findViewById(R.id.additem);
        removeitem = findViewById(R.id.removeitem);
        quantity_no = findViewById(R.id.quantity);
        addtocart = findViewById(R.id.addtocartbtn);
        //quantity = Integer.parseInt(String.valueOf(quantity_no.getText()));
        removeitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity>1)
                {
                    quantity--;
                    quantity_no.setText(String.valueOf(quantity));
                }
            }
        });
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity<10)
                {
                    quantity++;
                    quantity_no.setText(String.valueOf(quantity));
                }
            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedtoCart();
            }
        });
     }

    private void addedtoCart() {
        String saveCurrentDate,saveCurrentTime;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM,dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calendar.getTime());
        int totalprice = 100 * quantity;
        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("productName",textView.getText().toString());
        cartMap.put("productPrice","100");
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("totalQuantity",String.valueOf(quantity));
        cartMap.put("totalPrice",String.valueOf(totalprice));

        firestore.collection("currentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(GrainDetails.this,"Added To Cart",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
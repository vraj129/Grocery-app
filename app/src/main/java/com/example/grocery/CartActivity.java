package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grocery.Adapter.CartAdapter;
import com.example.grocery.Model.CartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    List<CartModel> cartModelList;
    TextView textView;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    LinearLayout linearLayout;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.cartrecycler);
        textView = findViewById(R.id.textView);
        linearLayout = findViewById(R.id.linearlayout);
        constraintLayout = findViewById(R.id.constraint);
        LocalBroadcastManager.getInstance(CartActivity.this)
                .registerReceiver(mMBroadcastReceiver,new IntentFilter("TotalAmount"));

        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));

        cartModelList = new ArrayList<>();
        cartAdapter = new CartAdapter(CartActivity.this,cartModelList);
        recyclerView.setAdapter(cartAdapter);
        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("currentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                        CartModel cartModel = documentSnapshot.toObject(CartModel.class);
                        cartModelList.add(cartModel);
                        cartAdapter.notifyDataSetChanged();

                    }
                }

            }
        });

    }

    public BroadcastReceiver mMBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                int totalBill = intent.getIntExtra("totalAmount",0);
                textView.setText("Total Bill : ₹"+totalBill);
        }
    };

}
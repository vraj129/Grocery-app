package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.cartrecycler);
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
}
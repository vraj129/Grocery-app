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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocery.Adapter.CartAdapter;
import com.example.grocery.Model.CartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    boolean status = false;
    FirebaseFirestore firestore;
    LinearLayout linearLayout;
    ConstraintLayout constraintLayout;
    ProgressBar progressBar;

    DatabaseReference databaseReference;
    String email11;
    int amount=0;
    Button buyButton;
    private static final String TAG = "Cart Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cart);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        getUserData();
        recyclerView = findViewById(R.id.cartrecycler);
        textView = findViewById(R.id.textView);
        linearLayout = findViewById(R.id.linearlayout);
        constraintLayout = findViewById(R.id.constraint);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));

        cartModelList = new ArrayList<>();
        cartAdapter = new CartAdapter(CartActivity.this,cartModelList,email11);
        recyclerView.setAdapter(cartAdapter);
        firestore.collection("currentUser").document(auth.getCurrentUser().getUid())
                .collection("Appointment").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    linearLayout.setVisibility(View.GONE);
                    constraintLayout.setVisibility(View.VISIBLE);


                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                        String documentId= documentSnapshot.getId();
                                CartModel cartModel = documentSnapshot.toObject(CartModel.class);
                                cartModel.setDocumentId(documentId);
                        cartModelList.add(cartModel);
                        Log.d(TAG,"Date : "+documentSnapshot.toString());
                        Log.d(TAG,"Date Count : "+cartModelList.size());
                        cartAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    if(cartModelList.size() == 0 ){
                        status = true;
                        progressBar.setVisibility(View.GONE);
                        constraintLayout.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }

                    //calculateTotalAmount(cartModelList);
                    //amount = calculateTotalAmount(cartModelList);
//                    if(amount == 0)
//                    {
//                        status = true;
//                        progressBar.setVisibility(View.GONE);
//                        constraintLayout.setVisibility(View.GONE);
//                        linearLayout.setVisibility(View.VISIBLE);
//                        Toast.makeText(CartActivity.this, "Your Cart Is Empty", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });


    }
    private void getUserData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot snapshot = task.getResult();
                    email11 = String.valueOf(snapshot.child("email").getValue());
                    Log.d("CartActivity","Email : "+email11);
                }
                else {
                    Log.d("CartActivity","Error Fetching Data");
                }
            }
        });

    }

//    private int calculateTotalAmount(List<CartModel> cartModelList) {
//
//        int totalAmount = 0;
//        for(CartModel model : cartModelList)
//        {
//            totalAmount += Integer.parseInt(model.getTotalPrice());
//        }
//        textView.setText("Total Amount : ₹"+String.valueOf(totalAmount));
//        return totalAmount;
//    }


}
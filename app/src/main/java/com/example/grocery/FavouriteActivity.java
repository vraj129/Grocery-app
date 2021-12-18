package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.grocery.Adapter.StoreAdapter;
import com.example.grocery.Model.AllStoreModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StoreAdapter storeAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    List<AllStoreModel> modelList;
    ProgressBar progressBar;
    LinearLayout ll1;
    EditText searchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_favourite);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recycler);
        ll1= findViewById(R.id.ll1);
        searchBox = findViewById(R.id.search1);
        progressBar=findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.VISIBLE);
       // recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(FavouriteActivity.this));
        modelList = new ArrayList<>();
        storeAdapter=new StoreAdapter(FavouriteActivity.this,modelList,1);
        recyclerView.setAdapter(storeAdapter);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                progressBar.setVisibility(View.INVISIBLE);
                filter(editable.toString());
            }
        });

        firestore.collection("currentUser").document(auth.getCurrentUser().getUid())
                .collection("Favourites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                        AllStoreModel model = documentSnapshot.toObject(AllStoreModel.class);
                        modelList.add(model);
                        Log.d("Fav","Data : "+model);
                        storeAdapter.notifyDataSetChanged();

                    }
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    if(modelList.size() == 0){
                        ll1.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
    private void filter(String text) {
        List<AllStoreModel> list = new ArrayList<>();
        for(AllStoreModel model : modelList){
            if(model.getName().toLowerCase().contains(text.toLowerCase())){
                list.add(model);
            }
        }
        storeAdapter.filterlist(list);
    }
}
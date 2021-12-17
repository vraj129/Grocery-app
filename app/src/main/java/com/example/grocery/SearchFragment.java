package com.example.grocery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.grocery.Adapter.StoreAdapter;
import com.example.grocery.Model.AllStoreModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    RecyclerView storerecycler;
    StoreAdapter storeAdapter;
    FirebaseFirestore firestore;
    List<AllStoreModel> modelList;
    EditText searchBox;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);
        firestore = FirebaseFirestore.getInstance();
        storerecycler = root.findViewById(R.id.recycler);
        searchBox = root.findViewById(R.id.search);
        progressBar = root.findViewById(R.id.progress_circular);

        modelList = new ArrayList<>();
        storerecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        storeAdapter = new StoreAdapter(getActivity(),modelList);

        storerecycler.setAdapter(storeAdapter);

        firestore.collection("AllStore")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            storerecycler.setVisibility(View.VISIBLE);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AllStoreModel model = document.toObject(AllStoreModel.class);
                                modelList.add(model);
                                storeAdapter.notifyDataSetChanged();
                            }
                        } else {

                        }
                    }
                });

        return root;
    }
}
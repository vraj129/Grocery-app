package com.example.grocery;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.grocery.Adapter.ExclusiveAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {



    RecyclerView Exclusive_recycler,groceries_recycler,bestsellerecycler;
    ExclusiveAdapter exclusiveAdapter,getExclusiveAdapter;
    Groceries_adapter groceries_adapter;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firestore;
    ScrollView scrollView;
    LinearLayout linearLayout;
    List<Exclusive_model> ExclusiveList,Discountlist;
    List<Groceries_model> GrocieriesList;
    int loadStatus = 0;
    private static final String TAG = "HomeFragment";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        firestore = FirebaseFirestore.getInstance();
        scrollView = root.findViewById(R.id.scrollview11);
        scrollView.setVisibility(View.INVISIBLE);
        linearLayout = root.findViewById(R.id.linear);
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);


        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();


        List<SlideModel> slideModels= new ArrayList<>();
        SlideModel e1 = new SlideModel(R.drawable.pips, ScaleTypes.FIT);
        SlideModel e2 = new SlideModel(R.drawable.saloonbar,ScaleTypes.FIT);
        SlideModel e3 = new SlideModel(R.drawable.saloondoor,ScaleTypes.FIT);
        slideModels.add(e1);
        slideModels.add(e2);
        slideModels.add(e3);
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);


        Exclusive_recycler= root.findViewById(R.id.exclusiverecycler);
        groceries_recycler= root.findViewById(R.id.groceriesrecycler);
        bestsellerecycler= root.findViewById(R.id.bestsellerecycler);

        groceries_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        Exclusive_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        bestsellerecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        ExclusiveList = new ArrayList<>();
        GrocieriesList = new ArrayList<>();
        Discountlist = new ArrayList<>();

        groceries_adapter = new Groceries_adapter(getActivity(),GrocieriesList);
        exclusiveAdapter = new ExclusiveAdapter(getActivity(),ExclusiveList);
        getExclusiveAdapter = new ExclusiveAdapter(getActivity(),Discountlist);

        groceries_recycler.setAdapter(groceries_adapter);
        Exclusive_recycler.setAdapter(exclusiveAdapter);
        bestsellerecycler.setAdapter(getExclusiveAdapter);

        firestore.collection("Exclusive Offer")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Exclusive_model model = document.toObject(Exclusive_model.class);
                                ExclusiveList.add(model);
                                exclusiveAdapter.notifyDataSetChanged();
                            }
                            loadStatus +=1;
                        } else {

                        }
                    }
                });

        firestore.collection("BestOffer")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Groceries_model model = document.toObject(Groceries_model.class);
                                GrocieriesList.add(model);
                                groceries_adapter.notifyDataSetChanged();
                            }
                            loadStatus +=1;
                        } else {

                        }
                    }
                });

        firestore.collection("Discount")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Exclusive_model model = document.toObject(Exclusive_model.class);
                                Discountlist.add(model);
                                getExclusiveAdapter.notifyDataSetChanged();
                            }
                            loadStatus += 1;
                            Log.d(TAG,"Load Status 1 : "+loadStatus);

                                linearLayout.setVisibility(View.INVISIBLE);
                                scrollView.setVisibility(View.VISIBLE);
                                //Toast.makeText(getActivity(),"All Loaded",Toast.LENGTH_SHORT).show();

                        } else {

                        }
                    }
                });
        Log.d(TAG,"Load Status : "+loadStatus);

        return root;
    }


}
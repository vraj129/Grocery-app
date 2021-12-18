package com.example.grocery;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocery.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class PeopleFragment extends Fragment {



    TextView name,email;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    String uid;
    LinearLayout ll1,ll2;
    Button appoint,fav,logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup)  inflater.inflate(R.layout.fragment_people, container, false);
        name = root.findViewById(R.id.name);
        email = root.findViewById(R.id.email);
        ll1 = root.findViewById(R.id.linear1);
        ll2 = root.findViewById(R.id.linear2);
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid().toString();
        appoint = root.findViewById(R.id.appointment);
        logout = root.findViewById(R.id.logout);
        fav = root.findViewById(R.id.favourite);

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),FavouriteActivity.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent i = new Intent(getActivity(),LoginRegistration.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),CartActivity.class);
                startActivity(i);


            }
        });

        //Toast.makeText(getActivity(),"Id : "+uid,Toast.LENGTH_LONG).show();
        if(!uid.isEmpty()){
            getUserData();
        }
        return root;
    }

    private void getUserData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    ll1.setVisibility(View.GONE);
                    ll2.setVisibility(View.VISIBLE);
                    DataSnapshot snapshot = task.getResult();
                     String name1 = String.valueOf(snapshot.child("name").getValue());
                     name.setText(name1);
                    Log.d("People Fragment","Name : "+name1);
                    email.setText(String.valueOf(snapshot.child("email").getValue()));
                }
                else {
                    Log.d("People Fragment","Error Fetching Data");
                }
            }
        });

    }
}
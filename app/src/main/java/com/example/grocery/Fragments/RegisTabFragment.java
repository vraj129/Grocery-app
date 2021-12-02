package com.example.grocery.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.grocery.Model.UserModel;
import com.example.grocery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class RegisTabFragment extends Fragment {
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    EditText name,email,password;
    Button register;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       ViewGroup root = (ViewGroup) inflater.inflate(R.layout.registab_fragment,container,false);
       BindingViews(root);
       progressBar.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();

            }
        });

        return root;
    }

    private void createUser() {
        progressBar.setVisibility(View.VISIBLE);
        String user_name = name.getText().toString();
        String user_email = email.getText().toString();
        String user_pass = password.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(TextUtils.isEmpty(user_name))
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Name is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(user_email))
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Email is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            if(!user_email.trim().matches(emailPattern))
            {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Email is Invalid", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(TextUtils.isEmpty(user_pass))
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Password is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(user_pass.length() < 6)
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Password Lenght should be Greater Than 6", Toast.LENGTH_SHORT).show();
            return;
        }


        auth.createUserWithEmailAndPassword(user_email,user_pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            UserModel user = new UserModel(user_name,user_email,user_pass);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user);
                            progressBar.setVisibility(View.GONE);
                            name.setText("");
                            email.setText("");
                            password.setText("");
                            Toast.makeText(getActivity(), "Registration Complete", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Registration Unsuccessful"+task.getException(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }

    public void BindingViews(ViewGroup root)
    {
        name = root.findViewById(R.id.user_name);
        email =root.findViewById(R.id.user_mail);
        password = root.findViewById(R.id.user_pass);
        register = root.findViewById(R.id.regbtn);
        progressBar = root.findViewById(R.id.progressbar);
    }
}

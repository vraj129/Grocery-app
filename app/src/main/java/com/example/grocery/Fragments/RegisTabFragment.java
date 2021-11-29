package com.example.grocery.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.grocery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisTabFragment extends Fragment {
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    EditText name,email,password;
    Button register;
    FirebaseAuth auth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       ViewGroup root = (ViewGroup) inflater.inflate(R.layout.registab_fragment,container,false);
       BindingViews(root);
        auth = FirebaseAuth.getInstance();
        
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

        return root;
    }

    private void createUser() {
        String user_name = name.getText().toString();
        String user_email = email.getText().toString();
        String user_pass = password.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(TextUtils.isEmpty(user_name))
        {
            Toast.makeText(getActivity(), "Name is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(user_email))
        {
            Toast.makeText(getActivity(), "Email is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            if(!user_email.trim().matches(emailPattern))
            {
                Toast.makeText(getActivity(), "Email is Invalid", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(TextUtils.isEmpty(user_pass))
        {
            Toast.makeText(getActivity(), "Password is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if(user_pass.length() < 6)
        {
            Toast.makeText(getActivity(), "Password Lenght should be Greater Than 6", Toast.LENGTH_SHORT).show();
            return;
        }


        auth.createUserWithEmailAndPassword(user_email,user_pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getActivity(), "Registration Complete", Toast.LENGTH_SHORT).show();

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
    }
}

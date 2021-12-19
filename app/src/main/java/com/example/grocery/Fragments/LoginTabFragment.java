package com.example.grocery.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.grocery.MainActivity;
import com.example.grocery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginTabFragment extends Fragment {

    EditText email,password;
    Button logbtn;
    FirebaseAuth auth;
    FirebaseUser fuser;
    ProgressBar progressBar;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.logintab_fragment,container,false);
        BindView(root);
        progressBar.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();
        fuser = auth.getCurrentUser();
        if(auth.getCurrentUser() != null)
        {

            if(fuser.isEmailVerified()){
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }



        }
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();

            }
        });
        return root;
    }

    private void loginUser() {
        fuser = auth.getCurrentUser();
        progressBar.setVisibility(View.VISIBLE);
        String user_email = email.getText().toString();
        String user_pass = password.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
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
            Toast.makeText(getActivity(), "Password Length should be Greater Than 6", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(user_email,user_pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            progressBar.setVisibility(View.GONE);
                            email.setText("");
                            password.setText("");
                            fuser = auth.getCurrentUser();
                            if(fuser.isEmailVerified()){
                                Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getActivity(), MainActivity.class);
                                startActivity(i);
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(), "Please Verify Your Email Id", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Login Unsuccessful"+task.getException(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void BindView(ViewGroup root)
    {
        email = root.findViewById(R.id.user_mail);
        password = root.findViewById(R.id.user_pass);
        logbtn = root.findViewById(R.id.login);
        progressBar = root.findViewById(R.id.progressbar);
    }


}

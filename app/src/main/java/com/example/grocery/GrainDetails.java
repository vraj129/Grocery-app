package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.grocery.Mail.JavaMailApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class GrainDetails extends AppCompatActivity {
    int cYear,cMonth,cDay,sYear,sMonth,sDay;
    int cHour,cMinute;
    int selectedTime= 0;
    int totalprice=0;
    ImageView imageView;
    TextView textView,date,bill;
    Button addtocart,SelectDate,Favourite;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    Spinner Splace;
    String place_selected;
    String id;
    String docid;
    String name;
    String places;
    Boolean fav_clicked = false;
    CheckBox haircut,facial,hairwash;
    LinearLayout ll1;

    //--------
    String email11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_grain_details);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        ll1 = findViewById(R.id.progresslayout);
        imageView = findViewById(R.id.grain_image);
        textView = findViewById(R.id.grain_name);
        Splace = findViewById(R.id.Spinnerplaces);
        bill = findViewById(R.id.billvalue);
        date = findViewById(R.id.datetext);
        haircut = findViewById(R.id.haircutting);
        facial = findViewById(R.id.facial);
        hairwash = findViewById(R.id.hairwash);
        SelectDate = findViewById(R.id.datebtn);
        Favourite = findViewById(R.id.fav);
        name = getIntent().getStringExtra("name");
       // Toast.makeText(this,"Name : "+name,Toast.LENGTH_SHORT).show();
         id = getIntent().getStringExtra("image");
        places = getIntent().getStringExtra("place");
        String[] placearr =  places.split(","); //Places
        place_selected = placearr[0];
        getUserData();
       // Toast.makeText(GrainDetails.this,"Selected : "+place_selected,Toast.LENGTH_SHORT).show();
//        for (int i =0; i<placearr.length;i++)
//            Log.d("Grain Details","Data : "+placearr[i]);
       // Toast.makeText(this,"Places : "+places,Toast.LENGTH_LONG).show();
        textView.setText(name);
        Glide.with(GrainDetails.this).load(id).into(imageView);
        ll1.setVisibility(View.VISIBLE);
        addtocart = findViewById(R.id.addtocartbtn);

        ArrayAdapter<String> placeList = new ArrayAdapter<String>(GrainDetails.this, android.R.layout.simple_spinner_item,placearr);
        placeList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Splace.setAdapter(placeList);
        Splace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                place_selected = adapterView.getItemAtPosition(i).toString();
               // Toast.makeText(GrainDetails.this,"Selected : "+place_selected,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //quantity = Integer.parseInt(String.valueOf(quantity_no.getText()));

            Calendar calendar = Calendar.getInstance();
            cYear = calendar.get(Calendar.YEAR);
            cMonth = calendar.get(Calendar.MONTH);
            cDay = calendar.get(Calendar.DAY_OF_MONTH);

            cHour = calendar.get(Calendar.HOUR_OF_DAY);
            cMinute = calendar.get(Calendar.MINUTE);

            String date1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            String time1 = new SimpleDateFormat("hh:mm aa",Locale.getDefault()).format(new Date());

            SelectDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            GrainDetails.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            sYear = i;
                            sMonth = i1;
                            sDay = i2;
                            date.setText(i + "-" + (i1+1) + "-" + (i2));
                            selectedTime=1;
                        }
                    },cYear,cMonth,cDay
                    );
                    datePickerDialog.updateDate(sYear,sMonth,sDay);

                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                    datePickerDialog.show();
                }
            });
            haircut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        totalprice +=100;
                        bill.setText(String.valueOf(totalprice));
                    }
                    else {
                        totalprice -=100;
                        bill.setText(String.valueOf(totalprice));
                    }
                }
            });

        facial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    totalprice +=100;
                    bill.setText(String.valueOf(totalprice));
                }
                else {
                    totalprice -=100;
                    bill.setText(String.valueOf(totalprice));
                }
            }
        });
        hairwash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    totalprice +=100;
                    bill.setText(String.valueOf(totalprice));
                }
                else {
                    totalprice -=100;
                    bill.setText(String.valueOf(totalprice));
                }
            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalprice == 0 || selectedTime == 0 || email11.isEmpty()){
                    Toast.makeText(GrainDetails.this,"Please Fill All Your Details",Toast.LENGTH_LONG).show();
                }
                else {
                    //Toast.makeText(GrainDetails.this,"Correctly Filled",Toast.LENGTH_LONG).show();
                    addAppointment();
                }
            }
        });
        docid = auth.getCurrentUser().getUid()+name;
        DocumentReference documentReference = firestore.collection("currentUser").document(auth.getCurrentUser().getUid())
                .collection("Favourites").document(docid);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ll1.setVisibility(View.GONE);
                if(task.isSuccessful()){

                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){
                        Log.d("Grain","Exists");
                        fav_clicked = true;
                        Favourite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_favorite_24,0,0,0);

                    }
                    //Toast.makeText(GrainDetails.this,"Exsists",Toast.LENGTH_SHORT);

                }
            }
        });
        Favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!fav_clicked){
                    fav_clicked = true;
                    ll1.setVisibility(View.VISIBLE);
                    Favourite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_favorite_24,0,0,0);
                    addFavourite();
                } else if(fav_clicked){
                    fav_clicked = false;
                    ll1.setVisibility(View.VISIBLE);
                    Favourite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_favorite_24_1,0,0,0);
                    removeFavourite();
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
                    Log.d("Grain","Email : "+email11);
                }
                else {
                    Log.d("Grain","Error Fetching Data");
                }
            }
        });

    }

    private void removeFavourite() {
        docid = auth.getCurrentUser().getUid()+name;
       // Toast.makeText(GrainDetails.this,"Doc Id: "+ docid,Toast.LENGTH_SHORT).show();

        firestore.collection("currentUser").document(auth.getCurrentUser().getUid())
                .collection("Favourites").document(docid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ll1.setVisibility(View.GONE);
                //Toast.makeText(GrainDetails.this,"Deleted",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addFavourite() {
        final HashMap<String,Object> favMap = new HashMap<>();
        favMap.put("image",id);
        favMap.put("name",textView.getText().toString());
        favMap.put("place",places);
        //favMap.put("documentId",auth.getCurrentUser().getUid()+name);
        String documentid = auth.getCurrentUser().getUid()+name.trim();
        firestore.collection("currentUser").document(auth.getCurrentUser().getUid())
                .collection("Favourites").document(documentid).set(favMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ll1.setVisibility(View.GONE);
                //Toast.makeText(GrainDetails.this,"Added",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addAppointment() {
        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("StoreName",textView.getText().toString());
        cartMap.put("TotalPrice",String.valueOf(totalprice));
        cartMap.put("AppointmentDate",date.getText().toString());
        cartMap.put("Location",place_selected);

        String subject = "Saloon Appointment";
        String Emailmsg = "Your Appointment For "+textView.getText().toString()+" is on "+date.getText().toString()+"\n at "+place_selected+" and your\n Total Bill is : ₹"+String.valueOf(totalprice);
        JavaMailApi javaMailAPI = new JavaMailApi(GrainDetails.this,email11,subject,Emailmsg);

        javaMailAPI.execute();
        firestore.collection("currentUser").document(auth.getCurrentUser().getUid())
                .collection("Appointment").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(GrainDetails.this,"Appointment Made !!!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
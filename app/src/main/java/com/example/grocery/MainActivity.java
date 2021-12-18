package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView exclusive_recycler,groceries_recycler,bestsellerecycler;
    RecyclerView.Adapter exclusive_adapter,groceries_adapter;
    ImageView cart,logout;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottomnavigation);
        bottomNavigation.setSelectedItemId(R.id.nav_home);
        //Fragment fragment1 = new HomeFragment();
        loadFragment(new HomeFragment());
        bottomNavigation.setOnNavigationItemSelectedListener(navListner);


//        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_search_24));
//        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_home_24));
//        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_people_alt_24));
//
//        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
//            @Override
//            public void onShowItem(MeowBottomNavigation.Model item) {
//                Fragment fragment = null;
//
//                switch (item.getId()){
//                    case 1:
//                        fragment = new SearchFragment();
//                        break;
//                    case 2:
//                        fragment = new HomeFragment();
//                        break;
//                    case 3:
//                        fragment = new PeopleFragment();
//                        break;
//                }
//                loadFragment(fragment);
//            }
//        });
//
////        KeyboardVisibilityEvent.setEventListener(
////                this,
////                new KeyboardVisibilityEventListener() {
////                    @Override
////                    public void onVisibilityChanged(boolean isOpen) {
////                        //Log.d(TAG,"onVisibilityChanged: Keyboard visibility changed");
////                        if(isOpen){
////                           // Log.d(TAG, "onVisibilityChanged: Keyboard is open");
////                            bottomNavigation.setVisibility(View.INVISIBLE);
////                            //Log.d(TAG, "onVisibilityChanged: NavBar got Invisible");
////                        }else{
////                            //Log.d(TAG, "onVisibilityChanged: Keyboard is closed");
////                            bottomNavigation.setVisibility(View.VISIBLE);
////                            //Log.d(TAG, "onVisibilityChanged: NavBar got Visible");
////                        }
////                    }
////                });
//        bottomNavigation.show(2,true);
//        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
//            @Override
//            public void onClickItem(MeowBottomNavigation.Model item) {
//               // Toast.makeText(MainActivity.this,"You Clicked : "+item.getId(),Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
//            @Override
//            public void onReselectItem(MeowBottomNavigation.Model item) {
//               // Toast.makeText(getApplicationContext(),"You reselected : "+item.getId(),Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.nav_search:
                        fragment = new SearchFragment();
                        break;
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.nav_profile:
                        fragment = new PeopleFragment();
                        break;
                }
                loadFragment(fragment);
                    return true;
                }
            };

    private void loadFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout,fragment)
                .commit();
    }
}
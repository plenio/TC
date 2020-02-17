package com.example.transcamb;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.transcamb.costantes.Constants;
import com.example.transcamb.utils.SharedPref;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;



    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tablayout_id);
//        appBarLayout = findViewById(R.id.appbarid);
        viewPager = findViewById(R.id.viewpager_id);
        FloatingActionButton fb_login = findViewById(R.id.fb_login);

        mAuth = FirebaseAuth.getInstance();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();

        SharedPref pref = new SharedPref(getBaseContext());
        String statusPref = pref.readPreference();

        bundle.putString("fragment", "passageiro");

        Bundle bundle1 = new Bundle();
        bundle1.putString("fragment", "passageiro");

        Passageiro passageiro = new Passageiro();
        passageiro.setArguments(bundle);

        Transportador transportador = new Transportador();
        transportador.setArguments(bundle1);

        if (statusPref != null && statusPref.equals(Constants.PASSAGEIRO)){
            adapter.addFrag(passageiro, "passageiro");
        }else if (statusPref != null && statusPref.equals(Constants.TRANSPORTADOR)){
            adapter.addFrag(transportador, "transportador");
        }else {
            adapter.addFrag(passageiro, "passageiro");
            adapter.addFrag(transportador, "transportador");
        }

        if (mAuth.getCurrentUser() != null){
            fb_login.setImageResource(R.drawable.ic_car);
        }


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        fb_login.setOnClickListener(view -> {
            if (tabLayout.getSelectedTabPosition() == 0) {
                Intent intent = new Intent(MainActivity.this, LogIn.class);
                intent.putExtra("status", "pass");
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, LogIn.class);
                intent.putExtra("status", "trans");
                startActivity(intent);
            }
        });
    }
}

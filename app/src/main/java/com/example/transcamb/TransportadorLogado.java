package com.example.transcamb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.transcamb.utils.SharedPref;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class TransportadorLogado extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportador_logado);

        Toolbar toolbar = findViewById(R.id.toolbarP);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer_layoutT);
        NavigationView navigationView = findViewById(R.id.nav_vew);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.menumT:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_homet()).commit();
                break;
            case R.id.meuperfilT:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new fragment_perfil()).commit();
                Intent ne = new Intent(TransportadorLogado.this,perfilActivity.class);
                startActivity(ne);
                break;
            case R.id.mypostT:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_meupost()).commit();
                break;
//            case R.id.convidar:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new fragment_convidar()).commit();
//                break;
            case R.id.fimT:
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                SharedPref pref = new SharedPref(getBaseContext());
                pref.clearPreference();

                startActivity(new Intent(TransportadorLogado.this, MainActivity.class));
                finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}

package com.example.transcamb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.transcamb.utils.SharedPref;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class PassageiroLogado extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton floatingActionButto;
    TextView titulo, fimpag;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passageiro_logado);

        Toolbar toolbar = findViewById(R.id.toolbarP);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawer_layoutP);

        NavigationView navigationView = findViewById(R.id.nav_vew);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new fragment_home()).commit();
            navigationView.setCheckedItem(R.id.menumT);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

//            case R.id.menum:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new fragment_home()).commit();
//                break;
            case R.id.meuperfil:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new fragment_perfil()).commit();
                Intent ne = new Intent(PassageiroLogado.this,perfilActivity.class);
                startActivity(ne);
                break;
            case R.id.mypost:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_meupost()).commit();
                break;
//            case R.id.convidar:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new fragment_convidar()).commit();
//                break;
            case R.id.fim:
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                SharedPref pref = new SharedPref(getBaseContext());
                pref.clearPreference();

                startActivity(new Intent(PassageiroLogado.this, MainActivity.class));
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

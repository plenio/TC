package com.example.transcamb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PassageiroLogado extends AppCompatActivity {

    private FloatingActionButton floatingActionButto;
    TextView titulo, fimpag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passageiro_logado);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//       titulo = findViewById(R.id.titulo);
//       fimpag = findViewById(R.id.endpag);
//
//       //import font
//        //Typeface MLight = Typeface.createFromAsset(getAssets(),"fonts/ML.ttf");
//        Typeface MMedium= Typeface.createFromAsset(getAssets(),"fonts/MM.ttf");
//
//        //cusomi font
//        titulo.setTypeface(MMedium);
//        //fimpag.setTypeface(MLight);

        floatingActionButto = findViewById(R.id.solic);
        floatingActionButto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bt = new Intent(PassageiroLogado.this,SolicitarTransporte.class);
                startActivity(bt);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.perfil_id:
                //////
                return true;
            case R.id.meupst_id:
                //////
                return true;
            case R.id.logOut_id:
                //////
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }


    }
}

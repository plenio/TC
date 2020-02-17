package com.example.transcamb;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SolicitarTransporte extends AppCompatActivity implements View.OnClickListener {
    private static final String[] DESTINO = new String[]{
            "Praça Morrumbene","Dumbanengue","Entrocamento Cambine","Madzandzene","90","28","Screp","Rocha","Entrocamento Mucoduene","Bairro Novo","Internato Femenino","Galango","Praça Cambine"
    };
    private TextView ehora;
    private EditText eDestino,eLocalizacao,eNumPassag;
    private MaterialButton solicit;
    private int horas,minuto;
    FirebaseDatabase database;
    DatabaseReference reference;
    private FirebaseUser user;
    private ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.transcamb.R.layout.activity_solicitar_transporte);

        AutoCompleteTextView editText = findViewById(com.example.transcamb.R.id.DestinoPassag);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, DESTINO);
        editText.setAdapter(adapter);
        AutoCompleteTextView editTex = findViewById(com.example.transcamb.R.id.localizacaoPass);
        ArrayAdapter<String>adapte = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, DESTINO);
        editTex.setAdapter(adapte);

        eDestino=findViewById(com.example.transcamb.R.id.DestinoPassag);
        eLocalizacao=findViewById(com.example.transcamb.R.id.localizacaoPass);
        eNumPassag =findViewById(com.example.transcamb.R.id.numP);
        solicit = findViewById(com.example.transcamb.R.id.solicitar);
        ehora = findViewById(com.example.transcamb.R.id.horaPartida);
        ehora.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBarsolicit);


        FirebaseAuth mauth =FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference=database.getReference("Solicitacao");


        solicit.setOnClickListener(v -> {
            addSolicitacao();
            solicit.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        horas = c.get(Calendar.HOUR_OF_DAY);
        minuto = c.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                ehora.setText(hourOfDay + ":" + minute);
            }
        },horas,minuto,false);
        timePicker.show();

    }
    private void addSolicitacao(){

        String destino =eDestino.getText().toString().trim();
        String numPassa =eNumPassag.getText().toString().trim();
        String hora =ehora.getText().toString().trim();
        String localizacao =eLocalizacao.getText().toString().trim();
        String nome = user.getDisplayName();

        if(!TextUtils.isEmpty(destino+numPassa+hora+localizacao)){

            reference.push();
            String id=user.getUid();
            SolicitarDados dados = new SolicitarDados(destino,numPassa,id,hora,localizacao,nome);
            reference.child(id).setValue(dados);

            Toast.makeText(this,"Solicitao enviada com sucesso ",Toast.LENGTH_LONG).show();
            Intent it = new Intent(SolicitarTransporte.this, fragment_home.class);
            startActivity(it);
            finish();

        }else{
            progressBar.setVisibility(View.INVISIBLE);
            solicit.setVisibility(View.VISIBLE);
            Toast.makeText(this,"preenche tods campos",Toast.LENGTH_LONG).show();
        }
    }



}

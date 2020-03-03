package com.example.transcamb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class DisponibilidadeTraspo extends AppCompatActivity implements View.OnClickListener{

    private static final String[] DESTINO = new String[]{
            "Praça Morrumbene","Dumbanengue","Entrocamento Cambine","Madzandzene","90","28","Screp","Rocha","Entrocamento Mucoduene","Bairro Novo","Internato Femenino","Galango","Praça Cambine"
    };
    private TextView Hora;
    private EditText Destino,Localizacao;
    private MaterialButton desponibili;
    private int horas,minuto;
    FirebaseDatabase database;
    DatabaseReference reference;
    private FirebaseUser user;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disponibilidade_traspo);

        AutoCompleteTextView editText = findViewById(com.example.transcamb.R.id.destinoTrans);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, DESTINO);
        editText.setAdapter(adapter);
        AutoCompleteTextView editTex = findViewById(R.id.localizacaoTrans);
        ArrayAdapter<String>adapte = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, DESTINO);
        editTex.setAdapter(adapte);

        Destino=findViewById(R.id.destinoTrans);
        Localizacao=findViewById(R.id.localizacaoTrans);
        desponibili = findViewById(R.id.desponibili);
        Hora = findViewById(R.id.horaPartidaTrans);
        Hora.setOnClickListener(this);
        progressBar = findViewById(R.id.DispoprogressBar);


        FirebaseAuth mauth =FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference=database.getReference("Disponibilidade");


        desponibili.setOnClickListener(v -> {
            addDisponibilidade();
            desponibili.setVisibility(View.INVISIBLE);
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
                Hora.setText(hourOfDay + ":" + minute);
            }
        },horas,minuto,false);
        timePicker.show();

    }
    private void addDisponibilidade(){

        String destino =Destino.getText().toString().trim();
        String hora =Hora.getText().toString().trim();
        String localizacao =Localizacao.getText().toString().trim();
        String nome = user.getDisplayName();

        if(!TextUtils.isEmpty(destino+hora+localizacao)){

            reference.push();
            String id=user.getUid();
            DisponibilidadeDados dados = new DisponibilidadeDados(destino,id,hora,localizacao,nome);
            reference.child(id).setValue(dados);

            Toast.makeText(this,"Disponibilidade enviada com sucesso ",Toast.LENGTH_LONG).show();
            Intent intentT = new Intent(DisponibilidadeTraspo.this, fragment_homet.class);
            startActivity(intentT);
            finish();

        }else{
            progressBar.setVisibility(View.INVISIBLE);
            desponibili.setVisibility(View.VISIBLE);
            Toast.makeText(this,"preenche tods campos",Toast.LENGTH_LONG).show();
        }
    }

}
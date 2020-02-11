package com.example.transcamb;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.R;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.function.Consumer;

public class SolicitarTransporte extends AppCompatActivity implements View.OnClickListener {
    private static final String[] DESTINO = new String[]{
            "Praça Morrumbene","Dumbanengue","Entrocamento Cambine","Madzandzene","90","28","Screp","Rocha","Entrocamento Mucoduene","Bairro Novo","Internato Femenino","Galango","Praça Cambine"
    };
    private TextView ehora;
    private EditText eDestino,eLocalizacao,eNumPassag;
    private Button solicit;
    private int horas,minuto;
    FirebaseDatabase database;
    DatabaseReference reference;
    private FirebaseUser user;
    private String nome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_transporte);

        AutoCompleteTextView editText = findViewById(R.id.DestinoPassag);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, DESTINO);
        editText.setAdapter(adapter);
        AutoCompleteTextView editTex = findViewById(R.id.localizacaoPass);
        ArrayAdapter<String>adapte = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, DESTINO);
        editTex.setAdapter(adapte);

        eDestino=findViewById(R.id.DestinoPassag);
        eLocalizacao=findViewById(R.id.localizacaoPass);
        eNumPassag =findViewById(R.id.numP);
        solicit = findViewById(R.id.solicitar);
        ehora = findViewById(R.id.horaPartida);
        ehora.setOnClickListener(this);

//        FirebaseAuth mauth =FirebaseAuth.getInstance();
//        user=mauth.getCurrentUser();
//        database = FirebaseDatabase.getInstance();
//        reference=database.getReference("Solicitacao");
//
//
//        getNome(new NomeListner(){
//            @Override
//            public String nomeUser(String nome) {
////                addSolicitacao(nome);
//
//                Toast.makeText(getBaseContext(), nome, Toast.LENGTH_SHORT).show();
//                return nome;
//            }
//        }, database);
//
//
//        solicit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getNome(new NomeListner() {
//                    @Override
//                    public String nomeUser(String nome) {
//                        addSolicitacao(nome);
//
//                        Toast.makeText(getBaseContext(), nome, Toast.LENGTH_SHORT).show();
//                        return nome;
//                    }
//                }, database);
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v) {
//        final Calendar c = Calendar.getInstance();
//        horas = c.get(Calendar.HOUR_OF_DAY);
//        minuto = c.get(Calendar.MINUTE);
//
//        TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                ehora.setText(hourOfDay + ":" + minute);
//            }
//        },horas,minuto,false);
//        timePicker.show();
//
//    }
//    private void addSolicitacao(String nome){
//        String destino =eDestino.getText().toString().trim();
//        String numPassa =eNumPassag.getText().toString().trim();
//        String hora =ehora.getText().toString().trim();
//        String localizacao =eLocalizacao.getText().toString().trim();
//
//        if(!TextUtils.isEmpty(destino+numPassa+hora+localizacao)){
//
//          reference.push();
//          String id=user.getUid();
//          SolicitarDados dados = new SolicitarDados(destino,numPassa,id,hora,localizacao,nome);
//          reference.child(id).setValue(dados);
//            Toast.makeText(this,"Certo ",Toast.LENGTH_LONG).show();
//
//        }else{
//            Toast.makeText(this,"preenche tods campos",Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private void getNome(final NomeListner nomeListner, FirebaseDatabase reference){
//        DatabaseReference databaseReferenceUser = reference.getReference("User").child("Passgeiro");
//
//        databaseReferenceUser.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                nome = String.valueOf(dataSnapshot.child("nome"));
//                nomeListner.nomeUser(nome);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

        FirebaseAuth mauth =FirebaseAuth.getInstance();
        user=mauth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference=database.getReference("Solicitacao");


        solicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSolicitacao();
            }
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
            Toast.makeText(this,"Certo ",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this,"preenche tods campos",Toast.LENGTH_LONG).show();
        }
    }



}

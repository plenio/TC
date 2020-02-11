package com.example.transcamb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CadastroTransportador extends AppCompatActivity {

    private EditText Name, Email, Password, confPassword, vMatricula, vMarca;
    private Button btCadastra;

    private ProgressBar loading;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_transportador);

        Name = findViewById(R.id.Nome);
        vMatricula = findViewById(R.id.Matricula);
        vMarca = findViewById(R.id.Marca);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Senha);
        confPassword = findViewById(R.id.edtConfSenha);
        btCadastra = findViewById(R.id.btnRegisT);
        loading = findViewById(R.id.progressBar);
        loading.setVisibility(View.INVISIBLE);

        //firebase
        databaseReference=FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(CadastroTransportador.this, TransportadorLogado.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };
        btCadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btCadastra.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.VISIBLE);
                final String email = Email.getText().toString();
                final String password = Password.getText().toString();
                final String nome = Name.getText().toString();
                final String marca = vMarca.getText().toString();
                final String matricula = vMatricula.getText().toString();

                if (!email.isEmpty() && !nome.isEmpty() && !marca.isEmpty()&& !matricula.isEmpty()&& !password.isEmpty()) {
                    if(password.length()>=6) {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CadastroTransportador.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    btCadastra.setVisibility(View.VISIBLE);
                                    loading.setVisibility(View.INVISIBLE);
                                    Toast.makeText(CadastroTransportador.this, "Erro no cadastro", Toast.LENGTH_SHORT).show();

                                } else {
                                    Map<String,Object>map = new HashMap<>();
                                    map.put("nome",nome);
                                    map.put("marca",marca);
                                    map.put("matricula",matricula);
                                    String user_id = mAuth.getCurrentUser().getUid();
                                    //DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("User").child("Passageiro").child(user_id);
                                    //current_user_db.setValue(true);

                                    databaseReference.child("User").child("Transportador").child(user_id).setValue(map);
                                }
                            }
                        });
                    }else {
                        btCadastra.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.INVISIBLE);
                        Toast.makeText(CadastroTransportador.this, "Password no minimo 6 caracteres.", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(CadastroTransportador.this, "Preenche todos campos.", Toast.LENGTH_SHORT).show();
                    btCadastra.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.INVISIBLE);
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }
}


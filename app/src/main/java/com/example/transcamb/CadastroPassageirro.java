package com.example.transcamb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.transcamb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CadastroPassageirro extends AppCompatActivity {

    private EditText userName, userEmail, userPassword, confPassword;
    private Button btCadastrar;
    private ProgressBar loading;
    private TextView beckLogPss;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_passageiro);

        beckLogPss = findViewById(R.id.beckLogPss);
//        userName = findViewById(R.id.edtNome);
//        userEmail = findViewById(R.id.edtEmail);
//        userPassword = findViewById(R.id.edtSenha);
//        confPassword = findViewById(R.id.edtConfSenha);
//        btCadastrar = findViewById(R.id.btnRegistP);
//        loading = findViewById(R.id.progressBar);
//        loading.setVisibility(View.INVISIBLE);
//
//        //bt
        beckLogPss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ne = new Intent(CadastroPassageirro.this,LogInPassageirro.class);
                startActivity(ne);
            }
        });


        userName = findViewById(R.id.edtNome);
        userEmail = findViewById(R.id.edtEmail);
        userPassword = findViewById(R.id.edtSenha);
        confPassword = findViewById(R.id.edtConfSenha);
        btCadastrar = findViewById(R.id.btnRegistP);
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
                    updateUI();
                }
            }
        };

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btCadastrar.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.VISIBLE);
                final String email = userEmail.getText().toString();
                final String password = userPassword.getText().toString();
                final String nome = userName.getText().toString();

                if(email.isEmpty() ||password.isEmpty() || nome.isEmpty()){
                    btCadastrar.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.INVISIBLE);
                    Toast.makeText(CadastroPassageirro.this,"Preeche todos campos",Toast.LENGTH_LONG).show();
                }else{
                    CreateUser(email,password,nome);
                }
            }
        });

    }

    private void CreateUser(String email, String password, final String nome) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(CadastroPassageirro.this,"Conta criada",Toast.LENGTH_LONG).show();
                        updateUserinfo(nome, mAuth.getCurrentUser());
                    }
                });

    }

    private void updateUserinfo(String nome, FirebaseUser currentUser) {

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(nome).build();

        currentUser.updateProfile(request)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            updateUI();
                        }
                    }
                });
    }

    private void updateUI() {
        Intent it = new Intent(CadastroPassageirro.this, PassageiroLogado.class);
        startActivity(it);
        finish();
    }

}

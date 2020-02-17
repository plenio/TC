package com.example.transcamb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.transcamb.costantes.Constants;
import com.example.transcamb.utils.SharedPref;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//import com.google.firebase.FirebaseApp;
//import com.google.firebase.auth.FirebaseAuth;

public class CadastroPassageirro extends AppCompatActivity {

    private EditText userName, userEmail, userPassword, confPassword;
    private MaterialButton btCadastrar;
    private ProgressBar loading;
    private TextView beckLogPss, tv_back_to_login_trans;

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
        beckLogPss.setOnClickListener(v -> {
            Intent ne = new Intent(CadastroPassageirro.this, LogIn.class);
            startActivity(ne);
        });


        userName = findViewById(R.id.edtNome);
        userEmail = findViewById(R.id.edtEmail);
        userPassword = findViewById(R.id.edtSenha);
        confPassword = findViewById(R.id.edtConfSenha);
        btCadastrar = findViewById(R.id.btnRegistP);
        loading = findViewById(R.id.progressBar);
        loading.setVisibility(View.INVISIBLE);

        //firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                updateUI();
            }
        };

        btCadastrar.setOnClickListener(v -> {
            btCadastrar.setVisibility(View.INVISIBLE);
            loading.setVisibility(View.VISIBLE);
            final String email = userEmail.getText().toString();
            final String password = userPassword.getText().toString();
            final String nome = userName.getText().toString();

            if (email.isEmpty() || password.isEmpty() || nome.isEmpty()) {
                btCadastrar.setVisibility(View.VISIBLE);
                loading.setVisibility(View.INVISIBLE);
                Toast.makeText(CadastroPassageirro.this, "Preeche todos campos", Toast.LENGTH_LONG).show();
            } else {
                CreateUser(email, password, nome);
            }
        });

    }

    private void CreateUser(String email, String password, final String nome) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    Toast.makeText(CadastroPassageirro.this, "Conta criada", Toast.LENGTH_LONG).show();
                    updateUserInfo(nome, Objects.requireNonNull(mAuth.getCurrentUser()));
                });

    }

    private void updateUserInfo(String nome, FirebaseUser currentUser) {

        Map<String, Object> map = new HashMap<>();
        map.put("nome", nome);
        map.put("status", "passageiro");

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(nome).build();

        currentUser.updateProfile(request)
                .addOnCompleteListener(task -> databaseReference.child(Constants.USER_CHILD)
                        .child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).setValue(map)
                        .addOnCompleteListener(finalTask -> {
                            if (finalTask.isSuccessful()) {
                                updateUI();
                            }
                        }));
    }

    private void updateUI() {
        SharedPref sharedPref = new SharedPref(getBaseContext());
        sharedPref.savePreference("passageiro");
        Intent it = new Intent(CadastroPassageirro.this, PassageiroLogado.class);
        startActivity(it);
        finish();
    }



}

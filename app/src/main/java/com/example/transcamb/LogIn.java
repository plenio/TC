package com.example.transcamb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.transcamb.costantes.Constants;
import com.example.transcamb.utils.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class LogIn extends AppCompatActivity {

    private EditText nEmail, password;
    private MaterialButton btLogin;
    private ProgressBar loginProgress;
    private TextView CadastroPass, recuperarSenhaPass, beckhome;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginProgress = findViewById(R.id.loginprogressBar);
        loginProgress.setVisibility(View.INVISIBLE);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


        nEmail = findViewById(R.id.nemail);
        password = findViewById(R.id.nsenha);
        btLogin = findViewById(R.id.btloginp);
        CadastroPass = findViewById(R.id.registe_se);
        beckhome = findViewById(R.id.backHome);


        String status = getIntent().getStringExtra("status");


        //bt
        beckhome.setOnClickListener(v -> {
            Intent e = new Intent(LogIn.this, MainActivity.class);
            startActivity(e);
        });

        CadastroPass.setOnClickListener(v -> {
            if (status.equals("pass")) {
                Intent ne = new Intent(LogIn.this, CadastroPassageirro.class);
                startActivity(ne);
            } else if (status.equals("trans")) {
                Intent ne = new Intent(LogIn.this, CadastroTransportador.class);
                startActivity(ne);
            }
        });


        //fireb
        mAuth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Intent intent1 = new Intent(LogIn.this, PassageiroLogado.class);
                startActivity(intent1);
                finish();
                return;
            }
        };

        btLogin.setOnClickListener(v -> {
            btLogin.setVisibility(View.INVISIBLE);
            loginProgress.setVisibility(View.VISIBLE);
            final String email = nEmail.getText().toString();
            final String senha = password.getText().toString();

            if (!email.isEmpty() && !senha.isEmpty()) {
                if (password.length() >= 6) {


                    mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                btLogin.setVisibility(View.VISIBLE);
                                loginProgress.setVisibility(View.INVISIBLE);
                                Toast.makeText(LogIn.this, "Falha ao iniciar de ceccao", Toast.LENGTH_SHORT).show();
                            }

                            databaseReference.
                                    child(Constants.USER_CHILD)
                                    .child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.exists()){
                                        String status = String.valueOf(dataSnapshot.child("status").getValue());

                                        Log.i("Infoooo", status);
                                        SharedPref sharedPref = new SharedPref(getBaseContext());
                                        sharedPref.savePreference(status);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    });
                } else {
                    btLogin.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                    Toast.makeText(LogIn.this, "Verifica a sua senha", Toast.LENGTH_SHORT).show();

                }
            } else {
                btLogin.setVisibility(View.VISIBLE);
                loginProgress.setVisibility(View.INVISIBLE);
                Toast.makeText(LogIn.this, "Preenche todos campos", Toast.LENGTH_SHORT).show();
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


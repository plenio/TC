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
import com.google.firebase.database.FirebaseDatabase;


public class LogInPassageirro extends AppCompatActivity {

    private EditText nEmail, password;
    private Button btLogin;
    private ProgressBar loginProgress;
    private TextView CadastroPass, recuperarSenhaPass, beckhome;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_passageiro);

        loginProgress = findViewById(R.id.loginprogressBar);
        loginProgress.setVisibility(View.INVISIBLE);

        nEmail=findViewById(R.id.nemail);
        password= findViewById(R.id.nsenha);
        btLogin =findViewById(R.id.btloginp);
        CadastroPass = findViewById(R.id.registe_se);
        beckhome = findViewById(R.id.beckhome);

        //bt
        beckhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(LogInPassageirro.this,MainActivity.class);
                startActivity(e);
            }
        });

        CadastroPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ne = new Intent(LogInPassageirro.this,CadastroPassageirro.class);
                startActivity(ne);
            }
        });


        //fireb
        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(LogInPassageirro.this, PassageiroLogado.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btLogin.setVisibility(View.INVISIBLE);
                loginProgress.setVisibility(View.VISIBLE);
                final String email = nEmail.getText().toString();
                final String senha = password.getText().toString();

                if (!email.isEmpty() && !senha.isEmpty()) {
                    if (password.length() >= 6) {


                    mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(LogInPassageirro.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                btLogin.setVisibility(View.VISIBLE);
                                loginProgress.setVisibility(View.INVISIBLE);
                                Toast.makeText(LogInPassageirro.this, "Falha ao iniciar de ceccao", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                        btLogin.setVisibility(View.VISIBLE);
                        loginProgress.setVisibility(View.INVISIBLE);
                        Toast.makeText(LogInPassageirro.this, "Verifica a sua senha", Toast.LENGTH_SHORT).show();

                    }
            }else {
                    btLogin.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                    Toast.makeText(LogInPassageirro.this, "Preenche todos campos", Toast.LENGTH_SHORT).show();
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


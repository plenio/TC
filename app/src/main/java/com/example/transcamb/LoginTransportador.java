package com.example.transcamb;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginTransportador extends AppCompatActivity {

    private EditText nEmail, password;
    private Button btLogin;
    private ProgressBar loginProgress;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private TextView CadstroTransp, recuperarsenhTransp,beckhome, te;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_transportador);

        loginProgress = findViewById(R.id.loginprogressBarT);
        loginProgress.setVisibility(View.INVISIBLE);

        nEmail=findViewById(R.id.nemailT);
        password= findViewById(R.id.nsenhaT);
        btLogin =findViewById(R.id.btloginpT);
        beckhome = findViewById(R.id.beck);
        te = findViewById(R.id.te);
        recuperarsenhTransp = findViewById(R.id.recuper);

        CadstroTransp = findViewById(R.id.textregisteT_se);


        beckhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nele = new Intent(LoginTransportador.this, MainActivity.class);
                startActivity(nele);
            }
        });
        CadstroTransp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nel = new Intent(LoginTransportador.this, CadastroTransportador.class);
                startActivity(nel);
            }
        });

        //fireb
        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(LoginTransportador.this, TransportadorLogado.class);
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
                te.setVisibility(View.INVISIBLE);
                recuperarsenhTransp.setVisibility(View.INVISIBLE);
                CadstroTransp.setVisibility(View.INVISIBLE);
                loginProgress.setVisibility(View.VISIBLE);
                final String email = nEmail.getText().toString();
                final String senha = password.getText().toString();

                if (!email.isEmpty() && !senha.isEmpty()) {
                    if (password.length() >= 6) {


                        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(LoginTransportador.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    btLogin.setVisibility(View.VISIBLE);
                                    loginProgress.setVisibility(View.INVISIBLE);
                                    te.setVisibility(View.VISIBLE);
                                    recuperarsenhTransp.setVisibility(View.VISIBLE);
                                    CadstroTransp.setVisibility(View.VISIBLE);
                                    Toast.makeText(LoginTransportador.this, "Falha ao iniciar de ceccao", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        btLogin.setVisibility(View.VISIBLE);
                        loginProgress.setVisibility(View.INVISIBLE);
                        te.setVisibility(View.VISIBLE);
                        recuperarsenhTransp.setVisibility(View.VISIBLE);
                        CadstroTransp.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginTransportador.this, "Verifica a sua senha", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    btLogin.setVisibility(View.VISIBLE);
                    te.setVisibility(View.VISIBLE);
                    recuperarsenhTransp.setVisibility(View.VISIBLE);
                    CadstroTransp.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginTransportador.this, "Preenche todos campos", Toast.LENGTH_SHORT).show();
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

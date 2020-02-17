package com.example.transcamb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class perfilActivity extends AppCompatActivity {


    private TextView email, nome;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        nome = findViewById(R.id.nomeu);
        email = findViewById(R.id.emailu);
        nome.setText(Objects.requireNonNull(user).getDisplayName());
        email.setText(user.getEmail());



    }
}

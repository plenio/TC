package com.example.transcamb;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import androidx.fragment.app.Fragment;

public class Passageiro extends Fragment {

    View view;
    FloatingActionButton fab;
    public Passageiro() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.passageirofragment, container, false);

        fab = (FloatingActionButton) view.findViewById(R.id.logc);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LogInPassageirro.class));
            }
        });

        return view;
    }


}

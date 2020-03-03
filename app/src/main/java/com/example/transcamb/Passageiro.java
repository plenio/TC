package com.example.transcamb;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Passageiro extends Fragment {

    View view;
    FloatingActionButton fab;
    public Passageiro() {
    }

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView rview;
    private List<DisponibilidadeDados> mdado;
    private AdapterTransportador adapterr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.passageirofragment, container, false);

        rview = view.findViewById(R.id.RView);
        rview.setHasFixedSize(true);
        rview.setLayoutManager(new LinearLayoutManager(getActivity()));
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Disponibilidade");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mdado = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    DisponibilidadeDados dado = snapshot.getValue(DisponibilidadeDados.class);
                    mdado.add(dado);
                }
                adapterr = new AdapterTransportador(getActivity(),mdado);
                rview.setAdapter(adapterr);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}

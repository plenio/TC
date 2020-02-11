package com.example.transcamb;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class Transportador extends Fragment {


    FloatingActionButton fab;
    View view;
    public Transportador() {
    }

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView rv;
    private List<SolicitarDados>mdados;
    private AdapterPassageiro adapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.transportadorfragment, container, false);


        fab = (FloatingActionButton) view.findViewById(R.id.log);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginTransportador.class));
            }
        });

        rv = view.findViewById(R.id.RV);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Solicitacao");



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mdados = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                SolicitarDados dados = snapshot.getValue(SolicitarDados.class);
                mdados.add(dados);
                }
                adapter = new AdapterPassageiro(getActivity(),mdados);
                rv.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class fragment_homet extends Fragment {
    private FloatingActionButton btf;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView rvieww;
    private List<SolicitarDados> mdado;
    private AdapterPassageiro adapterr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homet, container,false);

        rvieww = view.findViewById(R.id.RVieww);
        rvieww.setHasFixedSize(true);
        rvieww.setLayoutManager(new LinearLayoutManager(getActivity()));
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Disponibilidade");


        btf = (FloatingActionButton) view.findViewById(R.id.desponibilidade);
        btf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(getActivity(), DisponibilidadeTraspo.class);
                startActivity(k);
            }
        });

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

                    SolicitarDados dado = snapshot.getValue(SolicitarDados.class);
                    mdado.add(dado);
                }
                adapterr = new AdapterPassageiro(getActivity(), mdado);
                rvieww.setAdapter(adapterr);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}




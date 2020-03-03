package com.example.transcamb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class fragment_meupost extends Fragment {
    @Nullable
   private AdapterTransportador transportador;
    private RecyclerView recyclerView;
    private ArrayList<DisponibilidadeDados>disponibilidadeDados;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meupost, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        disponibilidadeDados = new ArrayList<>();

        List<String> itens = new ArrayList<>();
        itens.clear();
        itens.add("solicitar");
        itens.add("disponibilidade");



        return view;
    }

        @Override
        public void onStart() {
            super.onStart();

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();

            databaseReference.child("Disponibilidade").orderByChild("id").equalTo(Objects.requireNonNull(user).getUid())
                    .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    disponibilidadeDados = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        DisponibilidadeDados dado = snapshot.getValue(DisponibilidadeDados.class);
                        disponibilidadeDados.add(dado);
                    }
                    transportador = new AdapterTransportador(getActivity(), disponibilidadeDados);
                    recyclerView.setAdapter(transportador);

                    transportador.setOnItemLongClickListener(position -> alertDialog(disponibilidadeDados.get(position).getDesponibilidadeKey()
                                        ,disponibilidadeDados.get(position).getPath()
                                        ));
//                            }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
//    }

//        for (String item : itens){
//            databaseReference.child("Disponibilidade").orderByChild("id").equalTo(Objects.requireNonNull(user).getUid())
//                    .addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()){
//                                for(DataSnapshot snapshot :dataSnapshot.getChildren()){
//                                    DisponibilidadeDados dados = new DisponibilidadeDados();
//                                    dados.setNomeT(snapshot.child("destinoT").getValue(String.class));
//                                    dados.setDestinoT(snapshot.child("horaT").getValue(String.class));
//                                    dados.setDesponibilidadeKey(snapshot.child("").getValue(String.class));
//                                    dados.setLocalizacaoT(snapshot.child("").getValue(String.class));
//                                    dados.setHoraT(snapshot.child("").getValue(String.class));
//                                    dados.setTimestemp(snapshot.child("").getValue(String.class));
//                                    disponibilidadeDados.add(dados);
//                                }
//                                transportador = new AdapterTransportador(getContext(),disponibilidadeDados);
//                                recyclerView.setAdapter(transportador);
//
//                                transportador.setOnItemLongClickListener(position -> alertDialog(disponibilidadeDados.get(position).getDesponibilidadeKey()
//                                        ,disponibilidadeDados.get(position).getPath()
//                                        ));
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//
//
//                        }
//                    });
//        }
//
//        return view;
//    }
//
    private void alertDialog(String codigo, String path) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Oque deseja fazer?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Apagar",
                (dialog, id) -> {

                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Aguarde um instante");
                    progressDialog.show();

                    databaseReference.child(path).child(codigo).removeValue((databaseError, databaseReference) -> {

                        progressDialog.dismiss();
                        new AlertDialog.Builder(getContext())
                                .setMessage("Apagou o livro")
                                .setPositiveButton(android.R.string.yes, (dialog1, which) -> {
                                    Intent intent = new Intent(getContext(), TransportadorLogado.class);
                                    startActivity(intent);
                                })
                                .setIcon(android.R.drawable.alert_dark_frame)
                                .show();
                    });

                    });
        builder1.setNegativeButton(
                "Actualizar",
                (dialog, id) -> {

                    Intent intent = new Intent(getContext(), DisponibilidadeTraspo.class);
                    intent.putExtra("codigo", codigo);
                    intent.putExtra("path", path);
                    startActivity(intent);

                });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
    }
}

package com.example.transcamb;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterPassageiro extends RecyclerView.Adapter<AdapterPassageiro.SolicitarViewHolder> {

    private Context context;
    private List<SolicitarDados>mDados;

    public AdapterPassageiro(Context context, List<SolicitarDados> mDados) {
        this.context = context;
        this.mDados = mDados;
    }

    @NonNull
    @Override
    public SolicitarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.itemstransportador,parent,false);
        return new  SolicitarViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull SolicitarViewHolder holder, int position) {
        holder.tv_nome.setText("Nome: "+mDados.get(position).getNome());
        holder.tv_horaPartida.setText("Hora Partida: "+mDados.get(position).getHora());
        holder.tv_destino.setText("Destino: "+mDados.get(position).getDestino());
        holder.tv_numPassage.setText("N° Passageiros: "+mDados.get(position).getNumerPassageiros());
        holder.tv_hora.setText(timestampToString((Long)mDados.get(position).getTimestemp()));
        holder.tv_loca.setText("Localização: "+mDados.get(position).getLocalizacao());
    }

    @Override
    public int getItemCount() {
        return mDados.size();
    }

    public class SolicitarViewHolder extends RecyclerView.ViewHolder{

        TextView tv_nome,tv_hora,tv_destino,tv_numPassage, tv_horaPartida,tv_loca;

        public SolicitarViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nome = itemView.findViewById(R.id.nomeview);
            tv_hora =itemView.findViewById(R.id.horaview);
            tv_destino = itemView.findViewById(R.id.destinoview);
            tv_numPassage = itemView.findViewById(R.id.numeroview);
            tv_horaPartida = itemView.findViewById(R.id.horapartidaview);
            tv_loca = itemView.findViewById(R.id.localizacaoview);
        }
    }
    private String timestampToString(long time){
        Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String data = DateFormat.format("hh:mm",calendar).toString();
        return data;
    }
}

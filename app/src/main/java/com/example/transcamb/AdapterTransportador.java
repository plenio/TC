package com.example.transcamb;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterTransportador extends RecyclerView.Adapter<AdapterTransportador.DisponibilidadeViewHolder> {


    private OnItemLongClickListener lOngClickListener;
    private Context context;
    private List<DisponibilidadeDados>mDado;

    public AdapterTransportador(Context context, List<DisponibilidadeDados> mmDado) {
        this.context = context;
        this.mDado = mmDado;
        this.mDado = mDado;
    }

    @NonNull
    @Override
    public DisponibilidadeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.item_passageiro,parent,false);
        return new  DisponibilidadeViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull DisponibilidadeViewHolder holder, final int position) {
//        DisponibilidadeDados ld = mDado.get(position);
//        holder.tv_nomeTrans.setText(ld.getNomeT());
//        holder.tv_horaPartidaTrans.setText(ld.getHoraT());
//        holder.tv_destinoTrans.setText(ld.getDestinoT());
//        holder.id.setText(ld.getDesponibilidadeKey());
        holder.tv_locaTrans.setText(mDado.get(position).getDesponibilidadeKey());
        holder.tv_nomeTrans.setText("Transporte "+mDado.get(position).getNomeT());
        holder.tv_name_zeroT.setText(mDado.get(position).getNomeT().substring(0, 1));
        holder.tv_horaPartidaTrans.setText("Hora Partida: "+mDado.get(position).getHoraT());
        holder.tv_destinoTrans.setText("Destino: "+mDado.get(position).getDestinoT());
        holder.tv_horaTrans.setText(timestampToString((Long)mDado.get(position).getTimestemp()));
        holder.tv_locaTrans.setText("Localização: "+mDado.get(position).getLocalizacaoT());
}

    @Override
    public int getItemCount() {
        return mDado.size();
    }

    public class DisponibilidadeViewHolder extends RecyclerView.ViewHolder{

        TextView id,tv_nomeTrans,tv_horaTrans,tv_destinoTrans, tv_horaPartidaTrans,tv_locaTrans, tv_name_zeroT;

        public DisponibilidadeViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nomeTrans = itemView.findViewById(R.id.nomeTransV);
            tv_horaTrans =itemView.findViewById(R.id.horaTransV);
            tv_name_zeroT = itemView.findViewById(R.id.tv_name_zeroT);
            tv_destinoTrans = itemView.findViewById(R.id.destinoTransV);
            id = itemView.findViewById(R.id.PassID);
            tv_horaPartidaTrans = itemView.findViewById(R.id.horapartidTransV);
            tv_locaTrans = itemView.findViewById(R.id.localizacaoTransV);


            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && lOngClickListener != null) {
                    lOngClickListener.onItemLongClick(position);
                }

            });



        }
    }



    public interface OnItemLongClickListener {
        void onItemLongClick(int position);

    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.lOngClickListener = (OnItemLongClickListener) listener;
    }


    private String timestampToString(long time){
        Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String data = DateFormat.format("hh:mm",calendar).toString();
        return data;
    }
}

package com.example.transcamb;

import com.google.firebase.database.ServerValue;

public class SolicitarDados {
    private String Destino;
    private String NumerPassageiros;
    private String Id;
    private String Hora;
    private String Localizacao;
    private String Nome;
    private Object timestemp;

    public SolicitarDados() {
    }

    public SolicitarDados(String destino, String numerPassageiros,String id, String hora, String localizacao, String nome) {
        this.Destino = destino;
        this.NumerPassageiros = numerPassageiros;
        this.Id=id;
        this.Hora = hora;
        this.Localizacao = localizacao;
        this.Nome = nome;
        this.timestemp = ServerValue.TIMESTAMP;
    }

    public SolicitarDados(String destino, String numerPassageiros, String id, String hora, String localizacao, String nome, Object timestemp) {
        Destino = destino;
        NumerPassageiros = numerPassageiros;
        this.Id = id;
        this.Hora = hora;
        this.Localizacao = localizacao;
        this.Nome = nome;
        this.timestemp = timestemp;
    }

    public String getDestino() {
        return Destino;
    }

    public void setDestino(String destino) {
        Destino = destino;
    }

    public String getNumerPassageiros() {
        return NumerPassageiros;
    }

    public void setNumerPassageiros(String numerPassageiros) {
        NumerPassageiros = numerPassageiros;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getLocalizacao() {
        return Localizacao;
    }

    public void setLocalizacao(String localizacao) {
        Localizacao = localizacao;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Object getTimestemp() {
        return timestemp;
    }

    public void setTimestemp(Object timestemp) {
        this.timestemp = timestemp;
    }
}

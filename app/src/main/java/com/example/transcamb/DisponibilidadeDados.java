package com.example.transcamb;

import com.google.firebase.database.ServerValue;

public class DisponibilidadeDados {
    private String DestinoT;
    private String Id;
    private String HoraT;
    private String LocalizacaoT;
    private String DesponibilidadeKey;
    private String NomeT;
    private Object timestemp;
    private String path;

    public DisponibilidadeDados() {
    }

    public DisponibilidadeDados(String destinoT,String desponibilidadeKey, String id, String horaT, String localizacaoT, String nomeT) {
        this.DestinoT = destinoT;
        this.Id=id;
        this.HoraT = horaT;
        this.LocalizacaoT = localizacaoT;
        this.NomeT = nomeT;
        DesponibilidadeKey = desponibilidadeKey;
        this.timestemp = ServerValue.TIMESTAMP;
    }

    public DisponibilidadeDados(String destinoT, String id, String horaT, String localizacaoT, String nomeT) {
        this.DestinoT = destinoT;
        this.Id=id;
        this.HoraT = horaT;
        this.LocalizacaoT = localizacaoT;
        this.NomeT = nomeT;
        this.timestemp = ServerValue.TIMESTAMP;
    }

    public DisponibilidadeDados(String destinoT, String id, String horaT, String localizacaoT, String nomeT, Object timestemp) {
        DestinoT = destinoT;
        this.Id = id;
        this.HoraT = horaT;
        this.LocalizacaoT = localizacaoT;
        this.NomeT = nomeT;
        this.timestemp = timestemp;
    }

    public String getDestinoT() {
        return DestinoT;
    }

    public void setDestinoT(String destinoT) {
        DestinoT = destinoT;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getHoraT() {
        return HoraT;
    }

    public void setHoraT(String horaT) {
        HoraT = horaT;
    }

    public String getDesponibilidadeKey() {
        return DesponibilidadeKey;
    }

    public void setDesponibilidadeKey(String desponibilidadeKey) {
        DesponibilidadeKey = desponibilidadeKey;
    }

    public String getLocalizacaoT() {
        return LocalizacaoT;
    }

    public void setLocalizacaoT(String localizacaoT) {
        LocalizacaoT = localizacaoT;
    }

    public String getNomeT() {
        return NomeT;
    }

    public void setNomeT(String nomeT) {
        NomeT = nomeT;
    }

    public Object getTimestemp() {
        return timestemp;
    }

    public void setTimestemp(Object timestemp) {
        this.timestemp = timestemp;
    }
}

package com.example.transcamb.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.transcamb.costantes.Constants;

import static com.example.transcamb.costantes.Constants.MEU_ARQUIVO;
import static com.example.transcamb.costantes.Constants.USER_STATUS;

public class SharedPref{
    private Context  context;
    private SharedPreferences preferences;

    public SharedPref(Context context) {
        this.context = context;
    }



    public void savePreference(String valor){
        preferences = context.getSharedPreferences(MEU_ARQUIVO, Context.MODE_PRIVATE);
        preferences.edit().putString(USER_STATUS, valor)
                .apply();
    }

    public String readPreference(){
        preferences = context.getSharedPreferences(MEU_ARQUIVO, Context.MODE_PRIVATE);
        return preferences.getString(USER_STATUS, null);
    }

    public void clearPreference(){
        preferences = context.getSharedPreferences(MEU_ARQUIVO, Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }
}

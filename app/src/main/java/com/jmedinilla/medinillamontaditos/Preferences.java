package com.jmedinilla.medinillamontaditos;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

class Preferences {
    private static SharedPreferences sharedPreferences;
    private static Preferences instance;

    private Preferences(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    static Preferences getInstance(Context context) {
        if (instance == null) {
            instance = new Preferences(context);
        }
        return instance;
    }

    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

    void saveMontaditos() {
        SharedPreferences.Editor editor = getEditor();
        Repository repository = Repository.getInstance();
        for (int i = 0; i < repository.size(); i++) {
            Product product = repository.get(i);
            editor.putInt(product.getName(), product.getQuantity()).commit();
        }
    }

    void loadMontaditos() {
        Repository repository = Repository.getInstance();
        for (int i = 0; i < repository.size(); i++) {
            Product product = repository.get(i);
            product.setQuantity(sharedPreferences.getInt(product.getName(), 0));
        }
    }
}

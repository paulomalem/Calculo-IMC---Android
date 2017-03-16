package br.com.paulomalem.calculoimc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by paulo on 03/08/2016.
 */
public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context) {
        super(context, "IMC", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ScriptSQL.getCreateIMC());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
package br.com.paulomalem.calculoimc.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import br.com.paulomalem.calculoimc.R;
import br.com.paulomalem.calculoimc.app.HistoricoArrayAdapter;
import br.com.paulomalem.calculoimc.dominio.entidades.Imc;

/**
 * Created by paulo on 03/08/2016.
 */
public class RepositorioImc {

    private SQLiteDatabase conn;

    public RepositorioImc(SQLiteDatabase conn) {

        this.conn = conn;

    }

    public Imc setComponentes(Cursor cursor) {

        Imc imc = new Imc();
        imc.setId(cursor.getLong(cursor.getColumnIndex(imc.ID)));
        imc.setPeso(cursor.getString(cursor.getColumnIndex(imc.PESO)));
        imc.setAltura(cursor.getString(cursor.getColumnIndex(imc.ALTURA)));
        imc.setResultadoImc(cursor.getString(cursor.getColumnIndex(imc.RESULTADOIMC)));
        imc.setData_consulta(cursor.getString(cursor.getColumnIndex(imc.DATA_CONSULTA)));

        return imc;
    }

    private ContentValues preencheContentValues(Imc imc) {

        ContentValues values = new ContentValues();

        values.put(Imc.PESO, imc.getPeso());
        values.put(Imc.ALTURA, imc.getAltura());
        values.put(Imc.RESULTADOIMC, imc.getResultadoImc());
        values.put(Imc.DATA_CONSULTA, imc.getData_consulta());

        return values;
    }

    public int inserir(Imc imc) {

        ContentValues values = preencheContentValues(imc);

        long linhaInserida = conn.insertOrThrow(Imc.TABELA, null, values);

        if (linhaInserida == -1)
            return 0; // nao inserido
        else
            return 1; // inserido

    }

    public boolean existeHistorico() {

        boolean bexiste;

        bexiste = false;

        Cursor cur = conn.rawQuery("SELECT COUNT(*) FROM " + Imc.TABELA, null);

        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt(0) != 0) {
                bexiste = true;
            }
        }
        return bexiste;
    }

    public HistoricoArrayAdapter buscarHistorico(Context context) {

        HistoricoArrayAdapter historicoArrayAdapter = new HistoricoArrayAdapter(context, R.layout.activity_lista_historico);

        Cursor cursor = conn.query(Imc.TABELA, null, null, null, null, null, "_id DESC");

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {

                Imc imc = setComponentes(cursor);

                historicoArrayAdapter.add(imc);

            } while (cursor.moveToNext());
        }

        return historicoArrayAdapter;
    }

    public void excluirExcedente() {
        int iLinha = (int) DatabaseUtils.queryNumEntries(conn, Imc.TABELA);

        if (iLinha > 20) {
            long iRegistroExcluir = buscaMenorID();

            conn.delete(Imc.TABELA, " _id = ? ", new String[]{String.valueOf(iRegistroExcluir)});
        }
    }

    public void excluirTudo() {
        conn.delete(Imc.TABELA, null, null);
    }

    public void excluirUltimo() {

        conn.delete(Imc.TABELA, "_id=(SELECT MAX(_id) from IMC)", null);
    }

    private long buscaMenorID() {

        long id = 0;

        Cursor cursor = conn.query(Imc.TABELA, null, "_id=(SELECT MIN(_id) from IMC)", null, null, null, null);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            id = cursor.getInt(cursor.getColumnIndex(Imc.ID));

        }

        return id;

    }

    private long buscaMaiorID() {

        long id = 0;

        Cursor cursor = conn.query(Imc.TABELA, null, "_id=(SELECT MAX(_id) from IMC)", null, null, null, null);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            id = cursor.getInt(cursor.getColumnIndex(Imc.ID));

        }

        return id;

    }
}

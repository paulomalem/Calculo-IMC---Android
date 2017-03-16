package br.com.paulomalem.calculoimc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.paulomalem.calculoimc.database.DataBase;
import br.com.paulomalem.calculoimc.dominio.RepositorioImc;
import br.com.paulomalem.calculoimc.dominio.entidades.Imc;

public class MainActivity extends Activity implements View.OnClickListener {

    private RepositorioImc repositorioImc;

    private DataBase dataBase;
    private SQLiteDatabase conn;

    private Button buttonCalcula;
    private Button buttonHistico;
    private Button buttonSobre;

    private Imc imc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCalcula = (Button) findViewById(R.id.buttonCalcula);
        buttonHistico = (Button) findViewById(R.id.buttonHistorico);
        buttonSobre = (Button) findViewById(R.id.buttonSobre);

        buttonCalcula.setOnClickListener(this);
        buttonHistico.setOnClickListener(this);
        buttonSobre.setOnClickListener(this);

        try {
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            repositorioImc = new RepositorioImc(conn);

        } catch (SQLException ex) {

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao criar o banco: " + ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Fonte01.ttf");
        TextView myTextView = (TextView) findViewById(R.id.textImc);
        myTextView.setTypeface(myTypeface);

    }



    @Override
    public void onClick(View v) {

        if (v == buttonCalcula) {
            Intent it = new Intent(this, CalculoImcActivity.class);
            startActivity(it);

        } else if (v == buttonHistico) {
            boolean bEncontrou = repositorioImc.existeHistorico();
            if (bEncontrou) {
                Intent it = new Intent(this, HistoricoActivity.class);
                startActivity(it);
            } else {
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setMessage("Não existe nenhum registro.");
                dlg.setNeutralButton("OK", null);
                dlg.show();
            }
        } else if (v == buttonSobre) {
            Intent it = new Intent(this, SobreActivity.class);
            startActivity(it);
        }
    }
}
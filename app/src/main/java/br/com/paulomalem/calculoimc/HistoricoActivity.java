package br.com.paulomalem.calculoimc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import br.com.paulomalem.calculoimc.database.DataBase;
import br.com.paulomalem.calculoimc.dominio.RepositorioImc;
import br.com.paulomalem.calculoimc.dominio.entidades.Imc;

public class HistoricoActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView lstHistorico;
    private ArrayAdapter<Imc> adpHistorico;

    private Button buttonLimpa;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioImc repositorioImc;

    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        lstHistorico = (ListView) findViewById(R.id.lstHistorico);

        lstHistorico.setOnItemClickListener(this);

        buttonLimpa = (Button) findViewById(R.id.buttonLimpa);
        buttonLimpa.setOnClickListener(this);

        try {
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            repositorioImc = new RepositorioImc(conn);

            adpHistorico = repositorioImc.buscarHistorico(this);

            lstHistorico.setAdapter(adpHistorico);

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onClick(View v) {


        if (v == buttonLimpa) {

            boolean bEncontrou = repositorioImc.existeHistorico();
                if (bEncontrou) {
                    perguntaExcluirHistorico();
                } else {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                    dlg.setMessage("Não existe nenhum registro.");
                    dlg.setNeutralButton("OK", null);
                    dlg.show();
                }
            }
        }


    private void perguntaExcluirHistorico() {
        //Cria o gerador do AlertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Excluir");
        //define a mensagem
        builder.setMessage("Deseja excluir todo histórico?");
        //define um botão como positivo
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                repositorioImc.excluirTudo();
                Intent i = new Intent(HistoricoActivity.this, HistoricoActivity.class);
                finish();
                startActivity(i);
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.cancel();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }


    private void perguntaSalvarHistorico() {
        //Cria o gerador do AlertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Salvar");
        //define a mensagem
        builder.setMessage("Deseja salvar IMC?");
        //define um botão como positivo
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                repositorioImc.excluirTudo();
                Intent i = new Intent(HistoricoActivity.this, HistoricoActivity.class);
                startActivity(i);
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.cancel();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }
}


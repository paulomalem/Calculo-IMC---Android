package br.com.paulomalem.calculoimc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.paulomalem.calculoimc.database.DataBase;
import br.com.paulomalem.calculoimc.dominio.RepositorioImc;

public class PesoIdealActivity extends Activity implements View.OnClickListener {

    private TextView txtResultado;

    private Button buttonCalculaNova;
    private Button buttonSalvarHistorico;
    private Button buttonVoltar;

    private AlertDialog alerta;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioImc repositorioImc;

    double imc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peso_ideal);

        txtResultado = (TextView) findViewById(R.id.txtResultadoIdeal);

        obtemExtras();

        txtResultado.setText(String.format("%.2f", imc));

        buttonCalculaNova = (Button) findViewById(R.id.buttonCalculaNova);
        buttonSalvarHistorico = (Button) findViewById(R.id.buttonSalvarHistorico);
        buttonVoltar = (Button) findViewById(R.id.buttonVoltar);

        buttonCalculaNova.setOnClickListener(this);
        buttonSalvarHistorico.setOnClickListener(this);
        buttonVoltar.setOnClickListener(this);


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

    }

    public void obtemExtras() {

        Bundle bundle = getIntent().getExtras();

        if ((bundle != null) && (bundle.containsKey("TELA"))) {
            imc = bundle.getDouble("TELA");
        }

    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                //define o titulo
                .setTitle("Voltar")
                //define a mensagem
                .setMessage("Deseja mesmo sair? O IMC não será salvo")
                //define um botão como positivo
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        repositorioImc.excluirUltimo();

                        Intent i = new Intent(PesoIdealActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }

                })
                .show();

    }

    @Override
    public void onClick(View v) {

        if (v == buttonCalculaNova) {

            new AlertDialog.Builder(this)
                    //define o titulo
                    .setTitle("Voltar")
                    //define a mensagem
                    .setMessage("Deseja realizar outro cálculo? O IMC não será salvo!")
                    //define um botão como positivo
                    .setPositiveButton("SIM", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            repositorioImc.excluirUltimo();
                            new Intent(PesoIdealActivity.this, CalculoImcActivity.class);
                            finish();
                        }
                    })
                    .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }

                    })
                    .show();

        } else if (v == buttonSalvarHistorico) {

            Intent i = new Intent(PesoIdealActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

            Context context = getApplicationContext();
            CharSequence text = "IMC Salvo com sucesso!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else if (v == buttonVoltar) {

            new AlertDialog.Builder(this)
                    //define o titulo
                    .setTitle("Voltar")
                    //define a mensagem
                    .setMessage("Deseja mesmo sair? O IMC não será salvo!")
                    //define um botão como positivo
                    .setPositiveButton("SIM", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            repositorioImc.excluirUltimo();
                            //new Intent(Obesidade1Activity.this, CalculoImcActivity.class);
                            //Obesidade1Activity actOb1 = new Obesidade1Activity();
                            //actOb1.finish();
                            //finish();
                            Intent i = new Intent(PesoIdealActivity.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                    })
                    .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }

                    })
                    .show();
        }
    }


}

package br.com.paulomalem.calculoimc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

import br.com.paulomalem.calculoimc.database.DataBase;
import br.com.paulomalem.calculoimc.dominio.RepositorioImc;
import br.com.paulomalem.calculoimc.dominio.entidades.Imc;
import br.com.paulomalem.calculoimc.util.Mask;
import br.com.paulomalem.calculoimc.util.MaskPesoTresQuatroDigitos;
import br.com.paulomalem.calculoimc.util.Util;

public class CalculoImcActivity extends Activity implements View.OnClickListener {

    private RepositorioImc repositorioImc;

    private DataBase dataBase;
    private SQLiteDatabase conn;

    private AlertDialog alerta;

    private EditText editTextPeso;
    private EditText editTextAltura;
    private Button buttonCalcula;

    double p;
    double a;
    double imc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo_imc);

        editTextPeso = (EditText) findViewById(R.id.editTextPeso);
        editTextPeso.addTextChangedListener(MaskPesoTresQuatroDigitos.insert(editTextPeso));
        editTextAltura = (EditText) findViewById(R.id.editTextAltura);
        editTextAltura.addTextChangedListener(Mask.insert("#.##", editTextAltura));

        buttonCalcula = (Button) findViewById(R.id.buttonCalcula);
        buttonCalcula.setOnClickListener(this);

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

        //Não abre o teclado automaticamente
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Fonte01.ttf");
        TextView myTextView = (TextView) findViewById(R.id.textImc);
        myTextView.setTypeface(myTypeface);
    }

    public void onClick(View v) {

        if (v == buttonCalcula) {

            if (verificaCamposTela(this)) {
                calcular();
                salvar();
                excluirmaisdedez();
                limparCampos();

                if (imc <= 18.5) {
                    Intent it = new Intent(this, PesoAbaixoActivity.class);
                    double TelaConsulta = imc;
                    it.putExtra("TELA", TelaConsulta);
                    startActivity(it);

                } else if ((imc >= 18.6) && (imc <= 24.9)) {
                    Intent it = new Intent(this, PesoIdealActivity.class);
                    double TelaConsulta = imc;
                    it.putExtra("TELA", TelaConsulta);
                    startActivity(it);


                } else if ((imc >= 25) && (imc <= 29.9)) {
                    Intent it = new Intent(this, SobrePesoActivity.class);
                    double TelaConsulta = imc;
                    it.putExtra("TELA", TelaConsulta);
                    startActivity(it);


                } else if ((imc >= 30) && (imc <= 34.9)) {
                    Intent it = new Intent(this, Obesidade1Activity.class);
                    double TelaConsulta = imc;
                    it.putExtra("TELA", TelaConsulta);
                    startActivity(it);


                } else if ((imc >= 35) && (imc <= 39.9)) {
                    Intent it = new Intent(this, Obesidade2Activity.class);
                    double TelaConsulta = imc;
                    it.putExtra("TELA", TelaConsulta);
                    startActivity(it);


                } else if (imc >= 40) {
                    Intent it = new Intent(this, Obesidade3Activity.class);
                    double TelaConsulta = imc;
                    it.putExtra("TELA", TelaConsulta);
                    startActivity(it);
                }
            }
        }
    }


    private boolean verificaCamposTela(Context classe) {
        String message = "";
        boolean verificaCamposTela = true;
        if (this.editTextPeso.getText().toString().trim().isEmpty()) {
            message = "Favor inserir: Peso";
            verificaCamposTela = false;
            this.editTextPeso.requestFocus();
        } else if (this.editTextAltura.getText().toString().trim().isEmpty()) {
            message = "Favor inserir: Altura";
            verificaCamposTela = false;
            this.editTextAltura.requestFocus();
        }

        if (message != "") {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage(message);
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        return verificaCamposTela;
    }

    public void calcular() {
        this.p = Double.parseDouble(this.editTextPeso.getText().toString());
        this.a = Double.parseDouble(this.editTextAltura.getText().toString());

        String sMensagem = "";
        String sTitle = "";

        if (this.p == 0.0d) {
            sMensagem = "Insira valor válido no peso";
        } else if (this.a == 0.0d) {
            sMensagem = "Insira valor válido na altura.";
        } else {
            DecimalFormat df = new DecimalFormat("0.00");
            imc = (p / (a * a));
        }
    }

    public void salvar() {
        String sPeso = editTextPeso.getText().toString().trim();
        String sAltura = editTextAltura.getText().toString().trim();
        String sImc = String.valueOf(imc);
        Imc imc = new Imc();
        imc.setPeso(sPeso);
        imc.setAltura(sAltura);
        imc.setResultadoImc(sImc);
        imc.setData_consulta(Util.getDataAtualString());
        repositorioImc.inserir(imc);
    }

    public void excluirmaisdedez() {
        repositorioImc.excluirExcedente();
    }

    public void limparCampos() {
        editTextAltura.setText("");
        editTextPeso.setText("");
        editTextPeso.requestFocus();
    }
}

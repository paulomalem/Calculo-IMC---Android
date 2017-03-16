package br.com.paulomalem.calculoimc.dominio.entidades;

import java.io.Serializable;

/**
 * Created by paulo on 03/08/2016.
 */
public class Imc implements Serializable {

    public static String TABELA = "IMC";
    public static String ID = "_id";
    public static String PESO = "PESO";
    public static String ALTURA = "ALTURA";
    public static String RESULTADOIMC = "RESULTADOIMC";
    public static String DATA_CONSULTA = "DATA_CONSULTA";

    private long id;
    private String peso;
    private String altura;
    private String resultadoImc;
    private String data_consulta;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getResultadoImc() {
        return resultadoImc;
    }

    public void setResultadoImc(String resultadoImc) {
        this.resultadoImc = resultadoImc;
    }

    public String getData_consulta() {
        return data_consulta;
    }

    public void setData_consulta(String data_consulta) {
        this.data_consulta = data_consulta;
    }
}

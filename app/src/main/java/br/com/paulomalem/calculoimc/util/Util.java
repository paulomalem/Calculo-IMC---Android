package br.com.paulomalem.calculoimc.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by paulo on 03/08/2016.
 */
public class Util {

    public static String getDataAtualString() {

        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

        Date date = new Date();

        String data = dateformat.format(date);

        return data;
    }
}

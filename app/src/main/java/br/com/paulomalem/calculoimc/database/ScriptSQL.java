package br.com.paulomalem.calculoimc.database;

/**
 * Created by paulo on 03/08/2016.
 */
public class ScriptSQL {

    public static String getCreateIMC() {

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("CREATE TABLE IF NOT EXISTS IMC ( ");
        sqlBuilder.append("_id                  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("PESO                 VARCHAR(10),");
        sqlBuilder.append("ALTURA               VARCHAR(10),");
        sqlBuilder.append("RESULTADOIMC        VARCHAR(10),");
        sqlBuilder.append("DATA_CONSULTA        VARCHAR(20) ");
        sqlBuilder.append(" ); ");

        return sqlBuilder.toString();

    }

}

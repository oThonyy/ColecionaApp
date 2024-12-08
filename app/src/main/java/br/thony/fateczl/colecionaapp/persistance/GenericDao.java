package br.thony.fateczl.colecionaapp.persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDao extends SQLiteOpenHelper {

    private static final String DATABASE = "ColecaoDB";

    private static final int DATABASE_VER = 1;

    public static final String CREATE_TABLE_COLECAO =
            "CREATE TABLE colecao (" +
                    "colecaoId INTEGER PRIMARY KEY UNIQUE," +
                    "nome VARCHAR(35) NOT NULL," +
                    "descricao VARCHAR(100)," +
                    "categoria VARCHAR(35)," +
                    "dataCriacao VARCHAR(10) NOT NULL);";

    public static final String CREATE_TABLE_ITEM =
            "CREATE TABLE item (" +
                    "itemId INTEGER PRIMARY KEY UNIQUE," +
                    "nome VARCHAR(35) NOT NULL," +
                    "descricao VARCHAR(100)," +
                    "condicao VARCHAR(35)," +
                    "valorEstimado VARCHAR(10)," +
                    "dataAquisicao VARCHAR(10)," +
                    "colecaoId INTEGER NOT NULL," +
                    "FOREIGN KEY (colecaoId) REFERENCES colecoes (colecaoId) ON DELETE CASCADE);";

    public GenericDao(Context context) {
        super(context, DATABASE, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_COLECAO);
        sqLiteDatabase.execSQL(CREATE_TABLE_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int antigaVersao, int novaVersao) {
        if (novaVersao > antigaVersao) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS colecao");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS item");
            onCreate(sqLiteDatabase);
        }
    }
}

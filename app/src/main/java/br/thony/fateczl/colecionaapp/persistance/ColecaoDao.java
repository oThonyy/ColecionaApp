package br.thony.fateczl.colecionaapp.persistance;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.thony.fateczl.colecionaapp.model.Colecao;

public class ColecaoDao implements IColecaoDao, ICRUDDao<Colecao> {

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;

    public ColecaoDao(Context context) {
        this.context = context;
    }

    @Override
    public ColecaoDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Colecao colecao) throws SQLException {
        open();
        ContentValues contentValues = getContentValues(colecao);
        database.insert("colecao",null, contentValues);
        close();
    }

    @Override
    public int update(Colecao colecao) throws SQLException {
        open();
        ContentValues contentValues = getContentValues(colecao);
        int ret = database.update("colecao", contentValues, "colecaoId =" + colecao.getId(), null);
        close();
        return ret;
    }

    @Override
    public void delete(Colecao colecao) throws SQLException {
        open();
        database.delete("colecao", "colecaoId =" + colecao.getId(), null);
        close();
    }

    @SuppressLint("Range")
    @Override
    public Colecao findOne(Colecao colecao) throws SQLException {
        open();
        String sql = "SELECT * FROM colecao WHERE colecaoId =" + colecao.getId();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()){

            colecao.setId(cursor.getInt(cursor.getColumnIndex("colecaoId")));
            colecao.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            colecao.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            colecao.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
            colecao.setDataCriacao(cursor.getString(cursor.getColumnIndex("dataCriacao")));
        }
        cursor.close();
        close();
        return colecao;
    }

    @SuppressLint("Range")
    @Override
    public List<Colecao> findAll() throws SQLException {
        open();
        List<Colecao> colecao = new ArrayList<>();
        String sql = "SELECT * FROM colecao";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Colecao c = new Colecao();

                c.setId(cursor.getInt(cursor.getColumnIndex("colecaoId")));
                c.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                c.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                c.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                c.setDataCriacao(cursor.getString(cursor.getColumnIndex("dataCriacao")));

                colecao.add(c);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        close();
        return colecao;
    }

    private ContentValues getContentValues(Colecao colecao) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("colecaoId", colecao.getId());
        contentValues.put("nome", colecao.getNome());
        contentValues.put("descricao", colecao.getDescricao());
        contentValues.put("categoria", colecao.getCategoria());
        contentValues.put("dataCriacao", colecao.getDataCriacao());

        return contentValues;
    }
}

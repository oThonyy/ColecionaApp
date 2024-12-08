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
import br.thony.fateczl.colecionaapp.model.Item;

public class ItemDao implements IItemDao, ICRUDDao<Item> {

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;

    public ItemDao(Context context) {
        this.context = context;
    }

    @Override
    public ItemDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Item item) throws SQLException {
        open();
        ContentValues contentValues = getContentValues(item);
        database.insert("item", null, contentValues);
        close();
    }

    @Override
    public int update(Item item) throws SQLException {
        open();
        ContentValues contentValues = getContentValues(item);
        int ret = database.update("item", contentValues, "itemId =" + item.getId(), null);
        close();
        return ret;
    }

    @Override
    public void delete(Item item) throws SQLException {
        open();
        database.delete("item", "itemId =" + item.getId(), null);
        close();
    }

    @SuppressLint("Range")
    @Override
    public Item findOne(Item item) throws SQLException {
        open();
        String sql = "SELECT * FROM item WHERE itemId =" + item.getId();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()){

            item.setId(cursor.getInt(cursor.getColumnIndex("itemId")));
            item.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            item.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            item.setCondicao(cursor.getString(cursor.getColumnIndex("condicao")));
            item.setValorEstimado(cursor.getString(cursor.getColumnIndex("valorEstimado")));
            item.setDataAquisicao(cursor.getString(cursor.getColumnIndex("dataAquisicao")));
        }
        cursor.close();
        close();
        return item;
    }

    @SuppressLint("Range")
    @Override
    public List<Item> findAll() throws SQLException {
        open();
        List<Item> item = new ArrayList<>();
        String sql = "SELECT c.colecaoId AS id_colecao, c.nome AS nome_colecao, i.* " +
                "FROM colecao c, item i " +
                "WHERE c.colecaoId = i.colecaoId";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Item i = new Item();
                Colecao colecao = new Colecao();

                colecao.setId(cursor.getInt(cursor.getColumnIndex("id_colecao")));
                colecao.setNome(cursor.getString(cursor.getColumnIndex("nome_colecao")));

                i.setId(cursor.getInt(cursor.getColumnIndex("itemId")));
                i.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                i.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                i.setCondicao(cursor.getString(cursor.getColumnIndex("condicao")));
                i.setValorEstimado(cursor.getString(cursor.getColumnIndex("valorEstimado")));
                i.setDataAquisicao(cursor.getString(cursor.getColumnIndex("dataAquisicao")));
                i.setColecao(colecao);

                item.add(i);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        close();
        return item;
    }

    private static ContentValues getContentValues(Item item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("itemId", item.getId());
        contentValues.put("nome", item.getNome());
        contentValues.put("descricao", item.getDescricao());
        contentValues.put("condicao", item.getCondicao());
        contentValues.put("valorEstimado", item.getValorEstimado());
        contentValues.put("dataAquisicao", item.getDataAquisicao());
        contentValues.put("colecaoId", item.getColecao().getId());

        return contentValues;
    }
}

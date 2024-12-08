package br.thony.fateczl.colecionaapp.controller;

import java.sql.SQLException;
import java.util.List;

import br.thony.fateczl.colecionaapp.model.Item;
import br.thony.fateczl.colecionaapp.persistance.ItemDao;

public class ItemController implements IController<Item>{

    private final ItemDao iDao;

    public ItemController(ItemDao iDao) {
        this.iDao = iDao;
    }

    @Override
    public void inserir(Item item) throws SQLException {
        if (iDao == null){
            iDao.open();
        }
        iDao.insert(item);
        iDao.close();
    }

    @Override
    public void editar(Item item) throws SQLException {
        if (iDao == null){
            iDao.open();
        }
        iDao.update(item);
        iDao.close();
    }

    @Override
    public void excluir(Item item) throws SQLException {
        if (iDao ==  null){
            iDao.open();
        }
        iDao.delete(item);
        iDao.close();
    }

    @Override
    public Item buscar(Item item) throws SQLException {
        if (iDao ==  null){
            iDao.open();
        }
        return  iDao.findOne(item);
    }

    @Override
    public List<Item> listar() throws SQLException {
        if (iDao ==  null){
            iDao.open();
        }
        return iDao.findAll();
    }
}
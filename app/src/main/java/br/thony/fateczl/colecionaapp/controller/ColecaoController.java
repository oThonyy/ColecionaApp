package br.thony.fateczl.colecionaapp.controller;

import java.sql.SQLException;
import java.util.List;

import br.thony.fateczl.colecionaapp.model.Colecao;
import br.thony.fateczl.colecionaapp.persistance.ColecaoDao;

public class ColecaoController implements IController<Colecao>{

    private final ColecaoDao cDao;

    public ColecaoController(ColecaoDao cDao) {
        this.cDao = cDao;
    }

    @Override
    public void inserir(Colecao colecao) throws SQLException {
        if (cDao == null){
            cDao.open();
        }
        cDao.insert(colecao);
        cDao.close();
    }

    @Override
    public void editar(Colecao colecao) throws SQLException {
        if (cDao == null){
            cDao.open();
        }
        cDao.update(colecao);
        cDao.close();
    }

    @Override
    public void excluir(Colecao colecao) throws SQLException {
        if (cDao == null){
            cDao.open();
        }
        cDao.delete(colecao);
        cDao.close();
    }

    @Override
    public Colecao buscar(Colecao colecao) throws SQLException {
        if (cDao == null){
            cDao.open();
        }
        return cDao.findOne(colecao);
    }

    @Override
    public List<Colecao> listar() throws SQLException {
        if (cDao == null){
            cDao.open();
        }
        return cDao.findAll();
    }
}
package br.thony.fateczl.colecionaapp.controller;

import java.sql.SQLException;
import java.util.List;

public interface IController<T> {

    public void inserir(T t) throws SQLException;
    public void editar(T t) throws SQLException;
    public void excluir(T t) throws SQLException;
    public T buscar(T t) throws SQLException;
    public List<T> listar() throws SQLException;

}

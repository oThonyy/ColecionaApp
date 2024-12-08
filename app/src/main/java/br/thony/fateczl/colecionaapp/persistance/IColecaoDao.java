package br.thony.fateczl.colecionaapp.persistance;

import java.sql.SQLException;

public interface IColecaoDao {
    public ColecaoDao open() throws SQLException;
    public void close();
}

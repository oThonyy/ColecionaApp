package br.thony.fateczl.colecionaapp.persistance;

import java.sql.SQLException;

public interface IItemDao {
    public ItemDao open() throws SQLException;
    public void close();

}

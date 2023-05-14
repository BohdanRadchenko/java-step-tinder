package org.tinder.dao;

import org.tinder.interfaces.IDAO;
import org.tinder.models.Model;

import java.sql.Connection;
import java.sql.SQLException;

abstract public class DAO<T extends Model> implements IDAO<T> {
    public final Connection connection;

    public DAO(Connection connection) {
        this.connection = connection;
    }

    abstract int create(T model) throws SQLException;

    abstract int update(T model) throws SQLException;

    @Override
    public boolean save(T model) throws SQLException {
        if (model.getId() == null) {
            return create(model) >= 1;
        }
        return update(model) >= 1;
    }
}

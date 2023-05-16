package org.tinder.interfaces;

import java.util.Optional;

public interface DAO<T extends Model>{
    boolean delete(Integer id) throws Exception;
    default boolean delete(T t) throws Exception {
        return delete(t.id());
    }
    Optional<T> getById(Integer id) throws Exception;

   boolean save(T model) throws Exception;
}

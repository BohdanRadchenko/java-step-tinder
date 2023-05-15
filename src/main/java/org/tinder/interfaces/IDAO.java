package org.tinder.interfaces;

import java.util.Optional;

public interface IDAO<T extends Model> {

    boolean save(T model) throws Exception;

    boolean delete(Integer id) throws Exception;

    Optional<T> getById(Integer id) throws Exception;
}

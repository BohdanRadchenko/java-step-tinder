package org.tinder.interfaces;

import org.tinder.models.Model;

import java.util.Optional;

public interface IDAO<T extends Model> {

    boolean save(T model) throws Exception;

    boolean delete(String id) throws Exception;

    Optional<T> getById(String id) throws Exception;
}

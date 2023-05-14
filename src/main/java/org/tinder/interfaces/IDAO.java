package org.tinder.interfaces;

import org.tinder.models.Model;

import java.util.Optional;

public interface IDAO<T extends Model> {

    boolean save(T model) throws Exception;

    boolean delete(String uuid) throws Exception;

    Optional<T> getById(String uuid) throws Exception;
}

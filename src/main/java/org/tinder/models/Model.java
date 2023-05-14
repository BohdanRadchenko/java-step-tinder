package org.tinder.models;

abstract public class Model {
    private final Integer id;

    protected Model(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}

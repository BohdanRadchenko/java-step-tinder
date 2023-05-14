package org.tinder.models;

public abstract class Model {
    private final Integer id;

    protected Model(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}

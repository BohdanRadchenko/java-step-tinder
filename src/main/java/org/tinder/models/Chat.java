package org.tinder.models;

import org.tinder.interfaces.Model;

public class Chat implements Model {

    private User user;
    private String name;
    private final Integer id;
    public Chat(Integer id, User user, String name) {
        this.id = id;
        this.user = user;
        this.name = name;
    }

    //цей конструктор тимчасовий
    public Chat(Integer id) {
        this.id = id;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer id() {
        return this.id;
    }
}

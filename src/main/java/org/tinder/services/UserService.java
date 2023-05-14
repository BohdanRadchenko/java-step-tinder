package org.tinder.services;

import org.tinder.dao.UserDao;
import org.tinder.utils.Database;

public class UserService {
    private final UserDao db = new UserDao(Database.getConnection());
}

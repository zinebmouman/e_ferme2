package com.JAVA.DAO;

import com.JAVA.Beans.User;

public interface UserDAO {
    User findUserByLoginAndPassword(String email, String password);
}

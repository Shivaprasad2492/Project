package org.upgrad.services;

import org.upgrad.models.User;

public interface UserService {

    String findUserByEmail(String email);

    String findUserByUsername(String userName);

    void deleteUser(Integer userId);

    Integer findUserId(String userName);

    String findUserPassword(String userName);

    String findUserRole(String userName);

    Iterable<User> getAllUsers();

    void saveUser(String userName,String email, String password);
}

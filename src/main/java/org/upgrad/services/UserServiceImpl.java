package org.upgrad.services;

import org.upgrad.models.User;
import org.springframework.stereotype.Service;
import org.upgrad.repositories.UserRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String findUserByUsername(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public String findUserPassword(String userName) {
        return userRepository.findUserPassword(userName);
    }

    @Override
    public Integer findUserId(String userName) {
        return userRepository.findUserId(userName);
    }

    @Override
    public String findUserRole(String userName) {
        return userRepository.findUserRole(userName);
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Integer userId){ userRepository.deleteById(userId);}

    @Override
    public void saveUser(String userName,String email,String password) {
        String role="user";
        userRepository.save(userName,email,password,role);
    }
}

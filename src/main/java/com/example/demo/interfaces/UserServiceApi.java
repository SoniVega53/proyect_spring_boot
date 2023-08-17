package com.example.demo.interfaces;

import com.example.demo.model.UserModel;


import java.util.List;

public interface UserServiceApi {

    List<UserModel> getUserList();
    UserModel getUser(int id);
    UserModel setUserSave(UserModel user);
    UserModel setUserUpdate(UserModel user, int id);
    void deleteUser(int id);

    UserModel findByEmailAndPassword(String email, String password);
    List<UserModel> getUserRole(String role);
}

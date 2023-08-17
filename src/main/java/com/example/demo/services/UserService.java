package com.example.demo.services;

import com.example.demo.interfaces.UserServiceApi;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService implements UserServiceApi {
    @Autowired
    private UserRepository repository;
    @Override
    public List<UserModel> getUserList() {

        return repository.findAll();
    }

    @Override
    public UserModel getUser(int id) {
        return repository.findById(id).get();
    }

    @Override
    public UserModel setUserSave(UserModel user) {
        return repository.save(user);
    }

    @Override
    public UserModel setUserUpdate(UserModel user,int id) {
        UserModel updateModel = repository.findById(id).get();
        updateModel.setEmail(updateModel.getEmail());
        updateModel.setPassword(user.getPassword());
        updateModel.setName(user.getName());
        updateModel.setLastname(user.getLastname());
        updateModel.setPhone(user.getPhone());
        updateModel.setCarnet(user.getCarnet());
        updateModel.setUniversity(user.getUniversity());
        updateModel.setRole(updateModel.getRole());
        updateModel.setCourse(user.getCourse());

        return repository.save(updateModel);
    }

    @Override
    public void deleteUser(int id) {
        repository.deleteById(id);
    }

    @Override
    public UserModel findByEmailAndPassword(String email, String password) {
        return repository.findByEmailAndPassword(email,password);
    }

    @Override
    public List<UserModel> getUserRole(String role) {
        return repository.findByRole(role);
    }
}

package com.example.demo.controller;


import com.example.demo.interfaces.UserServiceApi;
import com.example.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@CrossOrigin
public class UserController {

    @Autowired
    private UserServiceApi serviceApi;

    //trae todos los usuarios
    @GetMapping("/api/user")
    public ResponseEntity<List<UserModel>> getUserListJSON(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type","application/json")
                .body(serviceApi.getUserList());
    }

    //trae todos los usuarios de rol user
    @GetMapping("/api/userRoleUser")
    public ResponseEntity<List<UserModel>> getUserRole(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type","application/json")
                .body(serviceApi.getUserRole("user"));
    }


    @GetMapping("/api/login/{email}/{password}")
    public ResponseEntity<UserModel> login(@PathVariable String email,@PathVariable String password){
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Type","application/json")
                .body(serviceApi.findByEmailAndPassword(email,password));
    }

    //trae informacion del usuario segun su id
    @GetMapping("/api/user/{id}")
    public ResponseEntity<UserModel> getUserIdJSON(@PathVariable int id){
        boolean exist = isExistUser(id);

        return ResponseEntity
                .status(exist ? HttpStatus.OK : HttpStatus.FOUND)
                .header("Content-Type","application/json")
                .body(exist ? serviceApi.getUser(id) : null);
    }

    //Crea usuario
    @PostMapping("/api/userSave")
    public ResponseEntity<UserModel> setUserSaveJSON(@RequestBody UserModel user){
        if (!isExistUserEmail(user.getEmail())){
            UserModel userCreated = serviceApi.setUserSave(user);
            return new ResponseEntity<>(userCreated,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
    }

    //Actualiza El usuario
    @PutMapping("/api/userSave/{id}/{password}")
    public ResponseEntity<UserModel> setUserSaveJSON(@PathVariable int id,@PathVariable String password,@RequestBody UserModel user){
        UserModel userEdit = serviceApi.getUser(id);
        if (userEdit.getPassword().equals(password)){
            UserModel userCreated = serviceApi.setUserUpdate(user,id);
            return new ResponseEntity<>(userCreated,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
    }

    //Actualiza El usuario
    @PutMapping("/api/userUpdate/{id}")
    public ResponseEntity<UserModel> setUpdateAdmin(@PathVariable int id,@RequestParam String password,@RequestParam String passwordNew){
        HttpStatus status = HttpStatus.OK;
        UserModel userEdit = serviceApi.getUser(id);
        UserModel userCreated = null;
        if (userEdit.getEmail().equals("admin")){
            if (userEdit.getPassword().equals(password)){
                userEdit.setPassword(passwordNew);
                userCreated = serviceApi.setUserUpdate(userEdit,id);
            }else{
                status = HttpStatus.BAD_REQUEST;
            }
        }else {
            status = HttpStatus.NO_CONTENT;
        }

        return ResponseEntity
                .status(status)
                .header("Content-Type","application/json")
                .body(userCreated);
    }

    //Actualiza El usuario
    @DeleteMapping ("/api/user/{id}")
    public ResponseEntity<String> deleteUserJSON(@PathVariable int id){
        boolean exist = isExistUser(id);
        if (exist)
            serviceApi.deleteUser(id);

        return ResponseEntity
                .status(exist ? HttpStatus.OK : HttpStatus.FOUND)
                .header("Content-Type","application/json")
                .body(exist ? "Se elimino Correctamente" : "No existe");

    }


    //Metodo para verificar si existe un id
    private boolean isExistUser(int id){
        for (UserModel item : serviceApi.getUserList()) {
            if (item.getIdUser() == id){
                return true;
            }
        }
        return false;
    }

    private boolean isExistUserEmail(String email){
        for (UserModel item : serviceApi.getUserList()) {
            if (item.getEmail().trim().toLowerCase().equals(email.trim().toLowerCase())){
                return true;
            }
        }
        return false;
    }
}

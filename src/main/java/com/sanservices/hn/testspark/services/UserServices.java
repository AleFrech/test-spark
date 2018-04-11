/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanservices.hn.testspark.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sanservices.hn.testspark.ConnectionFactory;
import com.sanservices.hn.testspark.JsonResult;
import java.sql.Connection;
import java.sql.SQLException;
import com.sanservices.hn.testspark.dao.UserDao;
import com.sanservices.hn.testspark.dto.LoginDto;
import com.sanservices.hn.testspark.dto.UserDto;
import com.sanservices.hn.testspark.util.EncryptionUtilites;
import static com.sanservices.hn.testspark.util.NumberUtilities.toIntOrNull;
import com.sanservices.hn.testspark.util.PropertyMap;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
/**
 *
 * @author afrech
 */
public class UserServices {

    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    
        public String loginUser(String body) throws SQLException, NoSuchAlgorithmException, IllegalArgumentException, UnsupportedEncodingException{
        try (Connection connection = ConnectionFactory.getConnection()) {
            JsonResult obj = new JsonResult();
            UserDao userDao = new UserDao(connection);
            LoginDto model =  gson.fromJson(body,LoginDto.class);
            UserDto user = userDao.getUser(model.getEmail());      
            if (user == null) {
                obj.message ="No User Found";
                 return gson.toJson(obj);

            } 
            String pass = EncryptionUtilites.hash(model.getPassword(), user.getSalt());
            System.out.println(pass);
            System.out.println(user.getPassword());
            if(!pass.equals(user.getPassword())){
                obj.message ="Invalid user credentials, please try again.";
                return gson.toJson(obj);
            }
            PropertyMap prop = PropertyMap.fromSource("server.properties");
            Algorithm algorithm = Algorithm.HMAC256(prop.get("jwtKey"));
            
            
            Date date = new Date();
            date.setTime(date.getTime() + (30 * 60 * 1000));
            user.setPassword(null);
            user.setSalt(null);
            String token = JWT.create().withExpiresAt(date)
                .withIssuer("auth0")
                .withClaim("user",gson.toJson(user))
                .sign(algorithm);
            
            obj.data = token;
            obj.status = "success";
            
            return gson.toJson(obj);
        }
    }
    
    public String getUsers() throws SQLException{
        try (Connection connection = ConnectionFactory.getConnection()) {
            JsonResult obj = new JsonResult();
            UserDao userDao = new UserDao(connection);
            ArrayList<UserDto> users = userDao.getUsers();
            if (users != null) {
                obj.data=users;
                obj.status="success"; 
            } else 
                obj.message ="No Users Found";
            return gson.toJson(obj);
        }
    }
    
    public String getUser(String id) throws SQLException{
        try (Connection connection = ConnectionFactory.getConnection()) {
            JsonResult obj = new JsonResult();
            UserDao userDao = new UserDao(connection);
            Integer userId = toIntOrNull(id);
            if(userId == null){
                obj.message = "Error fetching user, invalid user id";
                return gson.toJson(obj);
            }
            UserDto user = userDao.getUser(userId);
            if (user != null) {
                obj.status="success";
                obj.data=user;

            } else {
                 obj.message ="No User Found";
            }
            return gson.toJson(obj);
        }
    }
    

    
    public String addUser(String body) throws SQLException, NoSuchAlgorithmException{
        try (Connection connection = ConnectionFactory.getConnection()) {
            JsonResult obj = new JsonResult();
            UserDao userDao = new UserDao(connection);
            UserDto newUser =  gson.fromJson(body,UserDto.class);
            if(userDao.getUser(newUser.getEmail()) != null){
                obj.message="User already exists in database";
                return gson.toJson(obj);
            }
            newUser.setStartingDate(new java.sql.Date(System.currentTimeMillis()));
            UUID uuid = UUID.randomUUID();
            String salt = uuid.toString();
            newUser.setSalt(salt);
            newUser.setPassword(EncryptionUtilites.hash(newUser.getPassword(), salt));
           
            boolean result = userDao.insertUser(newUser);
            if (result) {
                obj.status="success";
                obj.message="Entry Added Successfully!!";
            }else{
                obj.message="Error adding entry, please try again";
            }
            return gson.toJson(obj);
        }
    }
    
    public String updateUser(String id, String body) throws SQLException{
        try (Connection connection = ConnectionFactory.getConnection()) {
            JsonResult obj = new JsonResult();
            UserDao userDao = new UserDao(connection);
            UserDto model =  gson.fromJson(body,UserDto.class);
            Integer userId = toIntOrNull(id);
            if(userId == null){
                obj.message = "Error fetching user, invalid user id";
                return gson.toJson(obj);
            }
            UserDto updateUser = userDao.getUser(userId);
            if(updateUser == null){
                obj.message ="No User Found";
                return gson.toJson(obj);
            }
            updateUser.setName(model.getName());
            updateUser.setEmail(model.getEmail());
            updateUser.setDepartmentId(model.getDepartmentId());
            updateUser.setRoleId(model.getRoleId());
            updateUser.setSalary(model.getSalary());
            
            boolean result = userDao.updateUser(updateUser);
            if (result) {
                obj.status="success";
                obj.message="Entry Updated Successfully!!";
            }else{
                obj.message="Error Updating entry, please try again";
            }
            return gson.toJson(obj);
        }
    }
    
    public String deleteUser(String id) throws SQLException{
        try (Connection connection = ConnectionFactory.getConnection()) {
            JsonResult obj = new JsonResult();
            UserDao userDao = new UserDao(connection);
            Integer userId = toIntOrNull(id);
            if(userId == null){
                obj.message = "Error deleting user, invalid user id";
                return gson.toJson(obj);
            }
            boolean result = userDao.deleteUser(userId);
            if (result) {
                obj.status="success";
                obj.message="Entry removed Successfully!!";
            }else{
                obj.message="Error removed entry, please try again";
            }
            return gson.toJson(obj);
        }
    }
    
}

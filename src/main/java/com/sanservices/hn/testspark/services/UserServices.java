/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanservices.hn.testspark.services;


import com.google.gson.Gson;
import com.sanservices.hn.testspark.ConnectionFactory;
import com.sanservices.hn.testspark.JsonResult;
import java.sql.Connection;
import java.sql.SQLException;
import com.sanservices.hn.testspark.dao.UserDao;
import com.sanservices.hn.testspark.dto.UserDto;
import static com.sanservices.hn.testspark.util.NumberUtilities.toIntOrNull;
import java.util.ArrayList;
/**
 *
 * @author afrech
 */
public class UserServices {

    private final Gson gson = new Gson();
    
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
}

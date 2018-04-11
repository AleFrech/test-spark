/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanservices.hn.testspark.controllers;

import com.sanservices.hn.testspark.dto.LoginDto;
import com.sanservices.hn.testspark.services.*;
import spark.Request;
import spark.Response;
import spark.Spark;

/**
 *
 * @author afrech
 */
public class LoginController implements Controller {
    private final UserServices userServices;

    public LoginController(){
        userServices = new UserServices();
    }
    
    @Override
    public void init() {
        Spark.path("/Login", () -> {
            Spark.post("/", this::Login);
        });
    }
       
    public Object Login(Request request, Response response) throws Exception {
        response.type("application/json");
        response.status(200);
        return userServices.loginUser(request.body());
    }

}

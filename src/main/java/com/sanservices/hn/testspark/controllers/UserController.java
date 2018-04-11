package com.sanservices.hn.testspark.controllers;

import com.sanservices.hn.testspark.services.UserServices;
import spark.Request;
import spark.Response;
import  spark.Spark;

public final class UserController implements Controller {
    
    private final UserServices userServices;

    public UserController(){
        userServices = new UserServices();
    }
    
    @Override
    public void init() {
        Spark.path("/Users", () -> {
            Spark.get("/", this::getUsers);
            Spark.get("/:id", this::getUser);
            Spark.post("/", this::addUser);
            Spark.put("/:id",this::updateUser);
            Spark.delete("/:id",this::deleteUser);
        });
    }
    
    public Object getUsers(Request request, Response response) throws Exception{
        response.type("application/json");
        response.status(200);
        return userServices.getUsers();
    }
    
    public Object getUser(Request request, Response response) throws Exception { 
        response.type("application/json");
        response.status(200);
        return userServices.getUser(request.params(":id"));
    }
    
    public Object addUser(Request request, Response response) throws Exception {
        response.type("application/json");
        response.status(200);
        return userServices.addUser(request.body());
    }
    
    public Object updateUser(Request request, Response response) throws Exception {
        response.type("application/json");
        response.status(200);
        return userServices.updateUser(request.params(":id"),request.body());
    }
    
    public Object deleteUser(Request request, Response response) throws Exception {
        response.type("application/json");
        response.status(200);
        return userServices.deleteUser(request.params(":id"));
    }
     
     
}

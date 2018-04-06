package com.sanservices.hn.testspark.controllers;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import  spark.Spark;

public final class UserController implements Controller {

    private final Gson gson = new Gson();
    
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
    
    public Object getUsers(Request request, Response response) {
        response.type("application/json");
        
        response.status(200);
        return gson.toJson("returning User List");
    }
    
    public Object getUser(Request request, Response response) { 
        response.type("application/json");
        response.status(200);
        return gson.toJson("returning user by id = "+request.params(":id"));
    }
    
    public Object addUser(Request request, Response response) {
        response.type("application/json");
        response.status(200);
        return gson.toJson("returning added user");
    }
    
    public Object updateUser(Request request, Response response) {
        response.type("application/json");
        response.status(200);
        return gson.toJson("returning updated user by id = "+request.params(":id"));
    }
    
    public Object deleteUser(Request request, Response response) {
        response.type("application/json");
        response.status(200);
        return gson.toJson("returning deleted user by id = "+request.params(":id"));
    }
     
     
}

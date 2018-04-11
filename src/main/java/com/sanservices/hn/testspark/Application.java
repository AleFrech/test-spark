package com.sanservices.hn.testspark;

import com.sanservices.hn.testspark.controllers.Controller;
import com.sanservices.hn.testspark.controllers.HelloController;
import com.sanservices.hn.testspark.controllers.LoginController;
import com.sanservices.hn.testspark.controllers.UserController;
import com.sanservices.hn.testspark.filters.AuthorizeFilter;
import com.sanservices.hn.testspark.handlers.ExceptionHandler;
import com.sanservices.hn.testspark.util.PropertyMap;
import spark.servlet.SparkApplication;

import java.util.ArrayList;
import java.util.List;
import spark.Spark;

public final class Application implements SparkApplication {

    private final List<Controller> controllers;
    private final ExceptionHandler handler;
    private final AuthorizeFilter authorize;
    public static  String environment = "dev";
    

    public Application(String env) {
        controllers = new ArrayList<>();
        controllers.add(new HelloController());
        controllers.add(new UserController());
        controllers.add(new LoginController());
        environment = env;
        authorize = new AuthorizeFilter();
        handler = new ExceptionHandler();
        
    }

    @Override
    public void init() {
        setupServer();
        startControllers();
        handler.init();
        authorize.init();
    }
    
    private void setupServer(){
        PropertyMap prop = PropertyMap.fromSource("server.properties");
        Spark.port(prop.getAsInt("port"));
    }

    private void startControllers() {
        for (Controller controller : controllers) {
            controller.init();
        }
    }
    
    public static void main(String[] args) {
        new Application(args[0]).init();
    }
    
}

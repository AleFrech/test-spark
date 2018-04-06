package com.sanservices.hn.testspark;

import com.sanservices.hn.testspark.controllers.Controller;
import com.sanservices.hn.testspark.controllers.HelloController;
import com.sanservices.hn.testspark.controllers.UserController;
import java.io.InputStream;
import spark.servlet.SparkApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import spark.Spark;

public final class Application implements SparkApplication {

    private final List<Controller> controllers;
    public static  String environment = "dev";

    public Application(String env) {
        controllers = new ArrayList<>();
        controllers.add(new HelloController());
        controllers.add(new UserController());
        environment = env;
    }

    @Override
    public void init() {
        setupServer();
        startControllers();
    }
    
    private void setupServer(){
        Properties prop = new Properties();
        try(InputStream input = getClass().getClassLoader().getResourceAsStream(environment+"/server.properties")){
            prop.load(input);
        }catch(Exception e){
            throw  new RuntimeException(e);
        }
        Spark.port(Integer.valueOf(prop.getProperty("port","5000")));
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

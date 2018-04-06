package com.sanservices.hn.testspark;

import com.sanservices.hn.testspark.controllers.Controller;
import com.sanservices.hn.testspark.controllers.HelloController;
import com.sanservices.hn.testspark.controllers.UserController;
import spark.servlet.SparkApplication;

import java.util.ArrayList;
import java.util.List;

public final class Application implements SparkApplication {

    private List<Controller> controllers;

    public Application() {
        controllers = new ArrayList<>();
        controllers.add(new HelloController());
        controllers.add(new UserController());
    }

    @Override
    public void init() {
        startControllers();
    }

    private void startControllers() {
        for (Controller controller : controllers) {
            controller.init();
        }
    }
    
    public static void main(String[] args) {
        new Application().init();
    }
    
}

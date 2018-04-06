package com.sanservices.hn.testspark.controllers;

import spark.Request;
import spark.Response;
import spark.Spark;

public final class HelloController implements Controller {

    public Object hello(Request request, Response response) {
        response.status(200);
        response.type("text/plain");
        return "Hello World";
    }

    @Override
    public void init() {
        Spark.path("/hello", () -> {
            Spark.get("/", this::hello);
        });
    }
}

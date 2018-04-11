/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanservices.hn.testspark.handlers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.gson.Gson;
import com.sanservices.hn.testspark.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

/**
 *
 * @author afrech
 */
public class ExceptionHandler {

    private final Logger errorLogger = LoggerFactory.getLogger("ERROR_LOGGER");
    private final Logger infoLogger = LoggerFactory.getLogger("INFO_LOGGER");
    JsonResult obj = new JsonResult();

    public void init() {
        Spark.exception(JWTVerificationException.class, (exception, request, response) -> {
            infoLogger.info(exception.getMessage());
            response.status(401);
            obj.message = "Unauthorized";
            response.body(new Gson().toJson(obj));
        });
        Spark.exception(Exception.class, (exception, request, response) -> {
            errorLogger.error(exception.getMessage(), exception);
            response.status(500);
            obj.message = "Internal Server Error";
            response.body(new Gson().toJson(obj));
        });
    }
}

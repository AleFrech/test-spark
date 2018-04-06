/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanservices.hn.testspark.handlers;

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
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    JsonResult obj = new JsonResult();
    
    public void init(){
        Spark.exception(Exception.class, (exception, request, response) -> {
             logger.error(exception.getMessage(), exception);
             response.status(500);
             obj.message = "Internal Server Error";
             response.body(new Gson().toJson(obj));
        });
    }
}

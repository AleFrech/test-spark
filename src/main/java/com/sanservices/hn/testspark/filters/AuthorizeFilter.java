/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanservices.hn.testspark.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sanservices.hn.testspark.dto.UserDto;
import com.sanservices.hn.testspark.util.PropertyMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import spark.Spark;

/**
 *
 * @author afrech
 */
public class AuthorizeFilter {

    private final List<String> unAuthorizedUris = Arrays.asList("/Login/");
    private final HashMap<String,List<String>> adminAuthorizedUris = new HashMap<>();
    
    public AuthorizeFilter(){
        adminAuthorizedUris.put("/Users/", Arrays.asList("POST","PUT","DELETE"));
    }

    public void init() {
        PropertyMap prop = PropertyMap.fromSource("server.properties");
        Spark.before("/*", (request, response) -> {
            request.requestMethod();
            if (!unAuthorizedUris.contains(request.uri())) {
                String bearerToken = request.headers("Authorization");
                if(bearerToken  == null || !bearerToken.contains("Bearer"))
                    throw new JWTVerificationException("Authorization Token not Found");
                String token =bearerToken.split(" ")[1];
                Algorithm algorithm = Algorithm.HMAC256(prop.get("jwtKey"));
                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer("auth0")
                        .build();
                DecodedJWT jwt = verifier.verify(token);
                Claim claim = jwt.getClaim("user");
                String txt = claim.asString();
                UserDto user = new GsonBuilder().setDateFormat("MMM d, yyyy").create().fromJson(txt, UserDto.class);
                
                if(adminAuthorizedUris.containsKey(request.uri())){
                    String method = request.requestMethod();
                    if(adminAuthorizedUris.get(request.uri()).contains(method) && user.getRoleId()!=1){
                        throw new JWTVerificationException("");
                    }
                }
            }
        });
    }
}

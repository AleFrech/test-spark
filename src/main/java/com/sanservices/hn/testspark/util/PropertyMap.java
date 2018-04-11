/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanservices.hn.testspark.util;

import static com.sanservices.hn.testspark.Application.environment;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author afrech
 */
public final class PropertyMap {
    private final String source;
    private final Properties properties;

    private PropertyMap(String source, Properties properties) {
        this.source = source;
        this.properties = properties;
    }

    public static PropertyMap fromSource(String url) {
        Properties properties;
        try (InputStream stream = PropertyMap.class.getClassLoader().getResourceAsStream(environment+"/"+url)) {
            properties = new Properties();
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new PropertyMap(url, properties);
    }
    
    public String get(String property) {
        return properties.getProperty(property);
    }
    
    public int getAsInt(String property) {
        return Integer.parseInt(properties.getProperty(property));
    }
}




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanservices.hn.testspark.util;

/**
 *
 * @author afrech
 */
public class NumberUtilities {

    public static Integer toIntOrNull(String string) {
        Integer parsedValue;
        try {
            parsedValue = Integer.valueOf(string);
        } catch (NumberFormatException nfe) {
            parsedValue = null;
        }
        return parsedValue;
    }
}

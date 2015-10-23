/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.moviemanager.utils;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author valentin
 */
public class Base64Coding {
    
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64URLSafeString(imageByteArray);
    }

    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }
    
    public static String encodeString(String str) {
        return Base64.encodeBase64URLSafeString(str.getBytes());
    }

    public static String decodeString(String str) {
        byte[] result = Base64.decodeBase64(str);
        return new String(result);
    }
}

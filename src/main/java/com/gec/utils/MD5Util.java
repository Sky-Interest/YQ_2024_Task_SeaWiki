package com.gec.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
public class MD5Util {
 
    public static String encryptMD5(String input) {
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Add input string to the digest
            md.update(input.getBytes());
            // Get the hash's bytes
            byte[] digest = md.digest();
            // Convert to hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            // Return hex string
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(encryptMD5("123"));//202cb962ac59075b964b07152d234b70
        System.out.println(encryptMD5("456"));//250cf8b51c773f3f8dc8b4be867a9a02
    }
}
package com.webapp.websiteportal.security;

import java.util.Base64;

public class TestBase64 {
    public static void main(String[] args) {
        String base64Key = "RWqClAmuv4MyWBWFGDjFVscLeVqaAf2IrPCzqOf/pW8="; // Your jwt.secret

        try {
            byte[] decodedKey = Base64.getDecoder().decode(base64Key);
            System.out.println("Decoded Key Length: " + decodedKey.length);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid Base64 Key: " + e.getMessage());
        }
    }
}

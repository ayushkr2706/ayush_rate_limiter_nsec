package com.ayush.rateLimiterApp.apiCredentialManagement.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;

public final class ApiKeyUtil {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String PREFIX = "rl_live_";

    private ApiKeyUtil(){
        // Private constructor prevents instances of this utility class from being created.
    }

    // Makes a new unpredictable random api key, like: rl_live_Xk3n...
    public static String generate(){

        // Creates an array capable of storing 32 bytes (256 bits) of data.
        //We use this because the api key should be unpredictable.
        byte[] bytes = new byte[32];

        // Fills all 32 bytes with cryptographically secure random data,
        // giving us 256 random bits.
        RANDOM.nextBytes(bytes);

        // Converts those 32 random bytes into a URL-safe Base64 string
        // and removes the '=' padding characters.
        return PREFIX + Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    //Turns a key into a 64-character fingerprint.
    //Same key in, same fingerprint out.
    public static String hash(String apiKey){

        try{
            //MesssageDigest is a class from the Java Cryptography Architecture.
            //Gives an object that can perform SHA-256 hashing.
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            //Converts the Java string into bytes and calculate their SHA-256 hash
            // and store in the byte array.
            byte[] hashed = digest.digest(apiKey.getBytes(StandardCharsets.UTF_8));

            //converts those bytes into hexadecimal characters.
            //Each byte become 2 hexadecimal characters.
            return HexFormat.of().formatHex(hashed);
        }
        catch(NoSuchAlgorithmException ex){
            throw new IllegalStateException("SHA-256 is not available", ex);
        }
    }
}

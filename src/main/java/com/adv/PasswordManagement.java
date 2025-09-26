package com.adv;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordManagement {

    private static final int SALT_BYTE_SIZE = 16;
    private static final String HASH_ALGORITHM = "SHA-512";

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        return salt;
    }

    private String hashPasswordSub(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(salt);
            md.update(password.getBytes(StandardCharsets.UTF_8));
            byte[] hashedPassword = md.digest();

            return Base64.getEncoder().encodeToString(hashedPassword);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Cannot find following HashAlgorithm: " + HASH_ALGORITHM);
        }

    }

    public String hashPassword(String password) {
        byte[] salt = generateSalt();
        String hashedPassword = hashPasswordSub(password, salt);
        String saltString = Base64.getEncoder().encodeToString(salt);
        return saltString + ":" + hashedPassword;
    }

    public boolean checkPassword(String password, String storedDbHash) {
        if (storedDbHash == null || !storedDbHash.contains(":")) {
            return false;
        }
        String[] parts = storedDbHash.split(":");
        if (parts.length != 2) {
            return false;
        }

        byte[] salt = Base64.getDecoder().decode(parts[0]);
        String hashOfAttempt = hashPasswordSub(password, salt);
        return hashOfAttempt.equals(parts[1]);
    }


//    public static void main(String[] args) {
//        PasswordManagement pw = new PasswordManagement();
//        String lol = pw.hashPassword("LOL");
//        System.out.println(lol);
//        System.out.println(pw.checkPassword("LOL", lol));
//    }


}

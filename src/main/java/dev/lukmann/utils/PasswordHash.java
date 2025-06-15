package dev.lukmann.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHash {

    public static String hashPassword(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt());
    }

    public static boolean checkPassword(String hashedPw, String plainPw) {
        return BCrypt.checkpw(plainPw, hashedPw);
    }

}

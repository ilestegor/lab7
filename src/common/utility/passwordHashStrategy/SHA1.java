package common.utility.passwordHashStrategy;

import common.exception.WrongArgumentException;
import server.MainServerApp;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1 implements Strategy {
    private final int HASHING_COUNT = 5;

    protected SHA1() {
    }

    public String hashPassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] pwd = new byte[password.length()];
            md.reset();
            for (int i = 0; i < HASHING_COUNT; i++) {
                pwd = md.digest(password.getBytes());
            }
            BigInteger bigInteger = new BigInteger(1, pwd);
            String hashPwd = bigInteger.toString(16);
            while (hashPwd.length() < 32) {
                hashPwd += "0";
            }
            return hashPwd;
        } catch (NoSuchAlgorithmException e) {
            MainServerApp.LOGGER.info("Алгоритм для хеширования не найден");
        }
        return null;
    }
}

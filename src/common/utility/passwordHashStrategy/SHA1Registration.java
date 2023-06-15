package common.utility.passwordHashStrategy;

import org.apache.commons.lang3.RandomStringUtils;

public class SHA1Registration implements Strategy {
    private static final String PEPPER = "9+MX=8h$j?";
    private static int SALT_GENERATED_COUNT = 0;
    private static final String salt = generateSalt();
    private static final SHA1 sha1 = new SHA1();

    private SHA1Registration() {
    }

    public static String hashPassword(String password) {
        String noHashPwd = PEPPER + password + salt;
        return sha1.hashPassword(noHashPwd);
    }

    public static String generateSalt() {
        if (SALT_GENERATED_COUNT == 0) {
            SALT_GENERATED_COUNT++;
            return RandomStringUtils.random(10, true, true);
        }
        return salt;
    }

    public static String getSalt() {
        return salt;
    }
}

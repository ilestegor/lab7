package common.utility.passwordHashStrategy;

public class SHA1Login implements Strategy {
    private static final String PEPPER = "9+MX=8h$j?";
    private static final SHA1 sha1 = new SHA1();


    public static String hashPassword(String password) {
        return PEPPER + password;
    }

    public static String hashPassword(String password, String salt) {
        String noHashPwd = password + salt;
        return sha1.hashPassword(noHashPwd);
    }
}

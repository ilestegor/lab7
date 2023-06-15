package common.utility.passwordHashStrategy;

public interface Strategy {
    static String hashPassword(String password) {
        return password;
    }
}

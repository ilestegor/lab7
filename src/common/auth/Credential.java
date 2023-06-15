package common.auth;

import java.io.Serializable;

public class Credential implements Serializable {
    private final String login;
    private final String password;
    private String salt;

    public Credential(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Credential(String login, String password, String salt) {
        this.login = login;
        this.password = password;
        this.salt = salt;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Credential that)) return false;

        if (!getLogin().equals(that.getLogin())) return false;
        return getPassword().equals(that.getPassword());
    }

    @Override
    public int hashCode() {
        int result = getLogin().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }
}

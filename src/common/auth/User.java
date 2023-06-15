package common.auth;

public class User {
    private int id;
    private Credential credential;

    public User(int id, Credential credential) {
        this.id = id;
        this.credential = credential;
    }

    public User(Credential credential) {
        this.credential = credential;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Credential getCredentials() {
        return credential;
    }

    public void setCredentials(Credential credential) {
        this.credential = credential;
    }
}

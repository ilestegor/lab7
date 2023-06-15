package common.network;

import common.auth.RegistrationCode;

import java.io.Serial;
import java.io.Serializable;

public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = -5841772410405190084L;
    private String message;
    private RegistrationCode registrationCode;

    public Response() {
    }

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public RegistrationCode getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

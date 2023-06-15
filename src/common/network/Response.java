package common.network;

import common.auth.RegistrationCode;

import java.io.Serial;
import java.io.Serializable;

public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = -5841772410405190084L;
    private String message;
    private RegistrationCode registrationCode;
    private boolean isSubResponse;
    private boolean doesExists;
    private String commandToExecute;

    public Response() {
    }

    public Response(String message) {
        this.message = message;
    }

    public Response(String message, boolean isSubResponse, boolean doesExists) {
        this.message = message;
        this.isSubResponse = isSubResponse;
        this.doesExists = doesExists;
    }

    public Response(boolean doesExists, String commandToExecute) {
        this.doesExists = doesExists;
        this.commandToExecute = commandToExecute;
    }

    public Response(String message, boolean doesExists, String commandToExecute) {
        this.message = message;
        this.doesExists = doesExists;
        this.commandToExecute = commandToExecute;
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

    public boolean isSubResponse() {
        return isSubResponse;
    }

    public boolean isDoesExists() {
        return doesExists;
    }

    public void setSubResponse(boolean subResponse) {
        isSubResponse = subResponse;
    }

    public void setDoesExists(boolean doesExists) {
        this.doesExists = doesExists;
    }

    public String getCommandToExecute() {
        return commandToExecute;
    }

    public void setCommandToExecute(String commandToExecute) {
        this.commandToExecute = commandToExecute;
    }
}

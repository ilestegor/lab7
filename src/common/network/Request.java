package common.network;


import common.auth.Credential;
import common.auth.RegistrationCode;

import java.io.Serial;
import java.io.Serializable;

public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = -9137511527076368943L;
    private CommandDTO commandDTO;
    private RequestBody requestBody;
    private RequestBodyMusicBand requestBodyMusicBand;
    private Credential credential;
    private RegistrationCode registrationCode;
    private boolean isSubRequest;

    public Request(CommandDTO commandDTO, RequestBody requestBody) {
        this.commandDTO = commandDTO;
        this.requestBody = requestBody;
    }

    public Request(CommandDTO commandDTO, RequestBody requestBody, boolean isSubRequest) {
        this.isSubRequest = isSubRequest;
    }

    public Request(CommandDTO commandDTO) {
        this.commandDTO = commandDTO;
    }

    public Request(CommandDTO commandDTO, RequestBodyMusicBand requestBodyMusicBand) {
        this.commandDTO = commandDTO;
        this.requestBodyMusicBand = requestBodyMusicBand;
    }

    public Request(CommandDTO commandDTO, RequestBody requestBody, RequestBodyMusicBand requestBodyMusicBand) {
        this.commandDTO = commandDTO;
        this.requestBody = requestBody;
        this.requestBodyMusicBand = requestBodyMusicBand;
    }

    public Request() {
    }

    public CommandDTO getCommandDTO() {
        return commandDTO;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public RequestBodyMusicBand getRequestBodyMusicBand() {
        return requestBodyMusicBand;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public RegistrationCode getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public void setRequestBodyMusicBand(RequestBodyMusicBand requestBodyMusicBand) {
        this.requestBodyMusicBand = requestBodyMusicBand;
    }

    public void setCommandDTO(CommandDTO commandDTO) {
        this.commandDTO = commandDTO;
    }

    public boolean isSubRequest() {
        return isSubRequest;
    }
}

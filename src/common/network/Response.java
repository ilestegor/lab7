package common.network;

import java.io.Serial;
import java.io.Serializable;

public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = -5841772410405190084L;
    private String message;

    public Response() {
    }

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

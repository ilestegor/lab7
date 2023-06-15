package common.network;

import java.io.Serial;
import java.io.Serializable;

public class ResponseFactory implements Serializable {
    @Serial
    private static final long serialVersionUID = -9196281319649423518L;

    public Response createResponse(String message) {
        return new Response(message);
    }

    public Response createSubResponse(String message, boolean isSubResponse, boolean doesExists) {
        return new Response(message, isSubResponse, doesExists);
    }

    public Response createResponseWithNoMessage(boolean doesExists, String commandToExecute) {
        return new Response(doesExists, commandToExecute);
    }

    public Response createResponseWithMessage(String message, boolean doesExists, String commandToExecute) {
        return new Response(message, doesExists, commandToExecute);
    }
}

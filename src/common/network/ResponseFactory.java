package common.network;

import java.io.Serial;
import java.io.Serializable;

public class ResponseFactory implements Serializable {
    @Serial
    private static final long serialVersionUID = -9196281319649423518L;
    public Response createResponse(String message) {
        return new Response(message);
    }
}

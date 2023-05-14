package common.network;

import java.io.Serial;
import java.io.Serializable;

public class RequestBody implements Serializable {
    @Serial
    private static final long serialVersionUID = 5683996701532927341L;
    private final String[] args;

    public RequestBody(String[] args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }
}

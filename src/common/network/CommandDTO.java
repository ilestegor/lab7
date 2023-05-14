package common.network;

import java.io.Serial;
import java.io.Serializable;

public class CommandDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -4867721242661179912L;
    private final String commandName;

    public CommandDTO(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}

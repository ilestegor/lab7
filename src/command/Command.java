package command;

public abstract class Command implements CommandInterface {
    private String name;
    private String description;
    private boolean hasArgs;
    private Object args;

    public Command(String name, String description, boolean hasArgs) {
        this.name = name;
        this.description = description;
        this.hasArgs = hasArgs;
    }

    @Override
    public abstract void execute();

    @Override
    public abstract boolean checkArgument(Object inputArgs);

    public boolean isHasArgs() {
        return hasArgs;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Object getArgs() {
        return args;
    }

    public void setArgs(Object args) {
        this.args = args;
    }
}

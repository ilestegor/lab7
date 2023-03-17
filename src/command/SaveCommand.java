package command;

import parse.YamlWriter;

/**
 * Class contains implementation of save command
 * Saves collection to file
 *
 * @author ilestegor
 */
public class SaveCommand extends Command {
    public SaveCommand(String description, boolean hasArgs) {
        super(description, hasArgs);
    }

    @Override
    public void execute() {
        if (checkArgument(getArgs())) {
            YamlWriter yamlWriter = new YamlWriter();
            yamlWriter.write("data/file.yaml");
            System.out.println("Коллекция успешно сохранена!");
        }

    }

    @Override
    public boolean checkArgument(Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            System.out.println("У команды save нет аргументов! Попробуйте еще раз");
            return false;
        }
    }
}

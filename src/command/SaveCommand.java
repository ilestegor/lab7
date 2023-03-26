package command;

import manager.CollectionManager;
import parse.YamlWriter;
import utility.Printer;

/**
 * Class contains implementation of save command
 * Saves collection to file
 *
 * @author ilestegor
 */
public class SaveCommand extends Command {
    public SaveCommand(CollectionManager collectionManager) {
        super("Команда сохраняет коолекцию в файл", collectionManager);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            YamlWriter yamlWriter = new YamlWriter(new Printer(), getMusicBandCollectionManager());
            yamlWriter.write("/Users/ilestegor/Desktop/Универ/1курс/2сем/прога/student-6/data/file.yaml");
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.printNextLine("У команды save нет аргументов! Попробуйте еще раз");
            return false;
        }
    }
}

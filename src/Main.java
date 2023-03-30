import manager.CollectionManager;
import manager.CommandManager;
import manager.UserManager;
import parse.YamlReader;
import utility.Printer;

import javax.xml.stream.XMLStreamWriter;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Printer printer = new Printer();
        printer.printNextLine("---------- " + LocalDateTime.now().toString().substring(0, 10) + " ---------");
        CollectionManager collectionManager = new CollectionManager();
        YamlReader yamlReader = new YamlReader(new Printer());
        collectionManager.readToCollection(yamlReader);
        CommandManager commandManager = new CommandManager(collectionManager);
        while (UserManager.isIsInWork()) {
            UserManager.requestCommand();
        }
    }

}
import manager.CollectionManager;
import manager.CommandManager;
import manager.UserManager;
import model.Coordinates;
import model.Label;
import model.MusicBand;
import model.MusicGenre;
import parse.YamlReader;
import utility.Printer;

import javax.xml.stream.XMLStreamWriter;
import java.time.LocalDateTime;
import java.util.Date;

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

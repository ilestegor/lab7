import manager.MusicBandCollection;
import manager.UserManager;
import model.MusicBandClone;
import parse.YamlReader;
import utility.Printer;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main {
    public static final String ENV_KEY = System.getenv("YamlFile");

    public static void main(String[] args) throws IOException {
        System.out.println("---------- " + LocalDateTime.now().toString().substring(0, 10) + " ---------");
        YamlReader yamlReader = new YamlReader(new Printer());
        MusicBandCollection musicBandCollection = new MusicBandCollection(yamlReader.read(ENV_KEY), ENV_KEY);
        while (UserManager.isIsInWork()) {
            UserManager.requestCommand();
        }
    }
}
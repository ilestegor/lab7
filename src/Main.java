import manager.MusicBandCollection;
import manager.UserManager;
import parse.YamlReader;

import java.io.IOException;

public class Main {
    public static final String ENV_KEY = System.getenv("YamlFile");

    public static void main(String[] args) throws IOException {
        YamlReader yamlReader = new YamlReader();
        MusicBandCollection musicBandCollection = new MusicBandCollection(yamlReader.read(ENV_KEY), ENV_KEY);
        while (UserManager.isIsInWork()) {
            UserManager.requestCommand();
        }
    }
}

import manager.MusicBandCollection;
import parse.YamlReader;

import java.io.IOException;
import java.time.LocalDateTime;
import manager.UserManager;

public class Main {
    public static final String ENV_KEY = "data/file.yaml";
    public static void main(String[] args) throws IOException {
        YamlReader yamlReader = new YamlReader();
        MusicBandCollection musicBandCollection = new MusicBandCollection(yamlReader.read(ENV_KEY), ENV_KEY);

        while (UserManager.isIsInWork()){
            UserManager.requestCommand();
        }
    }
}
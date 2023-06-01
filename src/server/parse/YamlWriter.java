package server.parse;

import common.interfaces.BaseWriter;
import common.manager.ServerCollectionManager;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import server.MainServerApp;
import server.model.MusicBand;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Parser for writing collection to file in Yaml
 *
 * @author ilestegor
 */
public class YamlWriter implements BaseWriter {
    private final ServerCollectionManager serverCollectionManager;

    public YamlWriter(ServerCollectionManager serverCollectionManager) {
        this.serverCollectionManager = serverCollectionManager;
    }

    @Override
    public void write(String path) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        if (path == null || !new File(path).exists()) {
            MainServerApp.LOGGER.warning("Файла, который задан в переменной окружения не существует. Коллекция не сохранена в файл!");
            return;
        }
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path))) {
            yaml.dump(new CloneParser().parseToClone(serverCollectionManager.getMusicBandLinkedList().toArray(new MusicBand[0])), writer);
            writer.flush();
            MainServerApp.LOGGER.info("Коллекция успешно сохранена");
        } catch (IOException e) {
            MainServerApp.LOGGER.warning("Отсутсвуют права на файл! Коллекция не сохранена в файл");
        }
    }
}

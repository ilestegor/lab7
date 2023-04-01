package parse;

import interfaces.BaseWriter;
import manager.CollectionManager;
import model.MusicBand;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import utility.Printer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Parser for writing collection to file in Yaml
 *
 * @author ilestegor
 */
public class YamlWriter implements BaseWriter {
    private final Printer printer;
    private final CollectionManager collectionManager;

    public YamlWriter(Printer printer, CollectionManager collectionManager) {
        this.printer = printer;
        this.collectionManager = collectionManager;
    }

    @Override
    public void write(String path) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path))) {
            yaml.dump(new CloneParser().parseToClone(collectionManager.getMusicBandLinkedList().toArray(new MusicBand[0])), writer);
            writer.flush();
            printer.printNextLine("Коллекция успешно сохранена");
        } catch (IOException e) {
            printer.printNextLine("Отсутсвуют права на файл! Коллекция не сохранена в файл");
        }
    }
}

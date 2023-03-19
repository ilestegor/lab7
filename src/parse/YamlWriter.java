package parse;

import interfaces.BaseWriter;
import manager.MusicBandCollection;
import model.MusicBand;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

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
    @Override
    public void write(String path) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path))) {
            yaml.dump(new CloneParser().parseToClone(MusicBandCollection.getMusicBandLinkedList().toArray(new MusicBand[0])), writer);
            writer.flush();
        } catch (FileNotFoundException ex) {
            System.out.println("Файл не найден!");
        } catch (IOException e) {
            System.out.println("Что-то пошло не так!");
        }
    }
}

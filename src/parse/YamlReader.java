package parse;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import interfaces.BaseReader;
import model.MusicBand;
import utility.Printer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Parser that reads Yaml from file, adding converted object to collection
 *
 * @author ilestegor
 */
public class YamlReader implements BaseReader {
    MusicBand[] musicBands;
    private final Printer printer;

    public YamlReader(Printer printer) {
        this.printer = printer;
    }

    @Override
    public MusicBand[] read(String path) throws FileNotFoundException, ClassCastException {
        CloneParser cloneParser = new CloneParser();
        YAMLMapper yamlMapper = new YAMLMapper(new YAMLFactory());
        yamlMapper.registerModule(new JavaTimeModule());
        try {
            InputStreamReader input = new InputStreamReader(new FileInputStream(path));
            musicBands = yamlMapper.readValue(input, MusicBand[].class);
        } catch (IOException ex) {
            printer.printNextLine("Файл не найден или файл пустой или поля у объектов неверны. Проверьте файл и валидность данных!");
        } catch (ClassCastException ex) {
            printer.printNextLine("Тип введенного поля не соответсвует требуемому типу! Попробуйте еще раз");
        }
        return musicBands;
    }

    public MusicBand[] getMusicBands() {
        return musicBands;
    }
}

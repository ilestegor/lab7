package server.parse;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import common.interfaces.BaseReader;
import common.utility.Printer;
import server.MainServerApp;
import server.model.MusicBand;

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
        YAMLMapper yamlMapper = new YAMLMapper(new YAMLFactory());
        yamlMapper.registerModule(new JavaTimeModule());
        try {
            InputStreamReader input = new InputStreamReader(new FileInputStream(path));
            musicBands = yamlMapper.readValue(input, MusicBand[].class);
            MainServerApp.LOGGER.info("Коллекция загружена из файла");
        } catch (FileNotFoundException ex) {
            MainServerApp.LOGGER.warning("Файл не найден или отсутствуют права на файл!");
        } catch (IOException ex) {
            MainServerApp.LOGGER.warning("Файл пустой или поля у объектов невалидны. Проверьте файл и валидность данных!");
        } catch (ClassCastException ex) {
            MainServerApp.LOGGER.warning("Тип введенного поля не соответствует требуемому типу! Попробуйте еще раз");
        }
        return musicBands;
    }
}

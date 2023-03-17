package parse;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import interfaces.BaseReader;
import manager.MusicBandCollection;
import model.MusicBand;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

/**
 * Parser that reads Yaml from file, adding converted object to collection
 * @author ilestegor
 */
public class YamlReader implements BaseReader {
    MusicBand[] musicBands;

    @Override
    public MusicBand[] read(String path) throws FileNotFoundException, ClassCastException {

        YAMLMapper yamlMapper = new YAMLMapper(new YAMLFactory());
        yamlMapper.registerModule(new JavaTimeModule());
        try {
            InputStreamReader input = new InputStreamReader(new FileInputStream(path));
            musicBands = yamlMapper.readValue(input, MusicBand[].class);

            for (MusicBand x : musicBands) {
                MusicBandCollection.validateAndAddToCollection(x);
            }

        } catch (IOException ex) {
            System.out.println("Файл не найден или файл пустой");
        } catch (ClassCastException ex) {
            System.err.println("Тип введенного поля не соответсвует требуемому типу! Попробуйте еще раз");
        }
        System.out.println("---------- " + LocalDateTime.now().toString().substring(0, 10) + " ---------");
        return musicBands;
    }
}

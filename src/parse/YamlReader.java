package parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import manager.MusicBandCollection;
import model.MusicBand;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

public class YamlReader implements BaseReader {
    MusicBand[] musicBands;
    LinkedHashMap<String, Object> musicBandValue = new LinkedHashMap<>();

    @Override
    public MusicBand[] read(String path) throws FileNotFoundException, ClassCastException{

        YAMLMapper yamlMapper = new YAMLMapper(new YAMLFactory());
        yamlMapper.registerModule(new JavaTimeModule());
        try {
            InputStream input = new FileInputStream(path);
            musicBands = yamlMapper.readValue(input, MusicBand[].class);

            for (MusicBand x : musicBands){
               MusicBandCollection.validateAndAddToCollection(x);
            }

        } catch (IOException ex){
            System.out.println("Файл не найден! Укажите правильный путь файла в переменной окружения ENV_KEY");
        } catch (ClassCastException ex){
            System.err.println("Тип введенного поля не соответсвует требуемому типу! Попробуйте еще раз");
        }
        System.out.println("---------- " + LocalDateTime.now().toString().substring(0, 10) + " ---------");
        return musicBands;
    }





}

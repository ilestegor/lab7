package parse;

import model.MusicBand;

import java.io.IOException;

public interface BaseReader {
    MusicBand[] read(String path) throws IOException;
}

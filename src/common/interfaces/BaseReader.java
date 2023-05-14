package common.interfaces;

import server.model.MusicBand;

import java.io.IOException;

/**
 * Contains method for reading file
 *
 * @author ilestegor
 */
public interface BaseReader {
    /**
     * Loads data from file
     *
     * @param path
     * @return
     * @throws IOException
     */
    MusicBand[] read(String path) throws IOException;
}

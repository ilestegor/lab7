package common.interfaces;

import java.io.FileNotFoundException;

/**
 * Contains method for writing collection to file
 *
 * @author ilestegor
 */
public interface BaseWriter {
    /**
     * Writes to file
     *
     * @param path
     * @throws FileNotFoundException
     */
    void write(String path) throws FileNotFoundException;
}

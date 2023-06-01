package server.parse;

import server.model.MusicBand;
import server.model.MusicBandClone;

/**
 * Parser for making clone of MusicGenre
 *
 * @author ilestegor
 */
public class CloneParser {
    /**
     * Parsing MusicGenre object to its clone
     *
     * @param musicBands
     * @return Array of musicBand values
     */
    public MusicBandClone[] parseToClone(MusicBand[] musicBands) {
        MusicBandClone[] musicBandClones = new MusicBandClone[musicBands.length];
        for (int i = 0; i < musicBands.length; i++) {
            musicBandClones[i] = new MusicBandClone(musicBands[i]);
        }
        return musicBandClones;
    }
}

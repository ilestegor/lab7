package common.utility;

import server.model.MusicBand;

import java.util.Comparator;

/**
 * Class for sorting collection by id, overriding method compare
 *
 * @author ilestegor
 */
public class SortByName implements Comparator<MusicBand> {

    @Override
    public int compare(MusicBand o1, MusicBand o2) {
        return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
    }
}

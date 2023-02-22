package utility;

import model.MusicBand;

import java.util.Comparator;

public class SortById implements Comparator<MusicBand> {
    @Override
    public int compare(MusicBand o1, MusicBand o2) {
        if (o1.getId() > o1.getId()){
            return 1;
        } else if (o1.getId() == o1.getId()){
            return 0;
        } else {
            return -1;
        }
    }
}

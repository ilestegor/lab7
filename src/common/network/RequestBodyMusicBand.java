package common.network;

import server.model.MusicBand;

import java.io.Serial;
import java.io.Serializable;

public class RequestBodyMusicBand implements Serializable {
    @Serial
    private static final long serialVersionUID = 5600571130977470656L;
    private final MusicBand musicBand;

    public RequestBodyMusicBand(MusicBand musicBand) {
        this.musicBand = musicBand;
    }

    public MusicBand getMusicBand() {
        return musicBand;
    }
}

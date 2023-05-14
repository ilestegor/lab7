package common.network;

import server.model.MusicBand;

import java.io.Serial;
import java.io.Serializable;

public class RequestBodyFactory implements Serializable {
    @Serial
    private static final long serialVersionUID = -6294964751654813239L;
    public static RequestBody createRequestBody(String[] args) {
        return new RequestBody(args);
    }

    public static RequestBodyMusicBand createRequestBodyMusicBand(MusicBand musicBand) {
        return new RequestBodyMusicBand(musicBand);
    }
}

package common.interfaces;

import server.model.MusicBand;

/**
 * Contains methods fro building MusicBand
 *
 * @author ilestegor
 */
public interface IMusicBandBuilder {
    IMusicBandBuilder buildId();

    IMusicBandBuilder buildName();

    IMusicBandBuilder buildCoordinates();

    IMusicBandBuilder buildCreationDate();

    IMusicBandBuilder buildNumberOfParticipants();

    IMusicBandBuilder buildAlbumsCount();

    IMusicBandBuilder buildEstablishmentDate();

    IMusicBandBuilder buildMusicGenre();

    IMusicBandBuilder buildLabel();

    MusicBand build();

}

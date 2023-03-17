package model;

/**
 * Contains enums MusicGenre
 *
 * @author ilestegor
 */
public enum MusicGenre {
    ROCK("1"),
    PROGRESSIVE_ROCK("2"),
    PSYCHEDELIC_CLOUD_RAP("3"),
    SOUL("4"),
    PUNK_ROCK("5");

    public final String s;

    MusicGenre(String s) {
        this.s = s;
    }

    /**
     * Method for checking values of enum when add command is running
     *
     * @param label
     * @return MusicGenre object
     */
    public static MusicGenre valueOfLabel(String label) {
        for (MusicGenre m : values()) {
            if (m.s.equals(label)) {
                return m;
            }
        }
        return null;
    }
}

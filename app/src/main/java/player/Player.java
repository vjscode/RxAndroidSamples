package player;

/**
 * Created by vijay on 5/8/16.
 */
public interface Player {

    public static final int PLAY = 1;
    public static final int PAUSE = 0;
    void setPlayerEventListener(PlayerEventListner listener);
    void play();
    void pause();
}

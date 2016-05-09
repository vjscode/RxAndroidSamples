package player;

/**
 * Created by vijay on 5/8/16.
 */
public class NumberPlayer implements Player {

    private PlayerEventListner eventListener;
    private long elapsedTime = 0;
    private long currentTime = 0;

    @Override
    public void setPlayerEventListener(PlayerEventListner listener) {
        this.eventListener = listener;
    }

    @Override
    public void play() {
        eventListener.onPlay();
    }

    @Override
    public void pause() {
        eventListener.onPaused();
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }

    public void updateElapsedTime() {
        this.elapsedTime = currentTime;
    }
}

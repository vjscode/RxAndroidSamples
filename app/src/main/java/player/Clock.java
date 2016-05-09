package player;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.observables.ConnectableObservable;

/**
 * Created by vijay on 5/8/16.
 */
public class Clock {

    public ConnectableObservable<Long> getConnectableObservable() {
        return Observable.interval(1, TimeUnit.SECONDS).publish();
    }
}

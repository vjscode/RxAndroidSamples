package rx.rxandroidsamples;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by vijay on 4/10/16.
 */
public class Mapper {
    public static final String TAG = "maptest";
    public static void emit(String input) {
        Observable.create(subscriber -> {
                subscriber.onNext(input);
        }).map(s -> {
                return "Echo string -> " + s;
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Mapped string: " + s);
            }
        });
    }
}

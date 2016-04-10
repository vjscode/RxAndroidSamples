package rx.rxandroidsamples;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by vijay on 4/10/16.
 */
public class DebounceEmitter {
    private static final String TAG = "debouncetest";
    public static Observable<Integer> emit() {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            arr.add(i);
        }
        return Observable.from(arr);
    }

    public static void debouncedEmitter() {
        emit().debounce(10, TimeUnit.MICROSECONDS)
                .subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "COMPLETED");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);
            }
        });
    }
}
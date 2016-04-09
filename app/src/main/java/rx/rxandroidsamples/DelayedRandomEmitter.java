package rx.rxandroidsamples;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func0;

/**
 * Created by vijay on 4/9/16.
 */
public class DelayedRandomEmitter {
    public static Observable<String> emit() {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                String[] num = {"one", "two",
                        "three", "four", "five"};
                    return Observable.from(num).delay(12, TimeUnit.SECONDS);
            }
        });
    }
}

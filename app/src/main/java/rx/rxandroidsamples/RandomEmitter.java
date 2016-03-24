package rx.rxandroidsamples;

import rx.Observable;
import rx.functions.Func0;

/**
 * Created by vijay on 3/24/16.
 */
public class RandomEmitter {
    public static Observable<String> emit() {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                String[] num = {"one", "two",
                        "three", "four", "five"};
                //for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(2000);
                        return Observable.from(num);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                //}
                return null;
            }
        });
    }
}

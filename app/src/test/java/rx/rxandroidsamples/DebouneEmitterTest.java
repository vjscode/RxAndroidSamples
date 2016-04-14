package rx.rxandroidsamples;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.observers.TestSubscriber;

/**
 * Created by vijay on 4/14/16.
 */
public class DebouneEmitterTest {
    @Test
    public void testDebounceEmitter() {
        Observable<Integer> debounceObservable = DebounceEmitter.emit();
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        debounceObservable.debounce(2, TimeUnit.SECONDS)
                .subscribe(testSubscriber);
        List<Integer> nextElements = testSubscriber.getOnNextEvents();
        Assert.assertEquals(1, nextElements.size());
    }
}

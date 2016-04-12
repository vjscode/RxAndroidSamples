package rx.rxandroidsamples;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

/**
 * Created by vijay on 4/11/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class EmitterTest {
    @Test
    public void testRandomEmitter() {
        Observable<String> randomEmitterObservable = RandomEmitter.emit();
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        randomEmitterObservable.subscribe(testSubscriber);

        testSubscriber.assertNoErrors();

        String[] expectedStrings = {"one", "two",
                "three", "four", "five"};

        List<String> onNextStrings = testSubscriber.getOnNextEvents();
        for (int i = 0; i < expectedStrings.length; i++) {
            Assert.assertEquals(expectedStrings[i], onNextStrings.get(i));
        }
    }
}

package rx.rxandroidsamples;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.widget.Button;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.robolectric.Shadows.shadowOf;

/**
 * Created by vijay on 4/11/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {

    private Context mRealContext;

    @Before
    public void setUp() {
        //no set up yet
        mRealContext = RuntimeEnvironment.application;
    }

    @Test
    public void testInitialUI() {
        Activity activity = Robolectric.buildActivity(MainActivity.class)
                .create().get();
        FloatingActionButton fab = (FloatingActionButton) shadowOf(activity).findViewById(R.id.fab);
        Assert.assertNotNull(fab);

        Button debounceButton = (Button) shadowOf(activity).findViewById(R.id.debounce);
        Assert.assertEquals(debounceButton.getText(), mRealContext.getString(R.string.debounce));
    }
}

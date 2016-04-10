package rx.rxandroidsamples;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.concurrent.ThreadLocalRandom;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtLogs;
    private Subscription stringEmitterSubscription;
    private Subscription jsonSubscription;
    private Button multiRequest;
    private Button multiRequestAndReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLogs = (TextView) findViewById(R.id.txtLogs);
        multiRequest = (Button) findViewById(R.id.multirequest);
        multiRequest.setOnClickListener(this);
        multiRequestAndReport = (Button) findViewById(R.id.multirequestAndReport);
        multiRequestAndReport.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> updateJSON(view));
        subscribeToEmitter();
    }

    private void updateJSON(View v) {
        jsonSubscription = JSONPlaceHolderEmitter.emit(ThreadLocalRandom.current().nextInt(1, 500))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        runOnUiThread(() -> txtLogs.setText("" + jsonObject));
                    }
                });
    }

    private void multiRequest() {
        jsonSubscription = MultipleRequestEmitter.emit()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer i) {
                        runOnUiThread(() -> txtLogs.setText("" + i));
                    }
                });
    }

    private void emitAndReportCount() {
        jsonSubscription = MultipleRequestEmitter.emitAndReportCount()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.d("test", "MainActivity request completed");
                        Toast.makeText(MainActivity.this, "All requests completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer i) {
                        runOnUiThread(() -> txtLogs.setText("" + i));
                    }
                });
    }

    private void subscribeToEmitter() {
        stringEmitterSubscription = DelayedRandomEmitter.emit()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        txtLogs.setText(txtLogs.getText() + "\n" + "END");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        txtLogs.setText(txtLogs.getText() + "\n" + s);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (stringEmitterSubscription != null) {
            stringEmitterSubscription.unsubscribe();
        }
        if (jsonSubscription != null) {
            jsonSubscription.unsubscribe();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.multirequest) {
            multiRequest();
        } else if (v.getId() == R.id.multirequestAndReport) {
            emitAndReportCount();
        }
    }
}

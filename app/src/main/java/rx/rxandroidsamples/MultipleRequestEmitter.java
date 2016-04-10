package rx.rxandroidsamples;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by vijay on 4/9/16.
 */
public class MultipleRequestEmitter {
    static OkHttpClient client = new OkHttpClient();
    public static Observable<Integer> emit() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 1; i < 5; i++) {
                    Log.d("test", "i: " + i);
                    Request r = getRequest(i);
                    client.newCall(r).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String res = response.body().string();
                                Log.d("test", "res: " + res);
                            }
                    });
                }
            }
        });
    }

    private static Request getRequest(int num) {
        Request request = new Request.Builder()
                .url("https://httpbin.org/stream/" + num)
                .build();
        return request;
    }
}

package rx.rxandroidsamples;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by vijay on 3/24/16.
 */
public class JSONPlaceHolderEmitter {

    static OkHttpClient client = new OkHttpClient();
    public static Observable<JSONObject> emit(final int id) {
        return Observable.create(new Observable.OnSubscribe<JSONObject>() {
            @Override
            public void call(Subscriber<? super JSONObject> subscriber) {
                JSONObject result = getPhotoJSONObject(id);
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        });
    }

    @Nullable
    private static JSONObject getPhotoJSONObject(int id) {
        Request request = new Request.Builder()
                .url("http://jsonplaceholder.typicode.com/photos/" + id)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            return new JSONObject(res);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

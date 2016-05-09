package player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.observables.ConnectableObservable;
import rx.rxandroidsamples.R;

/**
 * Created by vijay on 5/8/16.
 */
public class PlayerActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @BindView(R.id.control)
    Button btnPlayerControl;

    @BindView(R.id.playerTime)
    TextView tvPlayerTime;

    private NumberPlayer player;
    private Clock clock;
    private Subscription clockSubscription;

    private ConnectableObservable<Long> clockObservable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_layout);
        unbinder = ButterKnife.bind(this);
        player = new NumberPlayer();
        clock = new Clock();
        setUpClock();
        setUpPlayer();
    }

    @OnClick(R.id.control)
    public void handlePlayerControl() {
        if (btnPlayerControl.getText().equals("Play")) {
            player.play();
        } else {
            player.pause();
        }
    }

    private void setUpClock() {
        clockObservable = clock.getConnectableObservable();
        clockObservable.map((i) -> player.getElapsedTime() + i)
        .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                Log.d("test", "completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("test", "error: " + e);
            }

            @Override
            public void onNext(Long aLong) {
                player.setCurrentTime(aLong);
                tvPlayerTime.setText(aLong + "");
            }
        });
    }

    private void setUpPlayer() {
        ConnectableObservable<Integer> observablePlayer = Observable.create(new PlayerEventOnSubscribe()).publish();
        observablePlayer.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.d("test", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("test", "onError: " + e);
            }

            @Override
            public void onNext(Integer integer) {
                if (integer == Player.PLAY) {
                    if (clockSubscription != null && clockSubscription.isUnsubscribed()) {
                        setUpClock();
                    }
                    clockSubscription = clockObservable.connect();
                } else {
                    player.updateElapsedTime();
                    clockSubscription.unsubscribe();
                }
            }
        });
        observablePlayer.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    class PlayerEventOnSubscribe implements Observable.OnSubscribe<Integer> {
        @Override
        public void call(Subscriber<? super Integer> subscriber) {
            PlayerEventListner eventListner = new PlayerEventListner() {
                @Override
                public void onPlay() {
                    btnPlayerControl.setText("Pause");
                    subscriber.onNext(1);
                }

                @Override
                public void onPaused() {
                    btnPlayerControl.setText("Play");
                    subscriber.onNext(0);
                }

                @Override
                public void onComplete() {
                    btnPlayerControl.setText("Play");
                    subscriber.onNext(2);
                }
            };
            player.setPlayerEventListener(eventListner);
        }
    }
}

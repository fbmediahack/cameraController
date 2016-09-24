package com.example.camera;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.camera.Network.RestClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.photo)
    WebView photo;
    String photo_url = "http://192.168.0.136:88/cgi-bin/CGIProxy.fcgi?usr=user5&pwd=media5&cmd=snapPicture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timber.plant(new Timber.DebugTree());

        ButterKnife.bind(this);

        photo.loadUrl(photo_url);
        photo.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        photo.getSettings().setLoadWithOverviewMode(true);
        photo.getSettings().setUseWideViewPort(true);

    }

    private void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void onNext(ResponseBody responseBody) {
        Timber.d("OnNext");
    }

    private void onCompleted() {
        Timber.d("Completed");
        photo.loadUrl(photo_url);
    }

    @OnClick(R.id.up)
    public void onUp() {
        onCmd("ptzMoveUp");
    }

    @OnClick(R.id.down)
    public void onDown() {
        onCmd("ptzMoveDown");
    }

    @OnClick(R.id.left)
    public void onLeft() {
        onCmd("ptzMoveLeft");
    }

    @OnClick(R.id.right)
    public void onRight() {
        onCmd("ptzMoveRight");
    }

    @OnClick(R.id.stop)
    public void onStop() {
        onCmd("ptzStopRun");
    }

    private void onCmd(String cmd) {
        RestClient.getRequestService(
                RestClient.callUrl("http://192.168.0.136:88/cgi-bin/"))
                .request("user5", "media5", cmd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNext, this::onError, this::onCompleted);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            onCmd("ptzMoveDown");
        } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            onCmd("ptzMoveUp");
        }

        return true;
    }
}

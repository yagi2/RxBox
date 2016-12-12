package com.yagi2.rxbox.sample.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dropbox.core.android.Auth;
import com.dropbox.core.v2.users.FullAccount;
import com.yagi2.rxbox.RxBox;
import com.yagi2.rxbox.sample.R;
import com.yagi2.rxbox.sample.util.ClientFactory;
import com.yagi2.rxbox.sample.util.Observables;

import rx.Observable;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    @Override
    protected void loadData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        if (hasToken()) {
            Observables.usingProgressDialog(this)
                    .flatMap(new Func1<Void, Observable<FullAccount>>() {
                        @Override
                        public Observable<FullAccount> call(Void aVoid) {
                            return RxBox.getCurrentAccount(ClientFactory.getClient())
                                    .subscribeOn(Schedulers.io());
                        }
                    })
                    .toSingle()
                    .compose(bindToLifecycle().<FullAccount>forSingle())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleSubscriber<FullAccount>() {
                        @Override
                        public void onSuccess(FullAccount value) {
                            progressDialog.dismiss();
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), FileBrowseActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                        @Override
                        public void onError(Throwable error) {
                            findViewById(R.id.login_button).setVisibility(View.VISIBLE);
                        }
                    });
        } else {
            progressDialog.dismiss();
            findViewById(R.id.login_button).setVisibility(View.VISIBLE);
        }
    }

    private void initialize() {
        Button loginButton = (Button)findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.startOAuth2Authentication(MainActivity.this, getString(R.string.APP_KEY));
            }
        });
    }
}

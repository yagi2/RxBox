package com.yagi2.rxbox.sample.util;

import android.app.ProgressDialog;
import android.content.Context;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

public class Observables {
    public static Observable<Void> usingProgressDialog(final Context context) {
        return Observable.using(
                new Func0<ProgressDialog>() {
                    @Override
                    public ProgressDialog call() {
                        ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();
                        return progressDialog;
                    }
                },
                new Func1<ProgressDialog, Observable<? extends Void>>() {
                    @Override
                    public Observable<? extends Void> call(ProgressDialog progressDialog) {
                        return Observable.just(null);
                    }
                },
                new Action1<ProgressDialog>() {
                    @Override
                    public void call(ProgressDialog progressDialog) {
                        progressDialog.dismiss();
                    }
                });
    }
}

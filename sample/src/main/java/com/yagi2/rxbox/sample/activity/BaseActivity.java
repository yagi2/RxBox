package com.yagi2.rxbox.sample.activity;

import android.content.SharedPreferences;

import com.dropbox.core.android.Auth;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.yagi2.rxbox.sample.util.ClientFactory;

public abstract class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("rxbox", MODE_PRIVATE);
        String accessToken = prefs.getString("access-token", null);

        if (accessToken == null) {
            accessToken = Auth.getOAuth2Token();

            if (accessToken != null) {
                prefs.edit().putString("access-token", accessToken).apply();
                initAndLoadData(accessToken);
            }
        }
        else {
            initAndLoadData(accessToken);
        }
    }

    private void initAndLoadData(String accessToken) {
        ClientFactory.init(accessToken);
        loadData();
    }

    protected abstract void loadData();

    public boolean hasToken() {
        SharedPreferences prefs = getSharedPreferences("rxbox", MODE_PRIVATE);
        String accessToken = prefs.getString("access-token", null);

        return accessToken != null;
    }
}

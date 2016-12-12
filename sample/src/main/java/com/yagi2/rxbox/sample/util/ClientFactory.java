package com.yagi2.rxbox.sample.util;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.yagi2.rxbox.sample.MyApplication;

public class ClientFactory {
    private static DbxClientV2 sDbxClient;

    public static void init(String accessToken) {
        if (sDbxClient == null) {
            sDbxClient = clientBuild(accessToken);
        }
        else if (MyApplication.isTokenIsInvalid()) {
            sDbxClient = clientBuild(accessToken);
            MyApplication.setTokenIsInvalid(false);
        }
    }

    public static DbxClientV2 getClient() {
        if (sDbxClient == null) {
            throw new IllegalStateException("Client not initialized.");
        }

        return sDbxClient;
    }

    private static DbxClientV2 clientBuild(String accessToken) {
        DbxRequestConfig requestConfig = DbxRequestConfig.newBuilder("rxbox")
                .build();

        return (new DbxClientV2(requestConfig, accessToken));
    }
}

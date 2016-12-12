package com.yagi2.rxbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.GetTemporaryLinkResult;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.users.FullAccount;

import rx.Observable;
import rx.functions.Func0;

import static rx.Observable.defer;
import static rx.Observable.error;
import static rx.Observable.just;

public class RxBox {
    public static class Users {
        public static Observable<FullAccount> getCurrentAccount(final DbxClientV2 dbxClientV2) {
            return defer(new Func0<Observable<FullAccount>>() {
                @Override
                public Observable<FullAccount> call() {
                    try {
                        return just(dbxClientV2.users().getCurrentAccount());
                    } catch (DbxException e) {
                        return error(e);
                    }
                }
            });
        }
    }

    public static class Files {
        public static Observable<ListFolderResult> getFilesList(final DbxClientV2 dbxClientV2, final String path) {
            return defer(new Func0<Observable<ListFolderResult>>() {
                @Override
                public Observable<ListFolderResult> call() {
                    try {
                        return just(dbxClientV2.files().listFolder(path));
                    } catch (DbxException e) {
                        return error(e);
                    }
                }
            });
        }

        public static Observable<GetTemporaryLinkResult> getTemporaryLink(final DbxClientV2 dbxClientV2, final String path) {
            return defer(new Func0<Observable<GetTemporaryLinkResult>>() {
                @Override
                public Observable<GetTemporaryLinkResult> call() {
                    try {
                        return just(dbxClientV2.files().getTemporaryLink(path));
                    } catch (DbxException e) {
                        return error(e);
                    }
                }
            });
        }
    }
}

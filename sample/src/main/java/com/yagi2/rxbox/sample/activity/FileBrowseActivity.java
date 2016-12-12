package com.yagi2.rxbox.sample.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.yagi2.rxbox.RxBox;
import com.yagi2.rxbox.sample.R;
import com.yagi2.rxbox.sample.adapter.FileBrowseAdapter;
import com.yagi2.rxbox.sample.util.ClientFactory;
import com.yagi2.rxbox.sample.util.Observables;

import rx.Observable;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class FileBrowseActivity extends BaseActivity {

    public final static String EXTRA_PATH = "DropboxFilesActivity_Path";

    private FileBrowseAdapter mFileBrowseAdapter;
    private String lowerPath;

    public static Intent getIntent(Context context, String pathLower) {
        Intent dbxFilesIntent = new Intent(context, FileBrowseActivity.class);
        dbxFilesIntent.putExtra(FileBrowseActivity.EXTRA_PATH, pathLower);

        return dbxFilesIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String pathLower =getIntent().getStringExtra(EXTRA_PATH);
        lowerPath = pathLower == null ? "" : pathLower;

        setContentView(R.layout.activity_filebrowse);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.file_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFileBrowseAdapter = new FileBrowseAdapter(this, new FileBrowseAdapter.Callback() {
            @Override
            public void onFolderClicked(FolderMetadata folder) {
                startActivity(FileBrowseActivity.getIntent(FileBrowseActivity.this, folder.getPathLower()));
            }

            @Override
            public void onFileClicked(final FileMetadata file) {
                Toast.makeText(FileBrowseActivity.this, file.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(mFileBrowseAdapter);
    }

    @Override
    protected void loadData() {
        Observables.usingProgressDialog(this)
                .flatMap(new Func1<Void, Observable<ListFolderResult>>() {
                    @Override
                    public Observable<ListFolderResult> call(Void aVoid) {
                        return RxBox.getFilesList(ClientFactory.getClient(), lowerPath)
                                .subscribeOn(Schedulers.io());
                    }
                })
                .toSingle()
                .compose(bindToLifecycle().<ListFolderResult>forSingle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<ListFolderResult>() {
                    @Override
                    public void onSuccess(ListFolderResult result) {
                        mFileBrowseAdapter.setFiles(result.getEntries());
                    }

                    @Override
                    public void onError(Throwable error) {

                    }
                });
    }
}

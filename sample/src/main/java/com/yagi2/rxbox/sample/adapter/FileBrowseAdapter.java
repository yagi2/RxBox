package com.yagi2.rxbox.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.yagi2.rxbox.sample.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileBrowseAdapter extends RecyclerView.Adapter<FileBrowseAdapter.ViewHolder> {

    private List<Metadata> mFiles;
    private Callback mCallback;
    private Context mContext;

    public interface Callback {
        void onFolderClicked(FolderMetadata folder);
        void onFileClicked(FileMetadata file);
    }

    public FileBrowseAdapter(Context context, Callback callback) {
        mContext = context;
        mCallback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.files_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mFiles.get(position));
    }

    @Override
    public long getItemId(int position) {
        return mFiles.get(position).getPathLower().hashCode();
    }

    @Override
    public int getItemCount() {
        return mFiles == null ? 0 : mFiles.size();

    }

    public void setFiles(List<Metadata> files) {
        mFiles = Collections.unmodifiableList(new ArrayList<>(files));
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private Metadata metadata;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.file_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (metadata instanceof FileMetadata) {
                mCallback.onFileClicked((FileMetadata)metadata);
            }
            else if (metadata instanceof FolderMetadata) {
                mCallback.onFolderClicked((FolderMetadata)metadata);
            }
        }

        public void bind(Metadata item) {
            metadata = item;
            String type = "";

            if (item instanceof FileMetadata) {
                type += " [ITEM]";
            }
            else if (item instanceof FolderMetadata) {
                type += " [FOLDER]";
            }

            textView.setText(item.getName()+type);
        }
    }
}

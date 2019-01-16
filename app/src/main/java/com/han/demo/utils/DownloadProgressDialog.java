package com.han.demo.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.han.demo.R;


public class DownloadProgressDialog extends Dialog {
    private ProgressBar mProgressBar;
    private TextView mProgressTV;


    public DownloadProgressDialog(@NonNull Context context) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_download_progress, null);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBar.setMax(100);
        setCancelable(false);
        mProgressTV = view.findViewById(R.id.progress_tv);
        setContentView(view);
    }


    public void updateProgress(int progress) {
        mProgressTV.setText(progress + "%");
        mProgressBar.setProgress(progress);
    }



}

package com.ezz.data.remote.imageloader.impl;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.ezz.data.R;
import com.ezz.data.remote.imageloader.ImageLoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Ezz Waleed on 04,March,2019
 */

/**
 * Picasso implementation of {@link ImageLoader} interface.
 */
public class PicassoImageLoader implements ImageLoader {

    private Picasso picasso;

    @Inject
    public PicassoImageLoader(Picasso picasso) {
        this.picasso = picasso;
    }


    @Override
    public void loadImageWithProgress(@NonNull final ImageView imageView, final ProgressBar progressBar, @Nullable String url) {
        loadImageWithProgress(imageView, progressBar, url, R.color.transparent, R.color.white);
    }



    @Override
    public void loadImageWithProgress(@NonNull final ImageView imageView, final ProgressBar progressBar, @Nullable String url, int placeHolder, final int error) {
        if (url == null || url.isEmpty()) {
            imageView.setImageResource(error);
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        picasso.load(url).placeholder(placeHolder).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.GONE);
                imageView.setImageResource(error);
            }
        });
    }


    @Override
    public void loadImageWithoutProgress(@NonNull final ImageView imageView, @Nullable String url) {
        loadImageWithoutProgress(imageView, url, R.color.transparent, R.color.white);
    }


    @Override
    public void loadImageWithoutProgress(@NonNull final ImageView imageView, @Nullable String url, int placeHolder, final int error) {
        if (url == null || url.isEmpty()) {
            imageView.setImageResource(error);
            return;
        }
        picasso.load(url).placeholder(placeHolder).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                // nothing to do
            }

            @Override
            public void onError(Exception e) {
                imageView.setImageResource(error);
            }
        });
    }

    @Override
    public void loadImageWithCallback(@NonNull ImageView imageView, @Nullable String url, @NonNull CallBack callBack) {
        loadImageWithCallback(imageView, url, callBack, R.color.transparent, R.color.white);
    }

    @Override
    public void loadImageWithCallback(@NonNull ImageView imageView, @Nullable String url, @NonNull CallBack callBack, int placeHolder, int error) {
        if (url == null || url.isEmpty()) {
            imageView.setImageResource(error);
            return;
        }
        picasso.load(url).placeholder(placeHolder).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                callBack.onFinishLoading();
            }

            @Override
            public void onError(Exception e) {
                imageView.setImageResource(error);
                callBack.onFinishLoading();
            }
        });
    }
}

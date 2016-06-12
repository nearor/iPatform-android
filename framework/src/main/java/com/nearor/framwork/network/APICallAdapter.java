package com.nearor.framwork.network;

import android.support.annotation.Nullable;


import com.nearor.framwork.concurency.MainThreadExecutor;

import java.io.IOException;
import java.util.concurrent.Executor;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * {@link retrofit.Call} to {@link APICall}
 * Created by nearor on 1/31/16.
 */
class APICallAdapter<T> implements APICall<T> {

    private final Call<T> call;
    private Executor mCallBackExecutor = new MainThreadExecutor();

    public APICallAdapter(Call<T> call) {
        this.call = call;
    }

    @Override
    public void cancel() {
        call.cancel();
    }


    @Override
    public void enqueue(final ICallManager manager, final APICallBack<T> callback) {
        if (manager != null) {
            manager.addCall(call);
        }

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(final Response<T> response, Retrofit retrofit) {
                mCallBackExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        callFinished(manager);

                        if (callback != null) {
                            if (response.body() != null) {
                                callback.onSuccess(response.body());
                            } else {
                                callback.onError(new Throwable("Response.body is null"), null);
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(final Throwable t) {
                mCallBackExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        callFinished(manager);
                        if (callback != null) {
                            if (t instanceof APIResponseException) {
                                callback.onError(t, ((APIResponseException) t).getApiResponse());
                            } else {
                                callback.onError(t, null);
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public T execute() throws IOException {
        Response<T> response = call.execute();
        return response == null ? null : response.body();
    }

    @Override
    public APICall<T> clone() {
        return new APICallAdapter<>(call.clone());
    }

    private void callFinished(@Nullable ICallManager manager) {
        if (manager != null) {
            manager.removeCall(call);
        }
    }
}
